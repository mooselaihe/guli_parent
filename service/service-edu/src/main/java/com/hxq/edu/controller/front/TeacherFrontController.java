package com.hxq.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxq.commonutils.R;
import com.hxq.edu.entity.Course;
import com.hxq.edu.entity.Teacher;
import com.hxq.edu.service.CourseService;
import com.hxq.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/***
 *@author 何鑫强
 *@date 2021/1/12
 */
@RestController
@RequestMapping("/edu/teacherFront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    //1、分页查询讲师方法
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit){
        Page<Teacher>pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2.讲师详情的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        //1.根据讲师ID查询讲师基本信息
        Teacher teacher = teacherService.getById(teacherId);
        //2.根据讲师ID查询所讲课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<Course> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
