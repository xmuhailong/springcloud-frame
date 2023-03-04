package com.zzl.auth.persistence.service.impl;

import com.zzl.auth.persistence.entity.OauthApprovals;
import com.zzl.auth.persistence.mapper.OauthApprovalsMapper;
import com.zzl.auth.persistence.service.IOauthApprovalsService;
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
public class OauthApprovalsServiceImpl extends ServiceImpl<OauthApprovalsMapper, OauthApprovals> implements IOauthApprovalsService {

}
