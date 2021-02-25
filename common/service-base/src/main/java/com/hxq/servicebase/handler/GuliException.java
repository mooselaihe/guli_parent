package com.hxq.servicebase.handler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 *@author 何鑫强
 *@date 2020/11/22
 *
 * @AllArgsConstructor 有参构造
 * @NoArgsConstructor   无参构造
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException {
    @ApiModelProperty(value = "状态码")
    private Integer code;
    private String msg;

}
