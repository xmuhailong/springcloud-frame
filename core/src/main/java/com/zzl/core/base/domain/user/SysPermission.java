package com.zzl.core.base.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限标识
 * @author zhaozhonglong
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
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;

}
