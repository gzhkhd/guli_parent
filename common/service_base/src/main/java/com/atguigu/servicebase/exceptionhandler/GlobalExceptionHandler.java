package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.commonutils.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Ret error(Exception e) {
        e.printStackTrace();
        return Ret.error().message("执行了全局异常处理类..");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public Ret error(GuliException e) {
        //使用工具类，异常信息详细写到日志文件中
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return Ret.error().message(e.getMsg()).code(e.getCode());
    }
}
