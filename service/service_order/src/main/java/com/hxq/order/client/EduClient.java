package com.hxq.order.client;

import com.hxq.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/***
 *@author 何鑫强
 *@date 2021/1/29
 */
@Component
@FeignClient("service-edu")
public interface EduClient {
    @PostMapping("/edu/courseFront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}
