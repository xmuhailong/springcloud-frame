package com.zzl.authentication.authenticationservice.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzl.db.user.entity.User;
import com.zzl.db.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;

/**
 * @description 需要改变认证的用户信息来源，我们可以实现 UserDetailsService
 * @author zhaozhonglong
 * @date  2021/1/16 22:02:59
 */
@Configuration
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserService iUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserService.getOne(Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, username),
                false);

        if (null == user) {
            throw new InvalidGrantException("from auth myUserDetailsService用户名或密码验证失败");
        }

        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setUserName(username);
        userDetails.setPassword(user.getPassword());
        userDetails.setUser(user);
        return userDetails;
    }
}
