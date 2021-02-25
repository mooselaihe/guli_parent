package com.hxq.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxq.edu.entity.Course;
import com.hxq.edu.entity.CourseDescription;
import com.hxq.edu.entity.frontvo.CourseFrontVo;
import com.hxq.edu.entity.frontvo.CourseWebVo;
import com.hxq.edu.entity.vo.CourseInfoVo;
import com.hxq.edu.entity.vo.CoursePublishVo;
import com.hxq.edu.mapper.CourseMapper;
import com.hxq.edu.service.ChapterService;
import com.hxq.edu.service.CourseDescriptionService;
import com.hxq.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxq.edu.service.VideoService;
import com.hxq.servicebase.handler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-08
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    //注入小节和章节service
    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    //添加课程基本信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        //1.将实类信息保存到数据库中，课程信息存到课程表中
        //将CourseInfoVo对象转换成Course对象
        Course course = new Course();
        //前者转换为后者
        BeanUtils.copyProperties(courseInfoVo,course);
        int insert = baseMapper.insert(course);
        if (insert==0){
            //添加失败  new一个异常类
            throw new GuliException(20001,"添加课程信息失败");
        }
        //2.课程简介信息存入课程简介表中
        //获取添加之后的课程id然后根据id把课程简介加入到课程简介表中
        String cid = course.getId();
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(cid);
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    //根据课程id查询课程基本信息

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1.查询课程表
        Course course =baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);

        //2.查询描述表
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.修改课程表
        Course course =new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int update = baseMapper.updateById(course);
        if (update==0){
            throw new GuliException(20001,"修改课程信息失败");
        }
        //2.修改描述表
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publicCourseInfo = baseMapper.getPublicCourseInfo(id);
        return publicCourseInfo;
    }

    //删除课程 多表删除，视频，小节，章节，描述

    @Override
    public void removeCourse(String courseId) {

        //1.根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);
        //2.根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);
        //3.根据课程id删除描述
        courseDescriptionService.removeById(courseId);
        //4.根据课程id删除k课程本身
        int result = baseMapper.deleteById(courseId);
        if (result==0){
            //失败返回异常类
            throw new GuliException(20001,"删除失败");
        }

    }

    //条件查询带分页 获取课程信息，在前端显示
    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseFrontVo courseFrontVo) {
        //根据讲师id查询所讲课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            //一级分类
            //如果一级分类存在，从vo类把值作为条件查询
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            //价格
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage,wrapper);

        List<Course> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();//下一页
        boolean hasPrevious = coursePage.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String,Object>map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    //课程详情，封装到vo，在前端显示
    //根据课程id，编写sql语句查询课程信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
