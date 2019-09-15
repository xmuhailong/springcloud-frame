package com.zzl.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zzl.base.domain.Result;
import com.zzl.base.enums.ResultEnum;
import com.zzl.base.utils.ResultUtil;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.StreamUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 返回校验
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2019/9/15 上午12:11
 */
public class ResponseFilter extends ZuulFilter {
    // 定义返回需要的字段
    private static final String RESULT_CODE = "code";
    private static final String RESULT_MSG = "msg";
    private static final String RESULT_DATA = "data";

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * @description 判断返回类型若不为Result或AppException则报错
     * @param []
     * @return java.lang.Object
     * @author zhaozhonglong
     * @date  2019/9/15 00:30:29
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext rCtx = RequestContext.getCurrentContext();

        try {
            // 获取返回值内容，加以处理
            InputStream stream = rCtx.getResponseDataStream();
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));

            Result result = JSON.parseObject(body, Result.class);

            // 业务逻辑成功
            if (null == result.getCode()) {
               result.setCode("200");
               result.setMsg("成功");
               result.setData(JSONObject.parseObject(body));
            }

            rCtx.setResponseBody(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
