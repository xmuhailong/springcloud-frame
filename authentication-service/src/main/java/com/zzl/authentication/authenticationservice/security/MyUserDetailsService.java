package com.zzl.authentication.authenticationservice.security;

import com.zzl.authentication.authenticationservice.entity.User;
import com.zzl.authentication.authenticationservice.service.IUserService;
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
        // TODO 此处可以验证密码的，密码从何传递过来？难道数据库不允许username重复？
        User user = iUserService.selectByUsername(username);

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
