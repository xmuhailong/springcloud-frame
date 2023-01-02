package com.zzl.authentication.authenticationservice.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzl.authentication.authenticationservice.custom.CustomOauthExceptionSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @description 指定json序列化方式
 * @author zhaozhonglong
 * @date  2021/1/26 12:33:04
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

    public CustomOauthException(String msg) {
        super(msg);
    }

    public CustomOauthException(String msg, Throwable t) {
        super(msg, t);
    }

}
