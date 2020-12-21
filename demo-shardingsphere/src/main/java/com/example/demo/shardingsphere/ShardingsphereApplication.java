package com.example.demo.shardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ShardingsphereApplication
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/8/3
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.example.demo.shardingsphere.mapper"})
public class ShardingsphereApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereApplication.class, args);
    }
}