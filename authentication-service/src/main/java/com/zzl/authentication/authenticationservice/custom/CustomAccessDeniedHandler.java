package com.zzl.authentication.authenticationservice.custom;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 授权失败(forbidden)时返回信息
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/1/26 11:29 上午
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Map map = new HashMap();
        map.put("code", "400");
        map.put("msg", "无法访问：" + accessDeniedException.getMessage());

        response.setContentType("application/json;charset=UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
