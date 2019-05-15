package com.zzl.authentication.authenticationservice.service.impl;

import com.zzl.authentication.authenticationservice.constant.Constants;
import com.zzl.authentication.authenticationservice.entity.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("passwordService")
@Transactional
public class PasswordService {



	/**
	 * @Title: validateRules
	 * @Description: 登录时验证规则
	 * @param
	 * @return void
	 * @throws
	 */
	public void validateRules(User user, String password)
			throws RuntimeException {
		System.out.println("登录规则验证");
		// 用户密码
		String u_password = user.getPassword().toUpperCase();
		// 用户密码
		String c_password = password.toUpperCase();
		// 如果匹配成功
		if (u_password.equals(c_password)) {

		}else {
			throw new BadCredentialsException(Constants.getReturnStr(Constants.PASSWORD_INVALID,Constants.PASSWORD_INVALID_TIPS));
		}
	}

	/**
	 * @Title: modify
	 * @Description: 密码修改
	 * @param
	 * @return InvokeResult
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String modify(String logo_id, String oldPass, String newPass) {

		return "true";

	}

	/**
	 * @Title: validateChangePassword
	 * @Description: 密码修改规则
	 * @param
	 * @return void
	 * @throws
	 */
	public void validateChangePassword(String person,
			String password) throws RuntimeException {
		System.out.println("update");
	}






}
