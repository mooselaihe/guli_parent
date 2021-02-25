package com.hxq.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxq.edu.entity.Subject;
import com.hxq.edu.entity.excel.SubjectData;
import com.hxq.edu.service.SubjectService;
import com.hxq.servicebase.handler.GuliException;

/***
 *@author 何鑫强
 *@date 2020/12/6
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    // 因为SubjectExcelListener不能交给spring进行管理，自己new，不能注入其他对象
    // 不能实现数据库操作
    public SubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }



    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) {
            throw new GuliException(20001,"文件数据为空");
        }

        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断一级分类是否重复
        Subject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(existOneSubject == null) { //没有相同一级分类，进行添加
            existOneSubject = new Subject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());//一级分类名称
            subjectService.save(existOneSubject);
        }

        //获取一级分类id值
        String pid = existOneSubject.getId();

        //添加二级分类
        //判断二级分类是否重复
        Subject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new Subject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());//二级分类名称
            subjectService.save(existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private Subject existOneSubject(SubjectService subjectService,String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        Subject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private Subject existTwoSubject(SubjectService subjectService,String name,String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        Subject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
