package com.zzl.db.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzl.db.user.entity.SmsVerify;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 验证码发送记录 Mapper 接口
 * </p>
 *
 * @author liugh
 * @since 2019-07-23
 */
@Mapper
public interface SmsVerifyMapper extends BaseMapper<SmsVerify> {

}
