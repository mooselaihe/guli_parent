package com.hxq.oss.controller;

import com.hxq.commonutils.R;
import com.hxq.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/***
 *@author 何鑫强
 *@date 2020/11/30
 */
@RestController
@RequestMapping("/edu/oss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像
    @PostMapping("upload")
    public R uploadOssFile(MultipartFile file){
        //获取上传文件 MultipartFile
         String  url =ossService.upload(file);
        return R.ok().data("url",url);
    }
}
