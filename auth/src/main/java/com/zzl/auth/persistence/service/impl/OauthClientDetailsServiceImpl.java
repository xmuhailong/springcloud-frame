package com.zzl.auth.persistence.service.impl;

import com.zzl.auth.persistence.entity.OauthClientDetails;
import com.zzl.auth.persistence.mapper.OauthClientDetailsMapper;
import com.zzl.auth.persistence.service.IOauthClientDetailsService;
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
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {

}
