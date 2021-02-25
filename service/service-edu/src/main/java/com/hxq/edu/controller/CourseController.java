package com.hxq.edu.controller;


import com.hxq.commonutils.R;
import com.hxq.edu.entity.Course;
import com.hxq.edu.entity.vo.CourseInfoVo;
import com.hxq.edu.entity.vo.CoursePublishVo;
import com.hxq.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-08
 */
@RestController

@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    // TODO: 2020/12/19 完善分页查询，带分页
    @GetMapping
    public R getCourseList(){
        List<Course> list = courseService.list(null);
        return R.ok().data("list",list);
    }
    /**添加课程基本信息
     * @param courseInfoVo 表单中课程基本信息，封装在vo中，用json在前端传递
     * @return R 统一返回数据格式
     */
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确定信息 在最终发布页面
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo= courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");//设置课程表的一个是否发布的状态
        courseService.updateById(course);
        return R.ok();
    }

    //删除课程 多表删除，视频，小节，章节，描述
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

