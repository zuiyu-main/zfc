package com.zuiyu.boot.controller;

import com.zuiyu.response.HttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public HttpResponse<String> fileNotFound(IOException fe){
        HttpResponse<String> httpResponse = new HttpResponse<>();
        httpResponse.setCode(500);
        httpResponse.setMsg("转换失败");
        httpResponse.setData(fe.getMessage());
        return httpResponse;
    }
}
