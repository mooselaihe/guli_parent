package com.hxq.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxq.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxq.edu.entity.frontvo.CourseFrontVo;
import com.hxq.edu.entity.frontvo.CourseWebVo;
import com.hxq.edu.entity.vo.CourseInfoVo;
import com.hxq.edu.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-08
 */
public interface CourseService extends IService<Course> {

    //添加课程基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    //删除课程 多表删除，视频，小节，章节，描述
    void removeCourse(String courseId);

    //获取课程信息，在前端显示
    Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseFrontVo courseFrontVo);

    //课程详情，封装到vo，在前端显示
    CourseWebVo getBaseCourseInfo(String courseId);
}
