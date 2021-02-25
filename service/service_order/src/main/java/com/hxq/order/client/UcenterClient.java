package com.hxq.order.client;

import com.hxq.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/***
 *@author 何鑫强
 *@date 2021/1/29
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //根据用户id获取用户信息对象
    @PostMapping("/educenter/ucenter-member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
