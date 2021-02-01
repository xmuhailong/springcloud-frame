package com.zzl.meetingroom.controller;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/1/27 9:28 下午
 */
@RestController
@RequestMapping("/api")
public class MyTest {
    /**
     *
     * @方法名:hello
     * @方法描述:用于测试拥有hello的权限
     * @return
     * @修改描述:
     * @版本:1.0
     * @创建人:cc
     * @创建时间:2019年11月19日 下午1:48:06
     * @修改人:cc
     * @修改时间:2019年11月19日 下午1:48:06
     */
    @GetMapping("hello")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public String hello() {
        return "hello fuck you all time";
    }

    /**
     *
     * @方法名:query
     * @方法描述:用户测试未含有query的权限
     * @return
     * @修改描述:
     * @版本:1.0
     * @创建人:cc
     * @创建时间:2019年11月19日 下午1:50:13
     * @修改人:cc
     * @修改时间:2019年11月19日 下午1:50:13
     */
    @GetMapping("query")
//    @PreAuthorize("hasAnyAuthority('query')")
    public String query() {
        return "具有query权限";
    }

    /**
     *
     * @方法名:test
     * @方法描述:未加权限则登录即可访问
     * @return
     * @修改描述:
     * @版本:1.0
     * @创建人:cc
     * @创建时间:2019年11月19日 下午1:48:44
     * @修改人:cc
     * @修改时间:2019年11月19日 下午1:48:44
     */
    @GetMapping("test")
    public String test() {
        return "test fuckyou";
    }

    /**
     *
     * @方法名:current
     * @方法描述:获取用户的方式1
     * @param principal
     * @return
     * @修改描述:
     * @版本:1.0
     * @创建人:cc
     * @创建时间:2019年11月19日 下午1:49:23
     * @修改人:cc
     * @修改时间:2019年11月19日 下午1:49:23
     */
    @GetMapping("current")
    public Principal current(Principal principal) {
        return principal;
    }

    /**
     *
     * @方法名:current1
     * @方法描述:获取用户的方式2
     * @param authentication
     * @return
     * @修改描述:
     * @版本:1.0
     * @创建人:cc
     * @创建时间:2019年11月19日 下午1:49:54
     * @修改人:cc
     * @修改时间:2019年11月19日 下午1:49:54
     */
    @GetMapping("current1")
    public Authentication current1(Authentication authentication) {
        return authentication;
    }

}
