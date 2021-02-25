package com.hxq.staservice.client;

import com.hxq.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/***
 *@author 何鑫强
 *@date 2021/1/31
 */
@Component
@FeignClient("service-ucenter")  //可被启动类检测出来
public interface UcenterClient {
    //查询一天注册人数 制作图表使用
    @GetMapping("/educenter/ucenter-member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
