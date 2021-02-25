package com.hxq.edu.service;

import com.hxq.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-08
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(String courseId);
}
