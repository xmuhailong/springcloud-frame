package com.zzl.authentication.authenticationservice.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

/**
 * @description 添加CustomOauthException的序列化实现
 * @author zhaozhonglong
 * @date  2021/1/26 12:22:38
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    /**
     * @description 因用户名和密码不正确，抛出错误的时候，就会路由到这个地方
     * @param [value, gen, provider]
     * @return void
     * @author zhaozhonglong
     * @date  2021/1/26 16:41:51
     */
    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("code", String.valueOf(value.getHttpErrorCode()));
        gen.writeStringField("msg", value.getMessage());
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}
