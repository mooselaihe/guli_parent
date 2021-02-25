package com.hxq.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxq.edu.entity.Teacher;
import com.hxq.edu.mapper.TeacherMapper;
import com.hxq.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author 何鑫强
 * @since 2020-11-21
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    //分页查询前端显示教师列表
    @Override
    public Map<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher
        baseMapper.selectPage(pageTeacher,wrapper);

        List<Teacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        //是否有下页
        boolean hasNext = pageTeacher.hasNext();
        //是否有上一页
        boolean hasPrevious = pageTeacher.hasPrevious();

        //把分页数据获取，放到map
        Map<String,Object> map =new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }




}
