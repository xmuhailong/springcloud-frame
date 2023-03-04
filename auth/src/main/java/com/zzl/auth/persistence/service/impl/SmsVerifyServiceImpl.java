package com.zzl.auth.persistence.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzl.core.base.domain.user.SmsVerify;
import com.zzl.auth.persistence.mapper.SmsVerifyMapper;
import com.zzl.auth.persistence.service.ISmsVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 验证码发送记录 服务实现类
 * </p>
 *
 * @author zhaozhonglong123
 */
@Slf4j
@Service
public class SmsVerifyServiceImpl extends ServiceImpl<SmsVerifyMapper, SmsVerify> implements ISmsVerifyService {

    @Override
    public List<SmsVerify> getByMobileAndCaptchaAndType(String mobile, String captcha, Integer type) {

        return this.list(
                Wrappers.<SmsVerify>lambdaQuery()
                        .eq(SmsVerify::getMobile, mobile)
                        .eq(SmsVerify::getSmsVerify, captcha)
                        .eq(SmsVerify::getSmsType, type)
        );
    }
}
