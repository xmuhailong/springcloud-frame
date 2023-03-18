package com.zzl.core.base.exception;

import com.alibaba.fastjson.JSONObject;
import com.zzl.core.base.domain.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 统一响应
 * Created by zhaozhonglong on 2019/6/1.
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ResponseDataAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object obj, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        log.info("返回结果是：{}", obj);

        if (obj instanceof ResultHelper) {
            return obj;
        }

        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(obj));

        if (!StringUtils.isEmpty(jsonObject.get("respCode"))) {
            return obj;
        }

        // 系统报错
        if(obj.toString().endsWith("}")){
             if(!StringUtils.isEmpty(jsonObject.get("error"))){
                 return ResultHelper.failed(obj, StringUtils.isEmpty(jsonObject.get("message")) ? "未知异常" : jsonObject.get("message").toString());
             }
         } else {
            return ResultHelper.succeed(jsonObject);
        }

        return obj;
    }
}
