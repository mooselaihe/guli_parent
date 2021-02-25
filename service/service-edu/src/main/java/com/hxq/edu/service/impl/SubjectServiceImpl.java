package com.hxq.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxq.edu.entity.Subject;
import com.hxq.edu.entity.excel.SubjectData;
import com.hxq.edu.entity.subject.OneSubject;
import com.hxq.edu.entity.subject.TwoSubject;
import com.hxq.edu.listener.SubjectExcelListener;
import com.hxq.edu.mapper.SubjectMapper;
import com.hxq.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-06
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

//    @Autowired
//    private SubjectMapper subjectMapper;
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //分级显示课程 (树形)
    @Override
    public List<OneSubject> getAllSubject() {
        //1、查询所有一级分类
        QueryWrapper<Subject>wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<Subject>onSubjectList = baseMapper.selectList(wrapperOne);
        //2、查询所有二级分类
        QueryWrapper<Subject>wrapperOTwo = new QueryWrapper<>();
        wrapperOTwo.ne("parent_id","0");
        List<Subject>twoSubjectList = baseMapper.selectList(wrapperOTwo);
        //3、封装一级分类
        List<OneSubject>list = new ArrayList<>();
        for (Subject subject:onSubjectList) {
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(subject.getId());
            oneSubject.setTitle(subject.getTitle());
            list.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //遍历二级分类list集合
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //获取每个二级分类
                Subject tSubject = twoSubjectList.get(m);
                //判断二级分类parentid和一级分类id是否一样
                if(tSubject.getParentId().equals(subject.getId())) {
                    //把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }
        //4、封装二级

        return list;
    }
}
