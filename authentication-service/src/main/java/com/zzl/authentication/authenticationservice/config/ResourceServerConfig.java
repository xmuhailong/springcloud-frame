package com.zzl.authentication.authenticationservice.config;

import com.zzl.authentication.authenticationservice.constant.PermitAllUrl;
import com.zzl.authentication.authenticationservice.custom.AuthExceptionEntryPoint;
import com.zzl.authentication.authenticationservice.custom.CustomAccessDeniedHandler;
import com.zzl.authentication.authenticationservice.exception.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源服务配置
 * @ EnableResourceServer 启用资源服务
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)   // 启用注解权限配置
@Order(3)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private AuthExceptionEntryPoint authExceptionEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .requestMatchers()
//                .antMatchers("/**")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .and()
//                .httpBasic();
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(new MyAccessDeniedHandler())
                .and().requestMatcher(new OAuth2RequestedMatcher()).authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(PermitAllUrl.permitAllUrl(
                        "/swaggerList",
                        "/oauth/token",
                        "/oauth/user/token",
                        "/users-anon/**",
                        "/smsVerify",
                        "/thirdPartyLogin/**")).permitAll() // 放开权限的url
                .anyRequest().authenticated().and().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 重点，设置资源id
        resources.resourceId("authorize-server");

        //这里把自定义异常加进去
        resources.authenticationEntryPoint(authExceptionEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
    }

    /**
     * 判断来源请求是否包含oauth2授权信息<br>
     * url参数中含有access_token,或者header里有Authorization
     */
    private static class OAuth2RequestedMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
                return true;
            }
            // 请求参数中包含access_token参数
            if (request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) != null) {
                return true;
            }
            // 头部的Authorization值以Bearer开头
            String auth = request.getHeader("Authorization");
            if (auth != null) {
                return auth.startsWith(OAuth2AccessToken.BEARER_TYPE);
            }

            return false;
        }
    }

}
