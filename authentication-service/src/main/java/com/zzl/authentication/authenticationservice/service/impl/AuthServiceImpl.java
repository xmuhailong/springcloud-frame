package com.zzl.authentication.authenticationservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zzl.authentication.authenticationservice.constant.CredentialType;
import com.zzl.authentication.authenticationservice.service.IAuthService;
import com.zzl.authentication.authenticationservice.utils.LoginModel;
import com.zzl.authentication.authenticationservice.utils.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2023/1/2 10:33 上午
 */
@Component
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResultHelper getTokenResult(LoginModel loginModel) {
        //请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //提交参数设置
        MultiValueMap<String, String> p = new LinkedMultiValueMap<>();
        String grantType = loginModel.getGrantType();
        p.add("client_secret",loginModel.getClientSecret());
        p.add("client_id",loginModel.getClientId());
        if(!StringUtils.isEmpty(loginModel.getRefreshToken()) &&  grantType.equals("refresh_token")){
            p.add("grant_type","refresh_token");
            p.add("refresh_token",loginModel.getRefreshToken());
        }else {
            p.add("username", loginModel.getUsername());

            String[] params = loginModel.getUsername().split("\\|");

            if (params.length == 1 || (CredentialType.USERNAME == CredentialType.valueOf(params[1]))) {
                p.add("password", loginModel.getPassword());
            }

        }
        p.add("grant_type", !StringUtils.isEmpty(grantType)?grantType:"password");
        p.add("scope",!StringUtils.isEmpty(loginModel.getScope())?loginModel.getScope():"app");
        //提交请求
        try {
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(p, headers);
            ResponseEntity<String> exchange = restTemplate.exchange(URI.create("http://authentication-service/oauth/token"),
                    HttpMethod.POST, entity, String.class);
            if (exchange.getStatusCodeValue() == HttpStatus.OK.value()) {
                return ResultHelper.succeed(JSONObject.parse(exchange.getBody()));
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return ResultHelper.failed2Msg("用户名或密码错误");
        } catch (RestClientException e) {
            e.printStackTrace();
            return ResultHelper.failed2Msg("用户名密码错误");
        }
        return ResultHelper.succeed(null);
    }
}