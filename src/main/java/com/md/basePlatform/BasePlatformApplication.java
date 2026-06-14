package com.md.basePlatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 无人机信息管理系统后端启动类.
 */
@SpringBootApplication
@MapperScan("com.md.basePlatform.repository")
public class BasePlatformApplication {

    /**
     * 应用入口.
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(BasePlatformApplication.class, args);
    }

}
