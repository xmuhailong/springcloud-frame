package com.zzl.meetingroom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zzl.meetingroom.annotation.LoginUser;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/1/27 9:28 下午
 */
@RestController
@RequestMapping("/api")
public class MyTest {
    @GetMapping("hello")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public String hello(@LoginUser String userId) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//
//        json = StringEscapeUtils.unescapeJavaScript(json);
//        json = json.replaceFirst("\"", "");
//        json = json.substring(0, json.length() - 1);
//
//        ObjectNode node = objectMapper.readValue(json, ObjectNode.class);
//
//        if (node.has("user")) {
//            System.out.println(node.get("user"));
//        }

        System.out.println(userId);

        return "hello fuck you all time";
    }


}
