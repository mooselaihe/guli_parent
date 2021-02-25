package com.hxq.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/***
 *@author 何鑫强
 *@date 2021/1/30
 */
@SpringBootApplication      //启动类标配
@ComponentScan(basePackages = {"com.hxq"})  //扫描所有配置类
@EnableDiscoveryClient      //是能够让注册中心能够发现，扫描到该服务。
@EnableFeignClients         //远程服务注册
@MapperScan("com.hxq.staservice.mapper")
@EnableScheduling
public class StaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
