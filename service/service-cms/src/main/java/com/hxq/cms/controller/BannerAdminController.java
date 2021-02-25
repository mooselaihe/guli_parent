package com.hxq.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxq.cms.entity.CrmBanner;
import com.hxq.cms.service.CrmBannerService;
import com.hxq.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * 后台接口
 * </p>
 *
 * @author 何鑫强
 * @since 2020-12-22
 */
@RestController
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询banner
    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("/pageBanner{page}/{limit}")
    public R index(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        crmBannerService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());

    }

    //添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    //根据id查询
    @GetMapping("get/{id}")
    public R get(@PathVariable String id){
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item",banner);
    }

    //修改banner
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner){
        crmBannerService.updateById(banner);
        return R.ok();
    }

    //删除banner
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id){
        crmBannerService.removeById(id);
        return R.ok();
    }










}

