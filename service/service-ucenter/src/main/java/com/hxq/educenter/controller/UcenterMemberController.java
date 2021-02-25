package com.hxq.educenter.controller;


import com.hxq.commonutils.JwtUtils;
import com.hxq.commonutils.R;
import com.hxq.commonutils.ordervo.UcenterMemberOrder;
import com.hxq.educenter.entity.UcenterMember;
import com.hxq.educenter.entity.vo.RegisterVo;
import com.hxq.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author hxq
 * @since 2020-12-27
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    //登录接口
    @PostMapping("login")
    public R login(@RequestBody UcenterMember member){
        //member对象封装手机号
        //返回token用jwt生成
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    //注册接口
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库工具用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    //根据用户id获取用户信息对象
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
        //把member对象里的值复制给UcenterMemberOrder对象
        UcenterMemberOrder memberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,memberOrder);
        return memberOrder;
    }

    //查询一天注册人数 制作图表使用
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
         Integer count =memberService.countRegisterDay(day);
         return R.ok().data("countRegister",count);
    }
}

