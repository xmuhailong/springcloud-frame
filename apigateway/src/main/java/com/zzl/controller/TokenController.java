package com.zzl.controller;

import com.zzl.core.base.domain.ResultHelper;
import com.zzl.core.base.enums.CredentialTypeEnum;
import com.zzl.feign.UserClient;
import com.zzl.core.base.log.Log;
import com.zzl.core.base.log.constants.LogModule;
import com.zzl.core.base.utils.AppUserUtil;
import com.zzl.feign.LogClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 登陆、刷新token、退出
 *
 * @author zhaozhonglong
 */
@Slf4j
@RestController
public class TokenController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private LogClient logClient;

    @GetMapping("/users/current")
    public ResultHelper getLoginAppUser() {
        return ResultHelper.succeed(AppUserUtil.getLoginAppUser());
    }

    /**
     * 系统登陆<br>
     * 根据用户名登录<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/sys/login")
    public Map<String, Object> login(String username, String password) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "client1");
        parameters.put("client_secret", "client1");
        parameters.put(OAuth2Utils.SCOPE, "webclient");
//		parameters.put("username", username);
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", username + "|" + CredentialTypeEnum.USERNAME.name());
        parameters.put("password", password);
        log.info("调用前");
        Map<String, Object> tokenInfo = userClient.postAccessToken(parameters);
        saveLoginLog(username, "用户名密码登陆");

        return tokenInfo;
    }

    /**
     * 短信登录
     *
     * @param phone
     * @param key
     * @param code
     * @return
     */
    @PostMapping("/sys/login-sms")
    public Map<String, Object> smsLogin(String phone, String key, String code) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "system");
        parameters.put("client_secret", "system");
        parameters.put(OAuth2Utils.SCOPE, "app");
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了校验短信验证码，我们也拼上code等
        parameters.put("username", phone + "|" + CredentialTypeEnum.PHONE.name() + "|" + key + "|" + code + "|"
                + DigestUtils.md5Hex(key + code));
        // 短信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put("password", phone);

        Map<String, Object> tokenInfo = userClient.postAccessToken(parameters);
        saveLoginLog(phone, "手机号短信登陆");

        return tokenInfo;
    }

    /**
     * 微信登录
     *
     * @return
     */
    @PostMapping("/sys/login-wechat")
    public Map<String, Object> smsLogin(String openid, String tempCode) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "system");
        parameters.put("client_secret", "system");
        parameters.put(OAuth2Utils.SCOPE, "app");
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了服务端校验，我们也拼上tempCode
        parameters.put("username", openid + "|" + CredentialTypeEnum.WECHAT_OPENID.name() + "|" + tempCode);
        // 微信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put("password", tempCode);

        Map<String, Object> tokenInfo = userClient.postAccessToken(parameters);
        saveLoginLog(openid, "微信登陆");

        return tokenInfo;
    }

    /**
     * 登陆日志
     *
     * @param username
     */
    private void saveLoginLog(String username, String remark) {
        log.info("{}登陆", username);
        // 异步
        CompletableFuture.runAsync(() -> {
            try {
                Log log = Log.builder().username(username).module(LogModule.LOGIN).remark(remark).createTime(new Date())
                        .build();
                logClient.save(log);
            } catch (Exception e) {
                // do nothing
            }

        });
    }

    /**
     * 系统刷新refresh_token
     *
     * @param refresh_token
     * @return
     */
    @PostMapping("/sys/refresh_token")
    public Map<String, Object> refresh_token(String refresh_token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        parameters.put(OAuth2Utils.CLIENT_ID, "system");
        parameters.put("client_secret", "system");
        parameters.put(OAuth2Utils.SCOPE, "app");
        parameters.put("refresh_token", refresh_token);

        return userClient.postAccessToken(parameters);
    }

    /**
     * 退出
     *
     * @param access_token
     */
    @GetMapping("/sys/logout")
    public void logout(String access_token, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(access_token)) {
            if (StringUtils.isNoneBlank(token)) {
                access_token = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
            }
        }
        userClient.removeToken(access_token);
    }
}
