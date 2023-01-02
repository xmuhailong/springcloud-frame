package com.zzl.db.user.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzl.db.user.entity.SysPermission;

import java.util.Map;
import java.util.Set;

public interface SysPermissionService extends IService<SysPermission> {

	/**
	 * 根绝角色ids获取权限集合
	 *
	 * @param roleIds
	 * @return
	 */
	Set<SysPermission> findByRoleIds(Set<Long> roleIds);

	void saveSysPermission(SysPermission sysPermission);

	void delete(Long id);

	Page<SysPermission> findPermissionsByPage(Page<SysPermission> sysPermissionPage, Map<String, Object> params);
}
