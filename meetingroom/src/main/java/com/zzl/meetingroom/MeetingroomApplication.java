package com.zzl.meetingroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/1/27 9:22 下午
 */

@EnableEurekaClient
@SpringBootApplication
public class MeetingroomApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeetingroomApplication.class, args);
    }
}
