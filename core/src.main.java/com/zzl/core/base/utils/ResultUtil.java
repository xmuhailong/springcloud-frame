package com.zzl.core.base.utils;


import com.zzl.base.domain.Result;
import com.zzl.base.enums.ResultEnum;

/**
 * @author zhaozhonglong
 * 2019-06-07 16:10
 */
public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);

        return result;
    }

    public static Result success() {
        Result result = new Result();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(null);

        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();

        result.setCode(code);
        result.setMsg(msg);

        return result;
    }

    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();

        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());

        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();

        result.setCode("");
        result.setMsg(msg);

        return result;
    }
}
