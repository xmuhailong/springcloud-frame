package com.zzl.auth.constant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 *
 * @ClassName: Constants<br>
 * @Description: 用户登录相关提示信息<br>
 */
public class Constants {

	public static final String COMMON_TIP = "用户名或密码输入错误";
	public static final String COMMON_LOCK_TIP = "该用户已经锁定，请联系管理员！";
	/** 该用户不存在！ */
	public static final String USER_NOT_FOUND="userNotFound";
	public static final String USER_NOT_FOUND_TIPS=COMMON_TIP;//"未获取到用户信息：请查看该用户以及部门信息是否完整！";
	/** 密码输入错误！ */
	public static final String PASSWORD_INVALID="passwordInvalid";
	public static final String PASSWORD_INVALID_TIPS=COMMON_TIP;//"密码输入错误！";
	/** 获取用户信息出错！ */
	public static final String GET_USER_ERROR="getUserError";
	public static final String GET_USER_ERROR_TIPS=COMMON_TIP;//"获取用户信息出错！";

	/**
	 * @Title: getReturnStr
	 * @Description: 生成返回json
	 * @param
	 * @return String
	 * @throws
	 */
	public static String getReturnStr(String key, String tipStr) {
		ReturnObj m = new ReturnObj(key, tipStr);
		return JSON.toJSONString(m,SerializerFeature.WriteMapNullValue);
	}

	static class ReturnObj{
		ReturnObj(String type, String tip){
			this.setType(type);
			this.setTip(tip);
		}
		private String type;
		private String tip;

		/**
		 * @return the tip
		 */
		public String getTip() {
			return tip;
		}
		/**
		 * @param tip the tip to set
		 */
		public void setTip(String tip) {
			this.tip = tip;
		}
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}

	}
}
