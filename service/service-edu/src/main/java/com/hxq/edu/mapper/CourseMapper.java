package com.hxq.edu.mapper;

import com.hxq.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxq.edu.entity.frontvo.CourseWebVo;
import com.hxq.edu.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-12-08
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    public CoursePublishVo getPublicCourseInfo(String courseId);

    //根据课程id，编写sql语句查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
