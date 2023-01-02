package com.zzl.authentication.authenticationservice.service;

import com.zzl.authentication.authenticationservice.utils.LoginModel;
import com.zzl.authentication.authenticationservice.utils.ResultHelper;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2023/1/2 10:33 上午
 */
public interface IAuthService {
    ResultHelper getTokenResult(LoginModel loginModel);
}
