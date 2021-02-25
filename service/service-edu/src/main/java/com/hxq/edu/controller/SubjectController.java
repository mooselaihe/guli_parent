package com.hxq.edu.controller;


import com.hxq.commonutils.R;
import com.hxq.edu.entity.subject.OneSubject;
import com.hxq.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-06
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //查询所有分类
        List<OneSubject> list = subjectService.getAllSubject();
        return R.ok().data("list",list);
    }
}

