package com.zzl.authentication.authenticationservice.controller;

import com.zzl.authentication.authenticationservice.service.IAuthService;
import com.zzl.core.base.domain.ResultHelper;
import com.zzl.db.user.vo.LoginModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/1/28 1:52 下午
 */
@RestController
public class OAuth2Controller {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private IAuthService iAuthService;

    @GetMapping("query")
    @PreAuthorize("hasAnyAuthority('query')")
    public String query() {
        return "query";
    }


    @GetMapping("/user")
    public Principal user(Principal member) {
        return member;
    }

    /**
     * @description 从此方法中转入到具体的获取token链接中
     * @param [loginModel]
     * @return com.zzl.authentication.authenticationservice.utils.ResultHelper
     * @author zhaozhonglong
     * @date  2023/1/2 11:08:59
     */
    @ApiOperation(value = "用户名密码获取token,刷新token")
    @PostMapping("/oauth/user/token")
    public ResultHelper getUserTokenInfo(@RequestBody LoginModel loginModel) throws Exception {

        return iAuthService.getTokenResult(loginModel);

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