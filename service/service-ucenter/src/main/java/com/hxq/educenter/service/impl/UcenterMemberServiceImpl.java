package com.hxq.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxq.commonutils.JwtUtils;
import com.hxq.educenter.entity.UcenterMember;
import com.hxq.educenter.entity.vo.RegisterVo;
import com.hxq.educenter.mapper.UcenterMemberMapper;
import com.hxq.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxq.educenter.utils.MD5;
import com.hxq.servicebase.handler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author hxq
 * @since 2020-12-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String>redisTemplate;

    //登录
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //判断所传递的值非空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        //判断手机号码正确
        QueryWrapper<UcenterMember>wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember==null){
            throw new GuliException(20001,"登录失败");
        }

        //判断密码
        //把输入的密码进行，md5加密，再与数据库进行比较
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"登录失败");
        }
        //判断用户是否禁用
        if (mobileMember.getIsDisabled()){
            throw new GuliException(20001,"登录失败");
        }

        //进过一系列判断，登录成功
        //生成token字符，使用jwt工具类,传递id与昵称生成jwt
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if (StringUtils.isEmpty(mobile) ||StringUtils.isEmpty(password)
                || StringUtils.isEmpty(nickname) ||StringUtils.isEmpty(code)){
            throw new GuliException(20001,"注册失败");
        }
        //判断验证码,在redis里获取验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new  GuliException(20001,"验证码错误");
        }

        //p判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count>0){
            throw new GuliException(20001,"手机号重复，注册失败");
        }

        //注册成功，数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        //用户默认头像
        member.setAvatar("http://edu-4122.oss-cn-hangzhou.aliyuncs.com/2020/12/01/c79038914a08434d9a08c21689002dd1%E9%99%88%E7%91%B6.jpg");
        baseMapper.insert(member);

    }

    //根据openid在数据库是否存在
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
    //查询某一天注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
