package com.zzl.meetingroom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/2/19 8:47 下午
 */
@RestController
@RequestMapping("/permitAll")
public class FreeController {

    @GetMapping("/name")
    public Object getName() {

        return "i from permitAll name";
    }
}
