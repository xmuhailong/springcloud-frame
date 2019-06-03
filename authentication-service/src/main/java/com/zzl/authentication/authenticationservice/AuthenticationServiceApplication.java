package com.zzl.authentication.authenticationservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableEurekaClient
@EnableAuthorizationServer
@SpringBootApplication(scanBasePackages = {"com.zzl.authentication.authenticationservice", "com.zzl.myredis"})
@MapperScan(basePackages = "com.zzl.authentication.authenticationservice.mapper")
public class AuthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }


}
