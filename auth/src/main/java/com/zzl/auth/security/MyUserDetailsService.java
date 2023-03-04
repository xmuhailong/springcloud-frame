package com.zzl.auth.security;

import com.zzl.core.base.enums.CredentialTypeEnum;
import com.zzl.auth.persistence.service.IAppUserService;
import com.zzl.core.base.domain.user.LoginAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description 需要改变认证的用户信息来源，我们可以实现 UserDetailsService
 * @author zhaozhonglong
 * @date  2021/1/16 22:02:59
 */
@Configuration
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private IAppUserService userClient;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 为了支持多类型登录，这里username后面拼装上登录类型,如username|type
        String[] params = username.split("\\|");
        username = params[0];// 真正的用户名或手机号

        LoginAppUser loginAppUser = null;
        if (params.length == 1 || (CredentialTypeEnum.USERNAME == CredentialTypeEnum.valueOf(params[1]))) {
            loginAppUser = userClient.findByUsername(username);
            if (loginAppUser == null) {
                throw new AuthenticationCredentialsNotFoundException("用户不存在");
            } else if (!loginAppUser.isEnabled()) {
                throw new DisabledException("用户已作废");
            }
        } else {
            // 登录类型
            CredentialTypeEnum credentialType = CredentialTypeEnum.valueOf(params[1]);
            if (CredentialTypeEnum.PHONE == credentialType) {// 短信登录
                loginAppUser = userClient.findByPhone(username);
                loginAppUser.setPassword(passwordEncoder.encode(username));
            } else if (CredentialTypeEnum.THIRD == credentialType) {// 第三方登陆
                loginAppUser = userClient.findByUsername(username);
                loginAppUser.setPassword(passwordEncoder.encode(username));
            }
        }

        return loginAppUser;
    }
}
