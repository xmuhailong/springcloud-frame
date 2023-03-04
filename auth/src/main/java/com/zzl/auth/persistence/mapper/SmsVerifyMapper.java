package com.zzl.auth.persistence.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzl.core.base.domain.user.SmsVerify;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 验证码发送记录 Mapper 接口
 * </p>
 *
 * @author zhaozhonglong
 * @since 2019-07-23
 */
@Mapper
public interface SmsVerifyMapper extends BaseMapper<SmsVerify> {

}
