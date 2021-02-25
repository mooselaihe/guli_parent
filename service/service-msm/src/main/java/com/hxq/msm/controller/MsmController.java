package com.hxq.msm.controller;

import com.hxq.commonutils.R;
import com.hxq.msm.service.MsmService;
import com.hxq.msm.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/***
 *@author 何鑫强
 *@date 2020/12/27
 * 短信验证服务
 */
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/send/{phone}")
    public R send(@PathVariable String phone ){

        //1.从redis获取验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (StringUtils.isEmpty(code)){
            return R.ok();
        }
        //2 如果redis获取不到，进行阿里云发生
        //生成随机值，传递阿里云进行发送 四个字节的随机值
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        System.out.println("-------------------------------------"+code);
        //调用业务层的发送短信方法
        boolean isSend = msmService.send(param,phone);
        if(isSend){
            //发生成功，把发送成功验证码放到redis里面
            //设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }
    }
}
