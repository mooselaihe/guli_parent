package com.hxq.educenter.service;

import com.hxq.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxq.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author hxq
 * @since 2020-12-27
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    //查询某一天注册人数
    Integer countRegisterDay(String day);
}
