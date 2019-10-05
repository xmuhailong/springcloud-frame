package com.zzl.meetingroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MeetingroomApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingroomApplication.class, args);
    }

}
