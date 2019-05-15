package com.zzl.authentication.authenticationservice.constant;

public class StaticParams {

	public static class USERROLE {
		public static final String ROLE_ADMIN = "ROLE_ADMIN";
		public static final String ROLE_USER = "ROLE_USER";
		public static final String ROLE_SUPER = "ROLE_SUPER";
	}

	public static class PATHREGX {

		private final static String getPathRex(String path) {
			return "/" + path + "/**";
		}

		public static final String ALL = getPathRex(PATH.ALL);
		public static final String VIEW = getPathRex(PATH.VIEW);
		public static final String ADMIN = getPathRex(PATH.ADMIN);
		public static final String API = getPathRex(PATH.API);
		public static final String RESOURCE = getPathRex(PATH.RESOURCE);
		public static final String STATIC = getPathRex(PATH.STATIC);
		public static final String JS = getPathRex(PATH.JS);
		public static final String CSS = getPathRex(PATH.CSS);
		public static final String IMG = getPathRex(PATH.IMG);
	}

	public static class PATH {
		public static final String ALL = "**";
		public static final String VIEW = "view";
		public static final String ADMIN = "admin";
		public static final String API = "api";
		public static final String RESOURCE = "resource";
		public static final String STATIC = "static";
		public static final String JS = "js";
		public static final String CSS = "css";
		public static final String IMG = "img";
		public static final String WEBSOCKET_SERVICE = "websocket-service";
		public static final String WEBSOCKET = "websocket";
	}

	/**
	 *
	 * @Package: com.jb.auth.sso<br>
	 * @ClassName: SWAGGERUI<br>
	 * @Description: swagger限制地址<br>
	 */
	public static class SWAGGERUI {
		public static final String MAIN = "/**/swagger-ui.html";
		public static final String RESOURCES = "/**/swagger-resources/**";
		public static final String API_DOCS = "/**/api-docs";
		public static final String STATIC_RESOURCES = "/**/webjars/springfox-swagger-ui/**";

		/**
		 *
		 * @Title: getSwaggerResource
		 * @Description: 获取swagger对应ui资源
		 * @return
		 */
		public static String[] getSwaggerResource() {
			return new String[] { MAIN, RESOURCES, API_DOCS, STATIC_RESOURCES};
		}
	}

}
