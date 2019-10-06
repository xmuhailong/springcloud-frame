package com.zzl.meetingroom.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2019/10/5 下午4:38
 */

@RestController
public class ConfigTest {
    @Value("${hello}")
    private String hello;

    @RequestMapping("/hello")
    public String from() {
        return this.hello;
    }
}
