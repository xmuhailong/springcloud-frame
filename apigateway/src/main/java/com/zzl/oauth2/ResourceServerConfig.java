package com.zzl.oauth2;

import com.zzl.core.base.constants.PermitAllUrl;
import com.zzl.core.base.exception.auth.Auth2ResponseExceptionTranslator;
import com.zzl.core.base.exception.auth.AuthExceptionEntryPoint;
import com.zzl.core.base.exception.auth.CustomAccessDeniedHandler;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/1/27 10:24 下午
 */
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 定义异常转换类生效
        AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        ((OAuth2AuthenticationEntryPoint) authenticationEntryPoint)
                .setExceptionTranslator(new Auth2ResponseExceptionTranslator());
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().and().csrf().disable().
                //异常的时候处理 返回SC_UNAUTHORIZED --> 401状态码未授权异常
                        exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and().authorizeRequests()
                // 此处是放开auth服务下的所有请求，/sys/login 会通过feign访问auth服务下的接口
                .antMatchers(PermitAllUrl.permitAllUrl("/auth/**","/sys/login")).permitAll() // 放开权限的url
                .anyRequest().authenticated().and().httpBasic();

    }
}
