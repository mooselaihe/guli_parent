package com.hxq.edu.controller;


import com.hxq.commonutils.R;
import com.hxq.edu.client.VodClient;
import com.hxq.edu.entity.Video;
import com.hxq.edu.entity.chapter.VideoVo;
import com.hxq.edu.service.VideoService;
import com.hxq.servicebase.handler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

    /**
     * 添加小节
     * @param video
     * @return
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video){
        videoService.save(video);
        return R.ok();
    }

    //删除小节 和里面视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //查询到视频id传入
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();

        if(!StringUtils.isEmpty(videoSourceId)){
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode()==20001){
                throw new GuliException(20001,"批量删除视频失败，熔断器");
            }
        }
        videoService.removeById(id);
        return R.ok();
    }
    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody Video video){
        videoService.updateById(video);
        return R.ok();
    }
}

