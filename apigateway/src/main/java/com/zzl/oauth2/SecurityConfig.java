//package com.zzl.oauth2;
//
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * @author zhaozhonglong
// * @version 1.0.0
// * @date 2021/1/28 10:11 上午
// */
//@EnableOAuth2Sso
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .and().csrf().disable();
//        http.headers().frameOptions().sameOrigin().disable();
//        http.cors().disable();
//    }
//
//}
