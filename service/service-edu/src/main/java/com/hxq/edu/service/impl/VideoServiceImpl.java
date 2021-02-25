package com.hxq.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxq.edu.client.VodClient;
import com.hxq.edu.entity.Video;
import com.hxq.edu.mapper.VideoMapper;
import com.hxq.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-08
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {


    @Autowired
    private VodClient vodClient;

    // TODO: 2020/12/19 删除对应视频文件
    /**
     * 根据课程id删除数据，
     * @param courseId
     */
    @Override
    public void removeVideoByCourseId(String courseId) {

        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        videoWrapper.select("video_source_id");
        List<Video> videoList = baseMapper.selectList(videoWrapper);

        // List<EduVideo>变成List<String>
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            Video eduVideo = videoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)) {
                //放到videoIds集合里面
                videoIds.add(videoSourceId);
            }
        }
        //根据多个视频id删除多个视频
        if(videoIds.size()>0) {
            vodClient.deleteBatch(videoIds);
        }

        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
