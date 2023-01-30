package com.zzl.core.base.exception;

import com.zzl.core.base.domain.ResultHelper;
import com.zzl.core.base.enums.ResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultHelper badRequestException(IllegalArgumentException exception) {
		return ResultHelper.failed2Msg(exception.getMessage());
	}

	@ExceptionHandler({AppException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultHelper badGFCException(AppException exception) {
		return ResultHelper.failed2Msg((ResultEnum) exception.getBaseEnum());
	}

	@ExceptionHandler({Exception.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultHelper badException(Exception exception) {
		return ResultHelper.failed2Msg(exception.getMessage());
	}
}
