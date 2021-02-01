package com.zzl.authentication.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/1/28 1:52 下午
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("query")
    @PreAuthorize("hasAnyAuthority('query')")
    public String query() {
        return "query";
    }

    /**
     *
     * @方法名:user
     * @方法描述:用于进行权限校验查询
     * @param member
     * @return
     * @修改描述:
     * @版本:1.0
     * @创建人:cc
     * @创建时间:2019年11月19日 下午4:07:52
     * @修改人:cc
     * @修改时间:2019年11月19日 下午4:07:52
     */
    @GetMapping("/user")
    public Principal user(Principal member) {
        return member;
    }

    @DeleteMapping(value = "/exit")
    public boolean revokeToken(String access_token) {
        return consumerTokenServices.revokeToken(access_token);

//        Result result = new Result();
//        if (consumerTokenServices.revokeToken(access_token)) {
//            result.setCode(ResultCode.SUCCESS.getCode());
//            result.setMessage("注销成功");
//        } else {
//            result.setCode(ResultCode.FAILED.getCode());
//            result.setMessage("注销失败");
//        }
//        return result;
    }
}
