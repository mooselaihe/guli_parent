package com.hxq.edu.service;

import com.hxq.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxq.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-08
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);


    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
