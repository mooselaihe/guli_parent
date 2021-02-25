package com.hxq.edu.controller;


import com.hxq.commonutils.R;
import com.hxq.edu.entity.Chapter;
import com.hxq.edu.entity.chapter.ChapterVo;
import com.hxq.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    //课程大纲列表,根据课程id进行查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    //根据id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){

        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //修改章节
    @GetMapping("updateChapter")
    public R updateChapter(@RequestBody Chapter chapter ){
        chapterService.updateById(chapter);
        return R.ok();
    }

    //删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapterById(@PathVariable String chapterId){

        boolean flag =  chapterService.deleteChapter(chapterId);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }


    }

}

