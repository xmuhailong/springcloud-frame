package com.zzl.auth.persistence.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzl.core.base.domain.user.SysPermission;
import com.zzl.core.base.domain.user.SysRole;

import java.util.Set;

public interface ISysRoleService extends IService<SysRole> {

	void saveSysRole(SysRole sysRole);

	SysRole deleteRole(Long id);

	void setPermissionToRole(Long id, Set<Long> permissionIds);

	Set<SysPermission> findPermissionsByRoleId(Long roleId);

	Page<SysRole> findRolesByPage(Page<SysRole> sysRolePage, String name);
}
