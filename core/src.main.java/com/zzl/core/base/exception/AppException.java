package com.zzl.core.base.exception;


import com.zzl.core.base.enums.BaseEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaozhonglong
 * 2019-06-07 16:10
 */

@Data
@Slf4j
public class AppException extends RuntimeException{

    private String code;
    private String msg;
    private BaseEnum baseEnum;

    public AppException(BaseEnum baseEnum, String ... args) {
        super(renderMsg(baseEnum, args));

        this.code = baseEnum.code();
        this.baseEnum = baseEnum;
    }

    private static String renderMsg (BaseEnum baseEnum, String ... args) {
        try {
            return String.format(baseEnum.msg(), args);
        } catch (Exception ex) {
            log.error("渲染信息异常：" , ex);

            return baseEnum.msg();
        }
    }
}
