package com.zzl.authentication.authenticationservice.controller;



import com.zzl.authentication.authenticationservice.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaozhonglong
 * @description
 * @date 2019/5/6 下午9:45
 */

@RestController
public class UserController {

    /**
     * 获取授权用户的信息
     * @param user 当前用户
     * @return 授权信息
     */
    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }

    @RequestMapping("/getDemo")
    public Map<String , Object> getDemo() {
        User user = new User();
        user.setPassword("zzl");
        user.setUsername("hello");

        Map<String, Object> result = new HashMap<>();
        result.put("result", user);

        return result;
    }

}
