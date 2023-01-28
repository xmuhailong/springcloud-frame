CREATE TABLE `clientdetails` (
  `appId` varchar(255) COLLATE utf8_bin NOT NULL,
  `resourceIds` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `appSecret` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `scope` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `grantTypes` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `redirectUrl` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `authorities` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additionalInformation` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `autoApproveScopes` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `user_name` blob,
  `client_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `oauth_approvals` (
  `userId` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `clientId` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `scope` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastModifiedAt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `resource_ids` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `client_secret` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `scope` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `authorized_grant_types` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `authorities` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) COLLATE utf8_bin DEFAULT NULL,
  `autoapprove` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `user_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `client_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `oauth_code` (
  `code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;