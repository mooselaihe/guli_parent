package com.hxq.edu.client;

import com.hxq.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/***
 *@author 何鑫强
 *@date 2020/12/21
 * 服务熔断器的实现类
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
