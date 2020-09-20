package com.zzl.db.user.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaozhonglong
 * @since 2020-09-20
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class OauthApprovals implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("userId")
    private String userId;

    @TableField("clientId")
    private String clientId;

    private String scope;

    private String status;

    @TableField("expiresAt")
    private LocalDateTime expiresAt;

    @TableField("lastModifiedAt")
    private LocalDateTime lastModifiedAt;


}
