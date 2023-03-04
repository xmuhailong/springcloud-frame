package com.zzl.core.base.domain;

import com.zzl.core.base.enums.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhaozhonglong 66864662@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("统一返回json对象")
public class ResultHelper<T> implements Serializable {

    @ApiModelProperty(value = "统一返回数据")
    private T data;
    @ApiModelProperty(value = "统一返回状态码")
    private String respCode;
    @ApiModelProperty(value = "统一返回说明信息")
    private String respMsg;

    public static <T> ResultHelper<T> succeed2Msg(String msg) {
        return succeedWith(null, ResultEnum.SUCCESS.code(), msg);
    }

    public static <T> ResultHelper<T> succeed(T model, String msg) {
        return succeedWith(model, ResultEnum.SUCCESS.code(), msg);
    }

    public static <T> ResultHelper<T> succeed(T model) {
        return succeedWith(model, ResultEnum.SUCCESS.code(), ResultEnum.SUCCESS.msg());
    }

    public static <T> ResultHelper<T> succeedWith(T datas, String code, String msg) {
        return new ResultHelper<>(datas, code, msg);
    }

    public static <T> ResultHelper<T> failed2Msg(String msg) {
        return failedWith(null, ResultEnum.UNKNOW_ERROR.code(), msg);
    }

    public static <T> ResultHelper<T> failed2Msg(ResultEnum resultEnum) {
        return failedWith(null, resultEnum.code(), resultEnum.msg());
    }

    public static <T> ResultHelper<T> failed(T model, String msg) {
        return failedWith(model, ResultEnum.UNKNOW_ERROR.code(), msg);
    }


    public static <T> ResultHelper<T> failedWith(T datas, String code, String msg) {
        return new ResultHelper<>(datas, code, msg);
    }

}
