package com.zzl.core.base.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2023/2/5 9:48 下午
 */
@Configuration
@MapperScan("com.zzl.**.mapper.**")
public class MybatisPlusConfig {
    public MybatisPlusConfig() {
    }
}
