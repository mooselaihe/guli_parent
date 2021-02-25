package com.hxq.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/***
 *@author 何鑫强
 *@date 2020/12/22
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.hxq"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.hxq.cms.mapper")
public class cmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(cmsApplication.class, args);
    }
}
