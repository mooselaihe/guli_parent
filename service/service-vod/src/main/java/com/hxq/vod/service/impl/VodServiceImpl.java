package com.hxq.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.hxq.servicebase.handler.GuliException;
import com.hxq.vod.service.VodService;
import com.hxq.vod.utils.ConstantVodUtils;
import com.hxq.vod.utils.InitVodCilent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/***
 *@author 何鑫强
 *@date 2020/12/20
 */
@Service
public class VodServiceImpl implements VodService {

    /**
     * 本地上传到 阿里云点播视频
     * @param file
     * @return
     */
    @Override
    public String uploadVideoAly(MultipartFile file) {

        try{
        //fileName:上传文件原始名称
        String fileName = file.getOriginalFilename();
        //上传之后的显示名称
        String title = fileName.substring(0,fileName.lastIndexOf("."));
        //inputStream: 上传文件输入流
        InputStream inputStream = file.getInputStream();
        UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET,title,fileName,inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        String videoId = null;

            if(response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {
        try{
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //转化videoList值格式
            String videoIds = StringUtils.join(videoIdList.toArray(),",");
            request.setVideoIds(videoIds);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"批量删除视频失败");
    }
}
}
