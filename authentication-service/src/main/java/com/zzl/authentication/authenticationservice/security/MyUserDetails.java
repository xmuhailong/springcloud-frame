package com.zzl.authentication.authenticationservice.security;


import com.zzl.authentication.authenticationservice.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author zhaozhonglong
 * @description
 * @date 2019/5/12 下午1:16
 */

public class MyUserDetails implements UserDetails {

    private static final long seriaVersionUID=1L;

    private String userName;

    private String password;

    private User user;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 重写getAuthorities方法，将用户的角色作为权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO 后续带完善
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SUPER");
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }



    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
