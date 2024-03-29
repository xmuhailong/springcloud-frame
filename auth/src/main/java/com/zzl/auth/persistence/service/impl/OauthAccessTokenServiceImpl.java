package com.zzl.auth.persistence.service.impl;

import com.zzl.auth.persistence.entity.OauthAccessToken;
import com.zzl.auth.persistence.mapper.OauthAccessTokenMapper;
import com.zzl.auth.persistence.service.IOauthAccessTokenService;
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
public class OauthAccessTokenServiceImpl extends ServiceImpl<OauthAccessTokenMapper, OauthAccessToken> implements IOauthAccessTokenService {

}
