package com.zzl.db.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzl.db.user.entity.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppUserMapper extends BaseMapper<AppUser> {

	List<AppUser> findUsersByPage(Page<AppUser> appUserPage, Map<String, Object> params);

	AppUser findUserByUsername(@Param("username") String username);

	List<AppUser> findByNickName(String nickName);

	AppUser findUserByPhone(@Param("phone") String phone);
}
