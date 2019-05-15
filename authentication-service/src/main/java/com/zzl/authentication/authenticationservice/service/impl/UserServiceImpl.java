package com.zzl.authentication.authenticationservice.service.impl;

import com.zzl.authentication.authenticationservice.entity.User;
import com.zzl.authentication.authenticationservice.mapper.UserMapper;
import com.zzl.authentication.authenticationservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhaozhonglong
 * @description
 * @date 2019/3/10 下午12:40
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByUsername(String username) {

        return userMapper.selectByUsername(username);
    }
}
