package com.hxq.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxq.commonutils.R;
import com.hxq.edu.entity.Teacher;
import com.hxq.edu.entity.vo.TeacherQuery;
import com.hxq.edu.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 何鑫强
 * @since 2020-11-21
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     *使用了统一返回数据格式
     * 查询所有教师
    */
    @GetMapping("/findAll")
    public R findAllTeacher(){
        List<Teacher> list = teacherService.list();
        return R.ok().data("items",list);
    }

    /**
     * 删除操作 路径传参
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id){

        boolean flag=teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page",value = "当前页码",required = true) @PathVariable long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable long limit
    ){
        Page<Teacher> page1= new Page<>(page,limit);
        //调用方法，底层封装，把分页所有的数据封装到page1对象里
        teacherService.page(page1,null);
        long total = page1.getTotal();
        List<Teacher> records =  page1.getRecords();
        return R.ok().data("total",total).data("rows",records);

    }

    @ApiOperation(value = "分页+查询 讲师列表")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery){

        //1.创建page对象
        Page<Teacher> page = new Page<>(current,limit);

        //构建条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        //多条件组合查询，类似于动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，拼接条件
        if(name!=null){
            wrapper.like("name",name);
        }
        if (level!=null){
            wrapper.eq("level",level);
        }
        if (begin!=null){
            wrapper.ge("gmt_create",begin);
        }
        if (end!=null){
            wrapper.le("gmt_create",end);
        }
        wrapper.orderByDesc("gmt_create");
        //2.调用方法实现条件查询分页
        teacherService.page(page,wrapper);
        long total = page.getTotal();
        List<Teacher> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }



    @ApiOperation(value = "添加讲师信息")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){

        boolean save = teacherService.save(teacher);
        if (save) {
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation(value = "查询讲师信息（通过id）")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    @ApiOperation(value = "更新讲师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }

    }

}

