package com.zzl.db.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限标识
 * @author liugh
 *
 */
@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {

	private static final long serialVersionUID = 280565233032255804L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	private String permission;
	private String name;
	@TableField("create_time")
	private Long createTime;
	@TableField("update_time")
	private Long updateTime;

}
