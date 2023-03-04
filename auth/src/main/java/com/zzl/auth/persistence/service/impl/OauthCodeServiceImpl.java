package com.zzl.auth.persistence.service.impl;

import com.zzl.auth.persistence.entity.OauthCode;
import com.zzl.auth.persistence.mapper.OauthCodeMapper;
import com.zzl.auth.persistence.service.IOauthCodeService;
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
public class OauthCodeServiceImpl extends ServiceImpl<OauthCodeMapper, OauthCode> implements IOauthCodeService {

}
