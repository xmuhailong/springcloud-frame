package com.zzl.authentication.authenticationservice.security;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @description 进行认证
 * 参考WebSecurityConfig.authenticationManagerBean方法
 *
 * DaoAuthenticationProvider 在进行认证的时候需要一个 UserDetailsService 来获取用户的信息 UserDetails
 *
 * 所以如果我们需要改变认证的方式，我们可以实现自己的 AuthenticationProvider；
 * 如果需要改变认证的用户信息来源，我们可以实现 UserDetailsService
 *
 * @param
 * @return
 * @author zhaozhonglong
 * @date  2021/1/16 21:58:21
 */
@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider  {
    @Autowired
    private PasswordEncoder passwordEncoder;


    // 构造函数中注入
    public MyAuthenticationProvider(MyUserDetailsService myUserDetailsService)
    {
        this.setUserDetailsService(myUserDetailsService);
    }

    /**
     * 自定义验证方式
     * 也可参考https://blog.csdn.net/fanbojiayou/article/details/79323315中AdminAuthenticationProvider来实现
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        MyUserDetails userDetails = (MyUserDetails)
                this.getUserDetailsService().loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {

        }

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        // 设置为已验证(已找到原因，阅读UsernamePasswordAuthenticationToken源码，两个构造器的区别)
        // authentication.setAuthenticated(true);
        // error :java.lang.IllegalArgumentException: Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead
        // 传入userDetails及usermodel authenticationToken会被传到RS的返回参数中
        // username
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(JSON.toJSONString(userDetails,SerializerFeature.WriteMapNullValue), password, authorities);
        return authenticationToken;

    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
