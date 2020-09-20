package com.zzl.db.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2020/9/20 11:51 上午
 */

@Configuration
@MapperScan("com.zzl.db.*.mapper.**")
@ComponentScan("com.zzl.db") // 此处为了在引用的地方扫描能够加载service文件夹下的类
public class MybatisPlusConfiguration {
    public MybatisPlusConfiguration() {
    }
}
