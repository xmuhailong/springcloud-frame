package com.zzl.authentication.authenticationservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 异常统一处理
 * @author zhaozhonglong
 * 2019-06-07 16:10
 */
@RestControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * @description 全局异常
     * @param [e]
     * @return com.zzl.base.domain.Result
     * @author zhaozhonglong
     * @date  2021/1/16 22:50:15
     */
    @ExceptionHandler(value = Exception.class)
    public Object handle(Exception e) {
        return "from auth.ExceptionHandle Exception" + e.getMessage();
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
    @ExceptionHandler(value = InvalidGrantException.class)
    public Object appExceptionHandler(InvalidGrantException e) {

        return "from auth.ExceptionHandle InvalidGrantException" + e.getMessage();
    }

}
