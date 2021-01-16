package com.zzl.authentication.authenticationservice.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzl.db.user.entity.User;
import com.zzl.db.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author zhaozhonglong
 * @description
 * @date 2019/5/6 下午9:53
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
            throw new UsernameNotFoundException("用户["+username+"]不存在！");
        }

        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setUserName(username);
        userDetails.setPassword(user.getPassword());
        userDetails.setUser(user);
        return userDetails;
    }
}
