package com.zzl.db.user.service.impl;

import com.zzl.db.user.entity.User;
import com.zzl.db.user.mapper.UserMapper;
import com.zzl.db.user.service.IUserService;
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
