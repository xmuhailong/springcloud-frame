package com.zzl.db.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzl.core.base.enums.CredentialTypeEnum;
import com.zzl.core.base.enums.ResultEnum;
import com.zzl.core.base.enums.UserEnum;
import com.zzl.core.base.exception.AppException;
import com.zzl.core.base.utils.IdGenerate;
import com.zzl.core.base.utils.PhoneUtil;
import com.zzl.db.user.constants.RoleType;
import com.zzl.db.user.entity.AppUser;
import com.zzl.db.user.entity.SysPermission;
import com.zzl.db.user.entity.SysRole;
import com.zzl.db.user.mapper.AppUserMapper;
import com.zzl.db.user.mapper.UserCredentialsMapper;
import com.zzl.db.user.mapper.UserRoleMapper;
import com.zzl.db.user.service.IAppUserService;
import com.zzl.db.user.service.ISysPermissionService;
import com.zzl.db.user.service.ISysRoleService;
import com.zzl.db.user.vo.LoginAppUser;
import com.zzl.db.user.vo.UserCredential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

    @Autowired
    private AppUserMapper appUserMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ISysPermissionService ISysPermissionService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserCredentialsMapper userCredentialsDao;

    @Autowired
    private ISysRoleService sysRoleService;

    @Transactional
    @Override
    public AppUser addAppUser(AppUser appUser) throws Exception {
        String username = appUser.getUsername();
        if (StringUtils.isEmpty(username)) {
            throw new AppException(ResultEnum.NOT_NULL, "用户名");
        }

        // 防止用手机号直接当用户名，手机号要发短信验证
        if (PhoneUtil.checkPhone(username)) {
            throw new AppException(UserEnum.USERNAME_SHOULD_CONTAINS_ENGLISH_CHARACTERS);
        }

        // 防止用邮箱直接当用户名，邮箱也要发送验证（暂未开发）
        if (username.contains("@")) {
            throw new AppException(UserEnum.USERNAME_SHOULD_NOT_CONTAINS_SPECIAL_CHARACTERS, "@");
        }

        if (username.contains("|")) {
            throw new AppException(UserEnum.USERNAME_SHOULD_NOT_CONTAINS_SPECIAL_CHARACTERS, "|");
        }

        if (StringUtils.isEmpty(appUser.getNickname())) {
            appUser.setNickname(username);
        }

        if (StringUtils.isEmpty(appUser.getType())) {
            appUser.setType(appUser.getType());
        }

        if (appUser.getRole().equals(RoleType.SUPER_ADMIN.name())) {
            throw new AppException(UserEnum.CANNOT_SET_ADMIN);
        }

        SysRole role = sysRoleService.getOne(new QueryWrapper<SysRole>().eq("code", appUser.getRole()));
        if (StringUtils.isEmpty(role)) {
            throw new AppException(ResultEnum.NOT_EXIST, "角色");
        }

        appUser.setEnabled(appUser.getEnabled());
        appUser.setCreateTime(System.currentTimeMillis());
        appUser.setUpdateTime(appUser.getCreateTime());
        appUser.setOrganizationId(1);//随便默认值

        if (StringUtils.isEmpty(appUser.getId())) {
            if (StringUtils.isEmpty(appUser.getPassword())) {
                throw new AppException(ResultEnum.NOT_NULL, "密码");
            }

            if (!appUser.getPassword().equals(appUser.getRePassword())) {
                throw new AppException(UserEnum.PASSWORDS_NOT_INCONSISTENT);
            }

            // 加密密码
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            UserCredential userCredential = userCredentialsDao.findByUsername(appUser.getUsername());
            if (userCredential != null) {
                throw new AppException(UserEnum.USERNAME_ALREADY_EXISTS);
            }

            appUser.setId(IdGenerate.getNextId());
            appUserMapper.insert(appUser);
            userRoleMapper.saveUserRoles(appUser.getId(), role.getId());

            userCredentialsDao
                    .save(new UserCredential(appUser.getUsername(), CredentialTypeEnum.USERNAME.name(), appUser.getId()));

            log.info("添加用户：{}", appUser);
        } else {
            AppUser oldUser = appUserMapper.selectById(appUser.getId());
            Set<SysRole> oldRoles = userRoleMapper.findRolesByUserId(oldUser.getId());
            for (SysRole oldRole : oldRoles) {
                userRoleMapper.deleteUserRole(oldUser.getId(), oldRole.getId());
            }
            userRoleMapper.saveUserRoles(appUser.getId(), role.getId());
            userCredentialsDao.deleteByUserId(oldUser.getId());
            userCredentialsDao.save(new UserCredential(appUser.getUsername(), CredentialTypeEnum.USERNAME.name(), appUser.getId()));
            this.updateById(appUser);
            log.info("修改用户：{}", appUser);
        }

        return appUser;


    }

    @Transactional
    @Override
    public AppUser updateAppUser(AppUser appUser) {
        appUser.setUpdateTime(System.currentTimeMillis());
        appUserMapper.updateById(appUser);
        log.info("修改用户：{}", appUser);
        return appUser;
    }

    @Transactional
    @Override
    public LoginAppUser findByUsername(String username) {
        AppUser appUser = appUserMapper.findUserByUsername(username);
        return setUserRoles(appUser);
    }

    /**
     * 给用户设置角色<br>
     * 这里采用先删除老角色，再插入新角色
     */
    @Transactional
    @Override
    public void setRoleToUser(String id, Set<Long> roleIds) throws Exception {
        AppUser appUser = appUserMapper.selectById(id);
        if (appUser == null) {
            throw new AppException(ResultEnum.NOT_EXIST, "用户");
        }

        userRoleMapper.deleteUserRole(id, null);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                userRoleMapper.saveUserRoles(id, roleId);
            });
        }

        log.info("修改用户：{}的角色，{}", appUser.getUsername(), roleIds);
    }

    /**
     * 修改密码
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     */
    @Transactional
    @Override
    public AppUser updatePassword(String id, String oldPassword, String newPassword) throws Exception {
        AppUser appUser = appUserMapper.selectById(id);

        if (null == appUser) {
            throw new AppException(ResultEnum.NOT_EXIST, "用户");
        }

        if (this.isAdmin(appUser.getUsername())) {
            throw new AppException(UserEnum.CANNOT_RESET_ADMIN_PASSWORD);
        }

        if (!StringUtils.isEmpty(oldPassword)) {
            if (!passwordEncoder.matches(oldPassword, appUser.getPassword())) {
                throw new AppException(UserEnum.OLD_PASSWORD_ERROR);
            }
        }

        appUser.setPassword(passwordEncoder.encode(newPassword));
        this.updateById(appUser);

        log.info("修改密码：{}", appUser);

        return appUser;
    }

    @Override
    public Set<SysRole> findRolesByUserId(String userId) {
        return userRoleMapper.findRolesByUserId(userId);
    }

    private boolean isAdmin(String username) {
        if (!StringUtils.isEmpty(username)) {
            if ("admin".equals(username) || "superadmin".equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 绑定手机号
     */
    @Transactional
    @Override
    public void bindingPhone(String userId, String phone) throws Exception {
        UserCredential userCredential = userCredentialsDao.findByUsername(phone);
        if (userCredential != null) {
            throw new AppException(UserEnum.PHONE_HAS_USED);
        }

        AppUser appUser = appUserMapper.selectById(userId);
        appUser.setPhone(phone);

        updateAppUser(appUser);
        log.info("绑定手机号成功,username:{}，phone:{}", appUser.getUsername(), phone);

        // 绑定成功后，将手机号存到用户凭证表，后续可通过手机号+密码或者手机号+短信验证码登陆
        userCredentialsDao.save(new UserCredential(phone, CredentialTypeEnum.PHONE.name(), userId));
    }

    @Override
    public Page<AppUser> findUsersByPage(Page<AppUser> appUserPage, Map<String, Object> params) {
        List<AppUser> usersByPage = appUserMapper.findUsersByPage(appUserPage, params);
        for (AppUser appUser : usersByPage) {
            SysRole roleByUserId = userRoleMapper.findRoleByUserId(appUser.getId());
            appUser.setRole(roleByUserId.getCode());
        }
        return appUserPage.setRecords(usersByPage);
    }

    @Override
    public SysRole findRoleByUserId(String id) {
        return userRoleMapper.findRoleByUserId(id);
    }

    @Override
    @Transactional
    public AppUser deleteUser(AppUser appUser) throws Exception {
        if (this.isAdmin(appUser.getUsername())) {
            throw new AppException(UserEnum.CANNOT_DELETE_ADMIN);
        }

        appUserMapper.deleteById(appUser.getId());
        userCredentialsDao.deleteByUserId(appUser.getId());
        SysRole sysRole = userRoleMapper.findRoleByUserId(appUser.getId());
        userRoleMapper.deleteUserRole(appUser.getId(), sysRole.getId());
        return appUser;
    }

    @Override
    public List<AppUser> findByNickName(String nickName) {
        return appUserMapper.findByNickName(nickName);

    }

    @Transactional
    @Override
    public LoginAppUser findByPhone(String phone) {
        AppUser appUser = appUserMapper.findUserByPhone(phone);
        return setUserRoles(appUser);
    }

    private LoginAppUser setUserRoles(AppUser appUser) {
        if (StringUtils.isEmpty(appUser)) {
            return null;
        }
        LoginAppUser loginAppUser = new LoginAppUser();
        BeanUtils.copyProperties(appUser, loginAppUser);

        Set<SysRole> sysRoles = userRoleMapper.findRolesByUserId(appUser.getId());
        loginAppUser.setSysRoles(sysRoles);// 设置角色

        if (!CollectionUtils.isEmpty(sysRoles)) {
            Set<Long> roleIds = sysRoles.parallelStream().map(SysRole::getId).collect(Collectors.toSet());
            Set<SysPermission> sysPermissions = sysPermissionService.findByRoleIds(roleIds);
            if (!CollectionUtils.isEmpty(sysPermissions)) {
                Set<String> permissions = sysPermissions.parallelStream().map(SysPermission::getPermission)
                        .collect(Collectors.toSet());

                loginAppUser.setPermissions(permissions);// 设置权限集合
            }

        }

        return loginAppUser;
    }

}
