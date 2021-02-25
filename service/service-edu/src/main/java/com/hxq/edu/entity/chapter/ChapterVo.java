package com.hxq.edu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 *@author 何鑫强
 *@date 2020/12/15
 */
@Data
public class ChapterVo {
    private String id;



    private String title;
    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
