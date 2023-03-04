package com.zzl.auth.persistence.mapper;


import com.zzl.core.base.domain.user.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 用户角色关系<br>
 * 用户和角色是多对多关系，sys_role_user是中间表
 *
 * @author zhaozhonglong
 */
@Mapper
public interface UserRoleMapper {

	int deleteUserRole(@Param("userId") String userId, @Param("roleId") Long roleId);

	@Insert("insert into sys_role_user(id, userId, roleId) values(null,#{userId}, #{roleId})")
	int saveUserRoles(@Param("userId") String userId, @Param("roleId") Long roleId);


	/**
	 * 根据用户id获取角色
	 *
	 * @param userId
	 * @return
	 */
	@Select("select r.* from sys_role_user ru inner join sys_role r on r.id = ru.roleId where ru.userId = #{userId}")
	Set<SysRole> findRolesByUserId(String userId);

	@Select("select r.* from sys_role_user ru inner join sys_role r on r.id = ru.roleId where ru.userId = #{userId}")
	SysRole findRoleByUserId(String userId);

}
