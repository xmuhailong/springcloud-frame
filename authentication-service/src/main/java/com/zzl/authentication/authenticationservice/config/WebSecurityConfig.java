package com.zzl.authentication.authenticationservice.config;

import com.zzl.authentication.authenticationservice.security.MyAuthenticationProvider;
import com.zzl.authentication.authenticationservice.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置
 * @ EnableWebSecurity 启用web安全配置
 * @ EnableGlobalMethodSecurity 启用全局方法安全注解，就可以在方法上使用注解来对请求进行过滤
 *
 * 在ResourceServerProperties中，定义了它的order默认值为SecurityProperties.ACCESS_OVERRIDE_ORDER - 1;
 * 是大于100的,即WebSecurityConfigurerAdapter的配置的拦截要优先于ResourceServerConfigurerAdapter，
 * 优先级高的http配置是可以覆盖优先级低的配置的
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private MyUserDetailsService myUserDetailsService;



    /**
     * @description 密码加密工具类，它可以实现不可逆的加密
     * @param []
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @author zhaozhonglong
     * @date  2021/1/16 11:57:08
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 密码模式下必须注入的bean authenticationManagerBean
     * 认证是由 AuthenticationManager 来管理的，
     * 但是真正进行认证的是 AuthenticationManager 中定义的AuthenticationProvider。
     *  AuthenticationManager 中可以定义有多个 AuthenticationProvider
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 用户认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider)
            .userDetailsService(myUserDetailsService);
    }

    /**
     * 1:
     * 请求授权:
     * spring security 使用以下匹配器来匹配请求路劲：
     * 		antMatchers:使用ant风格的路径匹配
     * 		regexMatchers:使用正则表达式匹配路劲
     * anyRequest:匹配所有请求路劲
     * 在匹配了请求路劲后，需要针对当前用户的信息对请求路劲进行安全处理。
     * 2:定制登录行为。
     *  	formLogin()方法定制登录操作
     *      loginPage()方法定制登录页面访问地址
     * 		defaultSuccessUrl()登录成功后转向的页面
     *      permitAll()
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 允许匿名访问所有接口
        http.authorizeRequests()
                .antMatchers("/**").permitAll();
        // 硬编码形式白名单
//        http
//            .authorizeRequests()
//            .antMatchers(
//                StaticParams.PATHREGX.API,
//                StaticParams.PATHREGX.CSS,
//                StaticParams.PATHREGX.JS,
//                StaticParams.PATHREGX.IMG).permitAll()//允许用户任意访问
//                .antMatchers(StaticParams.SWAGGERUI.getSwaggerResource()).permitAll()// Swagger的链接允许通过
//                ;//允许用户任意访问
//
//        http.csrf().disable();
    }



}
