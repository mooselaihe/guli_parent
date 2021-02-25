package com.hxq.msm.service;

import java.util.Map;

/***
 *@author 何鑫强
 *@date 2020/12/27
 */
public interface MsmService {
    //发送短信
    boolean send(Map<String, Object> param, String phone);
}
