package com.hxq.edu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 *@author 何鑫强
 *@date 2020/12/6
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    //多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
