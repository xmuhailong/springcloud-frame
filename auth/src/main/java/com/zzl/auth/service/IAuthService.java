package com.zzl.auth.service;

import com.zzl.core.base.domain.ResultHelper;
import com.zzl.db.user.vo.LoginModel;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2023/1/2 10:33 上午
 */
public interface IAuthService {
    ResultHelper getTokenResult(LoginModel loginModel);
}
