package com.zzl.auth.persistence.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.zzl.auth.constant.RoleType;
import com.zzl.core.base.domain.user.SysPermission;
import com.zzl.core.base.domain.user.SysRole;
import com.zzl.auth.persistence.mapper.RolePermissionMapper;
import com.zzl.auth.persistence.mapper.SysRoleMapper;
import com.zzl.auth.persistence.mapper.UserRoleMapper;
import com.zzl.auth.persistence.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
//	@Autowired
//	private AmqpTemplate amqpTemplate;

	@Transactional
	@Override
	public void saveSysRole(SysRole sysRole) {
		if(this.isAdmin(sysRole.getCode())){
			throw new IllegalArgumentException("不能操作管理员");
		}
		if(!StringUtils.isEmpty(sysRole.getId())){
			if(StringUtils.isEmpty(sysRole.getName())) {
				throw new IllegalArgumentException("角色名不能为空");
			}
			sysRole.setUpdateTime(new Date());
			this.updateById(sysRole);
		}else {
			SysRole role = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("code", sysRole.getCode()));
			if (!StringUtils.isEmpty(role)) {
				throw new IllegalArgumentException("角色code已存在");
			}
			if (!StringUtils.isEmpty(sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("name", sysRole.getName())))) {
				throw new IllegalArgumentException("角色name已存在");
			}
			sysRole.setCreateTime(new Date());
			sysRole.setUpdateTime(sysRole.getCreateTime());
			sysRoleMapper.insert(sysRole);
		}
		log.info("保存角色：{}", sysRole);
	}

	private boolean isAdmin(String roleName){
		if (!StringUtils.isEmpty(roleName)){
			if(RoleType.SUPER_ADMIN.name().equals(roleName)){
				return true;
			}
		}
		return false;
	}


	@Transactional
	@Override
	public SysRole deleteRole(Long id) {
		SysRole sysRole = sysRoleMapper.selectById(id);
		if(this.isAdmin(sysRole.getCode())){
			throw new IllegalArgumentException("不能操作管理员");
		}
		if(!StringUtils.isEmpty(sysRole)){
			sysRoleMapper.deleteById(id);
			rolePermissionMapper.deleteRolePermission(id, null);
			userRoleMapper.deleteUserRole(null, id);
			log.info("删除角色：{}", sysRole);

			// 发布role删除的消息
//			amqpTemplate.convertAndSend(UserCenterMq.MQ_EXCHANGE_USER, UserCenterMq.ROUTING_KEY_ROLE_DELETE, id);

		}
		return sysRole;
	}

	/**
	 * 给角色设置权限
	 *
	 * @param roleId
	 * @param permissionIds
	 */
	@Transactional
	@Override
	public void setPermissionToRole(Long roleId, Set<Long> permissionIds) {
		SysRole sysRole = sysRoleMapper.selectById(roleId);
		if (sysRole == null) {
			throw new IllegalArgumentException("角色不存在");
		}
		if(this.isAdmin(sysRole.getCode())){
			throw new IllegalArgumentException("不能操作管理员");
		}

		// 查出角色对应的old权限
		Set<Long> oldPermissionIds = rolePermissionMapper.findPermissionsByRoleIds(Sets.newHashSet(roleId)).stream()
				.map(p -> p.getId()).collect(Collectors.toSet());

		// 需要添加的权限
		Collection<Long> addPermissionIds = org.apache.commons.collections4.CollectionUtils.subtract(permissionIds,
				oldPermissionIds);
		if (!CollectionUtils.isEmpty(addPermissionIds)) {
			addPermissionIds.forEach(permissionId -> {
				rolePermissionMapper.saveRolePermission(roleId, permissionId);
			});
		}
		// 需要移除的权限
		Collection<Long> deletePermissionIds = org.apache.commons.collections4.CollectionUtils
				.subtract(oldPermissionIds, permissionIds);
		if (!CollectionUtils.isEmpty(deletePermissionIds)) {
			deletePermissionIds.forEach(permissionId -> {
				rolePermissionMapper.deleteRolePermission(roleId, permissionId);
			});
		}

		log.info("给角色id：{}，分配权限：{}", roleId, permissionIds);
	}


	@Override
	public Set<SysPermission> findPermissionsByRoleId(Long roleId) {
		return rolePermissionMapper.findPermissionsByRoleIds(Sets.newHashSet(roleId));
	}

	@Override
	public Page<SysRole> findRolesByPage(Page<SysRole> sysRolePage, String name) {
		QueryWrapper<SysRole> ew = new QueryWrapper();
		if(!StringUtils.isEmpty(name)){
			ew.like("name",name);
		}
		return sysRolePage.setRecords(sysRoleMapper.selectPage(sysRolePage,ew).getRecords());
	}
}
