package com.zzl.auth.persistence.service.impl;

import com.zzl.auth.persistence.entity.OauthClientToken;
import com.zzl.auth.persistence.mapper.OauthClientTokenMapper;
import com.zzl.auth.persistence.service.IOauthClientTokenService;
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
public class OauthClientTokenServiceImpl extends ServiceImpl<OauthClientTokenMapper, OauthClientToken> implements IOauthClientTokenService {

}
