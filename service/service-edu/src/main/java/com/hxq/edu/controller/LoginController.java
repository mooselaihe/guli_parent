package com.hxq.edu.controller;

import com.hxq.commonutils.R;
import org.springframework.web.bind.annotation.*;

/***
 *@author 何鑫强
 *@date 2020/11/29
 */
@RestController
@RequestMapping("/edu/user")
@CrossOrigin
public class LoginController {

    //login
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3942751454,1089199356&fm=26&gp=0.jpg");
    }
}
