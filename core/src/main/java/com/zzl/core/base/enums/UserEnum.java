package com.zzl.core.base.enums;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2023/1/2 5:59 下午
 */
public enum UserEnum implements BaseEnum{
    USERNAME_SHOULD_CONTAINS_ENGLISH_CHARACTERS("1100", "用户名要包含英文字符"),
    USERNAME_SHOULD_NOT_CONTAINS_SPECIAL_CHARACTERS("1101", "用户名不能包含特殊字符：[%s]"),
    CANNOT_SET_ADMIN("1102", "不能添加管理员"),
    OLD_PASSWORD_ERROR("1103", "旧密码错误"),
    PASSWORDS_NOT_INCONSISTENT("1104", "两次密码不一致"),
    USERNAME_ALREADY_EXISTS("1105", "用户名已存在"),
    CANNOT_DELETE_ADMIN("1106", "不能删除管理员"),
    PHONE_HAS_USED("1107", "手机号已被绑定"),
    CANNOT_RESET_ADMIN_PASSWORD("1108", "不能重置管理员密码"),

    ;

    private String code;

    private String msg;

    UserEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String code() {
        return null;
    }

    @Override
    public String msg() {
        return null;
    }
}
