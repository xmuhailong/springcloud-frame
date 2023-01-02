package com.zzl.meetingroom.annotation;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/2/25 9:24 下午
 */
import java.lang.annotation.*;


@Target(ElementType.PARAMETER) // Annotation所修饰的对象范围:方法参数
@Retention(RetentionPolicy.RUNTIME)

public @interface LoginUser {
}
