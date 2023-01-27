package com.zzl.db.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzl.core.base.constants.Constant;
import com.zzl.db.user.constants.RoleType;
import com.zzl.db.user.entity.AppUser;
import com.zzl.db.user.entity.UserThirdparty;
import com.zzl.db.user.mapper.UserThirdpartyMapper;
import com.zzl.db.user.service.IAppUserService;
import com.zzl.db.user.service.IUserThirdpartyService;
import com.zzl.db.user.vo.ThirdPartyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方用户表 服务实现类
 * </p>
 *
 * @author liugh
 */
@Service
public class UserThirdpartyServiceImpl extends ServiceImpl<UserThirdpartyMapper, UserThirdparty> implements IUserThirdpartyService {

    @Autowired
    private IAppUserService IAppUserService;

    @Override
    public AppUser insertThirdPartyUser(ThirdPartyUser param) throws Exception {

        AppUser appUser = new AppUser();
        appUser.setPassword(Constant.PASSWORD);
        appUser.setRePassword(Constant.PASSWORD);
        appUser.setUsername("gfc" + param.getOpenid());
        appUser.setRole(RoleType.USER.name());
        appUser.setType("4");
        appUser = IAppUserService.addAppUser(appUser);

        // 初始化第三方信息
        UserThirdparty thirdparty = new UserThirdparty();
        thirdparty.setProviderType(param.getProvider());
        thirdparty.setOpenId(param.getOpenid());
        thirdparty.setCreateTime(System.currentTimeMillis());
        thirdparty.setUserNo(appUser.getId());
        thirdparty.setStatus(1);

        this.baseMapper.insert(thirdparty);

        return appUser;
    }
}
