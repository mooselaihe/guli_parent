package com.hxq.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/***
 *@author 何鑫强
 *@date 2020/11/30
 */

public interface OssService {
    /**
     * 文件上传至阿里云
     *
     */
    String upload(MultipartFile file);
}
