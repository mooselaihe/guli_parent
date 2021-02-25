package com.hxq.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/***
 *@author 何鑫强
 *@date 2021/1/30
 */
@Component
@FeignClient("service-order")
public interface OrdersClient {
    //根据课程id和用户id查询订单表状态
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
