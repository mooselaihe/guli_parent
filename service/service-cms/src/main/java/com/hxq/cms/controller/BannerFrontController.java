package com.hxq.cms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxq.cms.entity.CrmBanner;
import com.hxq.cms.service.CrmBannerService;
import com.hxq.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author 何鑫强
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/cms/bannerFront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有banner  key-value存储
    //@Cacheable(value = "banner",key = "'selectIndexList'")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        //根据id进行降序排列，显示limit 2
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法拼接sql语句
        wrapper.last("limit 2");
        List<CrmBanner> list = crmBannerService.list(wrapper);
        return R.ok().data("list",list);
    }
}

