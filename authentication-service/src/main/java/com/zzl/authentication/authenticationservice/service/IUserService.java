package com.zzl.authentication.authenticationservice.service;


import com.zzl.authentication.authenticationservice.entity.User;

/**
 * @author zhaozhonglong
 * @description
 * @date 2019/3/10 下午12:41
 */

public interface IUserService {
    public User selectByUsername(String username);
}
