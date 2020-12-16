package com.example.demo.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ApolloApplication
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/8/20
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.example.demo.apollo.mapper"})
@EnableApolloConfig
public class ApolloApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApolloApplication.class, args);
    }
}
