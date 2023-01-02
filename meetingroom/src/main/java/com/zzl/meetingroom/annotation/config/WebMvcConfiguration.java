package com.zzl.meetingroom.annotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/2/27 5:05 下午
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * @description 将loginUserMethodArgumentResolver配置启用
     * @param [argumentResolvers]
     * @return void
     * @author zhaozhonglong
     * @date  2021/2/27 17:21:52
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserMethodArgumentResolver());
    }

    @Bean
    public LoginUserMethodArgumentResolver loginUserMethodArgumentResolver() {
        return new LoginUserMethodArgumentResolver();
    }
}
