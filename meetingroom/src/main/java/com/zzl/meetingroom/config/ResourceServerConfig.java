package com.zzl.meetingroom.config;

import com.zzl.meetingroom.filter.PermitAllSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;


/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/1/27 10:24 下午
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${spring.application.name}")
    private String resourceId;

    @Autowired
    private AuthExceptionEntryPoint authExceptionEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private PermitAllSecurityConfig permitAllSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .apply(permitAllSecurityConfig)
                .and()
                .requestMatchers()
                .antMatchers("/api/**", "/permitAll/**")
                .and()
                .authorizeRequests()
                // 设置free对应的无需校验
                .antMatchers("/permitAll/**").permitAll()
                // 设置api对应的需要校验
                .antMatchers("/api/**").authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 重点，设置资源id
        resources.resourceId(resourceId);

        //这里把自定义异常加进去
        resources.authenticationEntryPoint(authExceptionEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
    }
}
