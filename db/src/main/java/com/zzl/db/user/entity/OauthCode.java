package com.zzl.db.user.entity;

import java.sql.Blob;
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
    public class OauthCode implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private Blob authentication;


}
