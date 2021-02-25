package com.hxq.cms.service.impl;

import com.hxq.cms.entity.CrmBanner;
import com.hxq.cms.mapper.CrmBannerMapper;
import com.hxq.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author 何鑫强
 * @since 2020-12-22
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> selectAllBanner() {
        return null;
    }
}
