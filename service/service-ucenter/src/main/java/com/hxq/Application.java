package com.hxq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/***
 *@author 何鑫强
 *@date 2020/12/27
 */

@SpringBootApplication
@MapperScan("com.hxq.educenter.mapper")
@EnableDiscoveryClient  //nacos注册
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
