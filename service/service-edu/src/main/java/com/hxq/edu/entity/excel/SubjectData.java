package com.hxq.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/***
 *@author 何鑫强
 *@date 2020/12/6
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
