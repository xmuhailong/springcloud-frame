package com.zzl.meetingroom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description 用于tokan校验失败返回信息
 * 未带token、token过期、资源权限不足
 * @author zhaozhonglong
 * @date  2021/1/26 12:34:58
 */
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws  ServletException {

        Throwable cause = authException.getCause();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");

        try {
            if (cause instanceof OAuth2AccessDeniedException) {
                // 资源权限不足
                response.getWriter().write(mapper.writeValueAsString("from meetingroom 资源不足"));
            } else if (cause == null || cause instanceof InvalidTokenException) {
                // 未带token或token无效
                // cause == null 一般可能是未带token
                response.getWriter().write(mapper.writeValueAsString("from meetingroom 没带token或token不对"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }
}
