package com.zzl.db.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzl.db.user.entity.SysPermission;
import com.zzl.db.user.mapper.RolePermissionMapper;
import com.zzl.db.user.mapper.SysPermissionMapper;
import com.zzl.db.user.service.ISysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	public Set<SysPermission> findByRoleIds(Set<Long> roleIds) {
		return rolePermissionMapper.findPermissionsByRoleIds(roleIds);
	}

	@Transactional
	@Override
	public void saveSysPermission(SysPermission sysPermission) {
		SysPermission permission = sysPermissionMapper.selectOne(new QueryWrapper<SysPermission>().eq("permission",sysPermission.getPermission()));
		if (permission != null) {
			throw new IllegalArgumentException("权限标识已存在");
		}
		sysPermission.setCreateTime(new Date());
		sysPermission.setUpdateTime(sysPermission.getCreateTime());

		sysPermissionMapper.insert(sysPermission);
		log.info("保存权限标识：{}", sysPermission);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		SysPermission permission = sysPermissionMapper.selectById(id);
		if (permission == null) {
			throw new IllegalArgumentException("权限标识不存在");
		}
		sysPermissionMapper.deleteById(id);
		rolePermissionMapper.deleteRolePermission(null, id);
		log.info("删除权限标识：{}", permission);
	}

	@Override
	public Page<SysPermission> findPermissionsByPage(Page<SysPermission> sysPermissionPage, Map<String, Object> params) {
		QueryWrapper<SysPermission> ew = new QueryWrapper();
		if(!StringUtils.isEmpty(params.get("permission"))){
			ew.like("permission",params.get("permission").toString());
		}
		if(!StringUtils.isEmpty(params.get("name"))){
			ew.like("name",params.get("name").toString());
		}
		return sysPermissionPage.setRecords(sysPermissionMapper.selectPage(sysPermissionPage,ew).getRecords());
	}
}
