package com.zzl.db.user.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzl.db.user.entity.SysPermission;
import com.zzl.db.user.entity.SysRole;

import java.util.Set;

public interface SysRoleService extends IService<SysRole> {

	void saveSysRole(SysRole sysRole);

	SysRole deleteRole(Long id);

	void setPermissionToRole(Long id, Set<Long> permissionIds);

	Set<SysPermission> findPermissionsByRoleId(Long roleId);

	Page<SysRole> findRolesByPage(Page<SysRole> sysRolePage, String name);
}
