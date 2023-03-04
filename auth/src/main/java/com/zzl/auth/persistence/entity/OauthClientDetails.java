package com.zzl.auth.persistence.entity;

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
    public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

      private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;


}
