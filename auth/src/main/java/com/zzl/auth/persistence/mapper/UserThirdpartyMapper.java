package com.zzl.auth.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzl.core.base.domain.user.UserThirdparty;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 第三方用户表 Mapper 接口
 * </p>
 *
 * @author zhaozhonglong
 * @since 2018-07-24
 */
@Mapper
public interface UserThirdpartyMapper extends BaseMapper<UserThirdparty> {

}
