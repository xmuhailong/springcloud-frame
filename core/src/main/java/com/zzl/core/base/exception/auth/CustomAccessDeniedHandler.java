package com.zzl.core.base.exception.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzl.core.base.domain.ResultHelper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权失败(forbidden)时返回信息
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/1/26 11:29 上午
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(httpServletResponse.getOutputStream(), ResultHelper.failed2Msg("权限校验错误"));
        } catch (Exception ex) {
            throw new ServletException();
        }
    }
}
