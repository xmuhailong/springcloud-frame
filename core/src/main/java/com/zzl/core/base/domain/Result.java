package com.zzl.core.base.domain;

import lombok.Data;

/**
 * @author zhaozhonglong
 * 2019-06-07 16:10
 */
@Data
public class Result<T> {

    /** 错误码. */
    private String code;

    /** 提示信息. */
    private String msg;

    /** 具体的内容. */
    private T data;
}
