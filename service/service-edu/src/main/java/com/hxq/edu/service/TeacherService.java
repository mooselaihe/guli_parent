package com.hxq.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxq.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxq.edu.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 何鑫强
 * @since 2020-11-21
 */
public interface TeacherService extends IService<Teacher> {

    //分页查询
    Map<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher);
    //void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery);

}
