package com.zzl.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@FeignClient(value = "auth")
public interface UserClient {

    /**
     * 获取access_token<br>
     * 这是spring-security-oauth2底层的接口，类TokenEndpoint<br>
     * @param parameters
     * @return
     * @see org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
     */
    @PostMapping(path = "/oauth/token")
    Map<String, Object> postAccessToken(@RequestParam Map<String, String> parameters);

    /**
     * 删除access_token和refresh_token<br>
     * 认证中心的OAuth2Controller方法removeToken
     *
     * @param access_token
     */
    @DeleteMapping(path = "/remove_token")
    void removeToken(@RequestParam("access_token") String access_token);
}
