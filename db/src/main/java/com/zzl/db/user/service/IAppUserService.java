package com.zzl.db.user.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzl.db.user.entity.AppUser;
import com.zzl.db.user.entity.SysRole;
import com.zzl.db.user.vo.LoginAppUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IAppUserService extends IService<AppUser> {

	AppUser addAppUser(AppUser appUser)throws Exception;

	AppUser updateAppUser(AppUser appUser)throws Exception;

	LoginAppUser findByUsername(String username);

	void setRoleToUser(String id, Set<Long> roleIds)throws Exception;

	AppUser updatePassword(String id, String oldPassword, String newPassword)throws Exception;


	Set<SysRole> findRolesByUserId(String userId);

	void bindingPhone(String userId, String phone)throws Exception;

	Page<AppUser> findUsersByPage(Page<AppUser> appUserPage, Map<String, Object> params);

	SysRole findRoleByUserId(String id);

	AppUser deleteUser(AppUser appUser)throws Exception;

	List<AppUser> findByNickName(String nickName);

	LoginAppUser findByPhone(String phone);
}
