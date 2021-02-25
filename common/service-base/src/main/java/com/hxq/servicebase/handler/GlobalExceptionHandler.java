package com.hxq.servicebase.handler;

import com.hxq.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 *@author 何鑫强
 *@date 2020/11/22
 *
 * 统一异常处理类
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    //指定出现什么异常执行此方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    /**
     * 特定异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行自定义异常");
    }

    /**
     * 自定义异常处理
     * @param e 自定义异常类
     * @return
     */
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode()); }

}
