package com.zzl.auth.persistence.service.impl;

import com.zzl.auth.persistence.entity.User;
import com.zzl.auth.persistence.mapper.UserMapper;
import com.zzl.auth.persistence.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaozhonglong
 * @since 2020-09-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
