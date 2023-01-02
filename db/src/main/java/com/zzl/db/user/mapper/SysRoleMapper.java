package com.zzl.db.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzl.db.user.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


	@Select("select * from sys_role t where t.code = #{code}")
	SysRole findByCode(String code);

}
