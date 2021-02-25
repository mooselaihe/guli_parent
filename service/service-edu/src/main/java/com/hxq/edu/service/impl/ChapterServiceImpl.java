package com.hxq.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxq.edu.entity.Chapter;
import com.hxq.edu.entity.Video;
import com.hxq.edu.entity.chapter.ChapterVo;
import com.hxq.edu.entity.chapter.VideoVo;
import com.hxq.edu.mapper.ChapterMapper;
import com.hxq.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxq.edu.service.VideoService;
import com.hxq.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-08
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;//注入小节service
    //课程大纲列表,根据课程id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1.根据课程id查询课程里面所有章节
        QueryWrapper<Chapter>wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapperChapter);

        //2.根据课程id查询课程里面所有小节
        QueryWrapper<Video>wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<Video> videoList  = videoService.list(wrapperVideo);
        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < chapterList.size(); i++) {
            //每个章节
            Chapter eduChapter = chapterList.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终list集合
            finalList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVo> videoListVo = new ArrayList<>();

            //4 遍历查询小节list集合，进行封装
            for (int m = 0; m < videoList.size(); m++) {
                //得到每个小节
                Video video = videoList.get(m);
                //判断：小节里面chapterid和章节里面id是否一样
                if (video.getChapterId().equals(eduChapter.getId())){
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    //放到小节封装集合
                    videoListVo.add(videoVo);
                }

            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoListVo);

            }
        return finalList;
    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterId章节id 查询小节表，如果有数据则不进行删除
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count>0){
            //有数据，有小节
            throw new GuliException(20001,"不能删除");
        }else {
            //无数据，进行删除
            int result = baseMapper.deleteById(chapterId);
            //成功 1》0
            return result>0;
        }
    }

    /**
     * 根据课程id删除数据
     * @param courseId
     */
    @Override
    public void removeChapterByCourseId(String courseId) {

        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }


}
