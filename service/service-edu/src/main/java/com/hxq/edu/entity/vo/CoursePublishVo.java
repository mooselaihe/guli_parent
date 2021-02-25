package com.hxq.edu.entity.vo;

import lombok.Data;

/***
 *@author 何鑫强
 *@date 2020/12/18
 */
@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
