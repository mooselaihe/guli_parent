package com.hxq.edu.client;

import com.hxq.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/***
 *@author 何鑫强
 *@date 2020/12/21
 */
@Component
@FeignClient(name = "service-vod",fallback =  VodFileDegradeFeignClient.class)
public interface VodClient {
    //根据视频id删除阿里云视频
    //@PathVariable注解要制定参数名称
    @DeleteMapping("/eduVod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    //定义调用删除多个视频的方法
    //删除多个阿里云视频的方法
    //参数多个视频id  List videoIdList
    @DeleteMapping("/eduVod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
