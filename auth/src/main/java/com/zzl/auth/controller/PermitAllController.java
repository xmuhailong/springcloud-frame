package com.zzl.auth.controller;

import com.zzl.core.base.domain.ResultHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 登录后，无论是否有权限，都可以获取的接口
 * 注意： 一定要登录后
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2023/1/30 9:28 下午
 */
@RestController
public class PermitAllController {

    @ApiOperation(value = "获取公共菜单", notes = "不需要特殊授权")
    @PermitAll
    @GetMapping("/common-menu")
    public ResultHelper getCommonMenu() {
        Map<String, String> menus = new HashMap<>();

        menus.put("main", "first");

        return ResultHelper.succeed(menus);
    }
}
