package com.hxq.edu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 *@author 何鑫强
 *@date 2020/12/15
 */
@Data
public class VideoVo {
    private String id;

    private String title;

    private String videoSourceId;//视频id

    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
