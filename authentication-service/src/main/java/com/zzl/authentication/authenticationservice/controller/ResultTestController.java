package com.zzl.authentication.authenticationservice.controller;

import com.zzl.base.domain.Result;
import com.zzl.base.utils.ResultUtil;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhonglong
 * @description
 * @date 2019/6/7 下午4:40
 */

@RestController
public class ResultTestController {
    public Result say() {
        return ResultUtil.success();
    }
}
