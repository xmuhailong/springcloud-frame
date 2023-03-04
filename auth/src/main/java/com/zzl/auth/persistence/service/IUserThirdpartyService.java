package com.zzl.auth.persistence.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzl.core.base.domain.user.AppUser;
import com.zzl.core.base.domain.user.UserThirdparty;
import com.zzl.core.base.domain.user.ThirdPartyUser;

/**
 * <p>
 * 第三方用户表 服务类
 * </p>
 *
 * @author zhaozhonglong
 */
public interface IUserThirdpartyService extends IService<UserThirdparty> {

    AppUser insertThirdPartyUser(ThirdPartyUser param)throws Exception;
}
