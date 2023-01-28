package com.zzl.auth.config;

import com.zzl.auth.constant.PermitAllUrl;
import com.zzl.auth.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    private MyUserDetailsService myUserDetailsService;

    /**
     * @description 配置使用的加密方式
     * @param []
     * @return PasswordEncoder
     * @author zhaozhonglong
     * @date  2021/1/16 21:41:36
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * @description 认证管理类
     * 密码模式下必须注入的bean authenticationManagerBean
     * 认证是由 AuthenticationManager 来管理的，
     * 但是真正进行认证的是 AuthenticationManager 中定义的AuthenticationProvider。
     * AuthenticationManager 中可以定义有多个 AuthenticationProvider
     *
     * 当我们使用 authentication-provider 元素来定义一个 AuthenticationProvider 时，
     * 如果没有指定对应关联的 AuthenticationProvider 对象，
     * Spring Security 默认会使用 DaoAuthenticationProvider（myAuthenticationProvider继承此类）。
     *
     *
     * DaoAuthenticationProvider 在进行认证的时候需要一个 UserDetailsService 来获取用户的信息 UserDetails，
     * 其中包括用户名、密码和所拥有的权限等
     * 所以configure中，配置了myAuthenticationProvider和myUserDetailsService
     *
     * 所以如果我们需要改变认证的方式，我们可以实现自己的 AuthenticationProvider；
     * 如果需要改变认证的用户信息来源，我们可以实现 UserDetailsService
     *
     * @param []
     * @return AuthenticationManager
     * @author zhaozhonglong
     * @date  2021/1/16 21:43:34
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * @description 配置校验的具体类
     *      * 定义用户以及相应的角色和权限。
     *      * 对于复杂的权限管理系统，用户和角色关联，角色和权限关联，权限和资源关联；对于简单的权限管理系统，
     *      * 用户和权限关联，权限和资源关联。无论是哪种，用户都不会和角色以及权限同时直接关联。反应到代码上
     *      * 就是roles方法和authorities方法不能同时调用，如果同时调用，后者会覆盖前者(可以自行查看源码，
     *      * 最终都会调用authorities(Collection<? extends GrantedAuthority> authorities)方法)。
     *      * 需要注意的是，spring security会自动给用户的角色加上ROLE_前缀。
     * @param [auth]
     * @return void
     * @author zhaozhonglong
     * @date  2021/1/16 21:45:31
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService) // loadUserByUsername此方法用于根据用户名获取用户信息
            .passwordEncoder(bCryptPasswordEncoder()); // 设置校验方式
    }

    /**
     * 1:
     * 请求授权:
     * spring security 使用以下匹配器来匹配请求路劲：
     * 		antMatchers:使用ant风格的路径匹配
     * 		regexMatchers:使用正则表达式匹配路劲
     * anyRequest:匹配所有请求路劲
     * 在匹配了请求路劲后，需要针对当前用户的信息对请求路径进行安全处理。
     * 2:定制登录行为。
     *  	formLogin()方法定制登录操作
     *      loginPage()方法定制登录页面访问地址
     * 		defaultSuccessUrl()登录成功后转向的页面
     *      permitAll()
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers().antMatchers("/oauth/**")
////                .and()
////                .authorizeRequests()
////                .antMatchers("/oauth/**").authenticated()
////                .and()
////                .csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(PermitAllUrl.permitAllUrl(
                        "/swaggerList",
                        "/oauth/token",
                        "/oauth/user/token",
                        "/users-anon/**",
                        "/smsVerify",
                        "/thirdPartyLogin/**")).permitAll() // 放开权限的url
                .anyRequest().authenticated()
                .and()
                .httpBasic().and().csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .headers().xssProtection().disable();
    }



}
