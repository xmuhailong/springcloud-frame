package com.zzl.core.base.enums;

/**
 * @author zhaozhonglong
 * 2019-06-07 16:10
 */

public enum ResultEnum implements BaseEnum{

    SUCCESS("0000", "成功"),
    UNKNOW_ERROR("9999", "未知异常"),

    NOT_NULL("0001", "[%s]不能为空"),
    NOT_EXIST("0002", "[%s]不存在"),


    // 1000错误为系统错误
    DEAL_RESPONSE_ERROR("1000", "处理返回报文出错"),

    // 2000错误为业务逻辑错误
    CREATE_PASSWORD_ERROR("2000", "创建用户密码出错"),
    USERNAME_OR_PASSWORD_ERROR("2001", "用户名或密码错误"),
    CLIENT_AUTHENTICATION_FAILED("2002", "客户端认证失败"),
    CLIENT_AUTHENTICATION_MISSING("2003", "客户端信息缺失"),
    OAUTH_ACCESS_RESOURCE_INSUFFICIENT_AUTHORITY("2004", "资源权限不足"),
    OAUTH_ACCESS_RESOURCE_TOKEN_INVALID("2005", "未带token或token无效"),
    OAUTH_ACCESS_DENIED("2006", "不能访问，权限不足"),

    ;

    private String code;

    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
