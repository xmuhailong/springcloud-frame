package com.zzl.auth.persistence.service.impl;

import com.zzl.auth.persistence.entity.OauthRefreshToken;
import com.zzl.auth.persistence.mapper.OauthRefreshTokenMapper;
import com.zzl.auth.persistence.service.IOauthRefreshTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaozhonglong
 * @since 2020-09-20
 */
@Service
public class OauthRefreshTokenServiceImpl extends ServiceImpl<OauthRefreshTokenMapper, OauthRefreshToken> implements IOauthRefreshTokenService {

}
