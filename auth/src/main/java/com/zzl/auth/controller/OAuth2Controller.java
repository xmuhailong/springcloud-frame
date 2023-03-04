package com.zzl.auth.controller;

import com.zzl.auth.service.IAuthService;
import com.zzl.core.base.domain.ResultHelper;
import com.zzl.core.base.domain.user.LoginModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/1/28 1:52 下午
 */
@Slf4j
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

    /**
     * 当前登陆用户信息<br>
     * <p>
     * security获取当前登录用户的方法是SecurityContextHolder.getContext().getAuthentication()<br>
     * 返回值是接口org.springframework.security.core.Authentication，又继承了Principal<br>
     * 这里的实现类是org.springframework.security.oauth2.provider.OAuth2Authentication<br>
     * <p>
     * 因此这只是一种写法，下面注释掉的三个方法也都一样，这四个方法任选其一即可，也只能选一个，毕竟uri相同，否则启动报错<br>
     *
     * @return
     */
    @GetMapping("/user-me")
    @ApiIgnore
    public Authentication principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("user-me:{}", authentication.getName());
        return authentication;
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
    public ResultHelper getUserTokenInfo(@RequestBody LoginModel loginModel) {

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
