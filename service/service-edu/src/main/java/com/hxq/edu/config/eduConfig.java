package com.hxq.edu.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 *@author 何鑫强
 *@date 2020/11/22
 */
@Configuration
public class eduConfig {
    /**
     * 分页插件
     */ @Bean public PaginationInterceptor paginationInterceptor() {     return new PaginationInterceptor();
    }

}
