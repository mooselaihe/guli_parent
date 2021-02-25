package com.hxq.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 *@author 何鑫强
 *@date 2020/12/20
 */
public interface VodService {
    String uploadVideoAly(MultipartFile file);

    void removeMoreAlyVideo(List<String> videoIdList);
}
