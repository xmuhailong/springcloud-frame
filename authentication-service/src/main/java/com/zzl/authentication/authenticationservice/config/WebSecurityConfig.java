package com.zzl.authentication.authenticationservice.config;

import com.zzl.authentication.authenticationservice.constant.StaticParams;
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

/**
 * 安全配置
 * @ EnableWebSecurity 启用web安全配置
 * @ EnableGlobalMethodSecurity 启用全局方法安全注解，就可以在方法上使用注解来对请求进行过滤
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * 配置系统用户，这里使用内存存储，添加了用户名为 user ，角色为 USER 的用户,
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
        // 硬编码形式白名单
        http
            .authorizeRequests()
            .antMatchers(
                StaticParams.PATHREGX.API,
                StaticParams.PATHREGX.CSS,
                StaticParams.PATHREGX.JS,
                StaticParams.PATHREGX.IMG).permitAll()//允许用户任意访问
                .anyRequest().authenticated()//其余所有请求都需要认证后才可访问
                .and()
                .formLogin()
                .permitAll();//允许用户任意访问

        http.csrf().disable();
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

}
