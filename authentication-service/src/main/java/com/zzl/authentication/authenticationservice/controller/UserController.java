package com.zzl.authentication.authenticationservice.controller;



import com.zzl.authentication.authenticationservice.entity.User;
import com.zzl.myredis.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaozhonglong
 * @description
 * @date 2019/5/6 下午9:45
 */
@Api(tags = "授权模块用户信息接口")
@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    /**
     * 获取授权用户的信息
     * @param user 当前用户
     * @return 授权信息
     */
    @ApiOperation(value = "获取公共的user信息", notes = "属于授权模块特有的信息")
    @GetMapping("/user")
    public Principal user(Principal user){
        System.out.println(RedisUtils.get("namess"));
        String name = "rmb";
        LOGGER.info("namess is {}" + name);
        LOGGER.info("zzlong test elk");
        return user;
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @RequestMapping("/getDemo")
    public Map<String , Object> getDemo() {
        User user = new User();
        user.setPassword("zzl");
        user.setUsername("hello");

        Map<String, Object> result = new HashMap<>();
        result.put("result", user);

        return result;
    }

    @ApiOperation(value = "根据用户ID查询用户", notes = "查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "用户姓名", required = false, paramType = "query", dataType = "String")
    })
    @RequestMapping("/user/{id}")
    public User getUserByIdAndName(@PathVariable("id") int id, @RequestParam("name") String name) {
        User user = new User();
        user.setUid("1112");
        user.setPassword("zzl-getUser");
        user.setUsername("hello-getUser");

        return user;
    }

}
