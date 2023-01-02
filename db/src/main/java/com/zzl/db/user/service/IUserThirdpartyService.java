package com.zzl.db.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzl.db.user.entity.AppUser;
import com.zzl.db.user.entity.UserThirdparty;
import com.zzl.db.user.vo.ThirdPartyUser;

/**
 * <p>
 * 第三方用户表 服务类
 * </p>
 *
 * @author liugh
 */
public interface IUserThirdpartyService extends IService<UserThirdparty> {

    AppUser insertThirdPartyUser(ThirdPartyUser param)throws Exception;
}
