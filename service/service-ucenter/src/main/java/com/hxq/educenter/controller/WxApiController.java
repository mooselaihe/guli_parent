package com.hxq.educenter.controller;

import com.google.gson.Gson;
import com.hxq.commonutils.JwtUtils;
import com.hxq.educenter.entity.UcenterMember;
import com.hxq.educenter.service.UcenterMemberService;
import com.hxq.educenter.utils.ConstantWxUtils;
import com.hxq.educenter.utils.HttpClientUtils;
import com.hxq.servicebase.handler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

/***
 *@author 何鑫强
 *@date 2020/12/29
 */
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;
    //1生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode(){
        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        }catch (Exception e){
        }
        //设置请求路径参数
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );
        //重定向到请求微信地址中
        return "redirect:"+url;
    }

    //2.获取扫描人信息，添加数据
    @GetMapping("callback")
    public String callback(String code,String state){
        try{
            //1. 获取code值，临时票据
            //2. 拿者code请求 微信固定的地址，得到两个值access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数，id，密钥和code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //再次发送请求，期望得到  access_token和openid
            //把返回的字符串转换为map集合
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");

            //把扫描人信息添加数据库里面
            //判断数据表里面是否存在相同微信信息，根据openid判断
            UcenterMember member = memberService.getOpenIdMember(openid);
            if (member==null){
                //数据库中无该用户信息，在数据库中进行添加操作
                //3 拿着得到access_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                //获取返回值，userInfo用户信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                 String nickname= (String)userInfoMap.get("nickname");
                 String headimgurl = (String)userInfoMap.get("headimgurl");

                 member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //最后：返回到首页面 ，通过路径传递token字符串
            return "redirect:http://localhost:3000?token="+jwtToken;

        }catch (Exception e){
            throw new GuliException(20001,"登录失败");
        }

    }

}
