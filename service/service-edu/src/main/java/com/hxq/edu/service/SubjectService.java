package com.hxq.edu.service;

import com.hxq.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxq.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-06
 */
public interface SubjectService extends IService<Subject> {

    //添加课程分类
    void saveSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubject> getAllSubject();
}
