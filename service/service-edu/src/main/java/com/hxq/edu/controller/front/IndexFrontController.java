package com.hxq.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxq.commonutils.R;
import com.hxq.edu.entity.Course;
import com.hxq.edu.entity.Teacher;
import com.hxq.edu.service.CourseService;
import com.hxq.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 *@author 何鑫强
 *@date 2020/12/25
 * 前端显示功能
 */
@RestController
@RequestMapping("/edu/indexFront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;
    //查询前八条热门课程
    @GetMapping("index")
    public R index(){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Course> eduList = courseService.list(wrapper);

        //查询前四名讲师
        QueryWrapper<Teacher> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        List<Teacher> teacherList = teacherService.list(wrapper1);
        return R.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}
