package com.zuiyu.boot.exception;

import com.zuiyu.response.HttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public HttpResponse<String> noHandlerFoundException(NoHandlerFoundException fe){
        HttpResponse<String> httpResponse = new HttpResponse<>();
        httpResponse.setCode(404);
        httpResponse.setMsg(fe.getMessage());
        System.out.println("文件已失效");
        return httpResponse;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public HttpResponse<String> illegalArgumentException(IllegalArgumentException fe){
        HttpResponse<String> httpResponse = new HttpResponse<>();
        httpResponse.setCode(500);
        httpResponse.setMsg(fe.getMessage());
        return httpResponse;
    }
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public HttpResponse<String> fileNotFound(IOException fe){
        HttpResponse<String> httpResponse = new HttpResponse<>();
        httpResponse.setCode(500);
        httpResponse.setMsg(fe.getMessage());
        return httpResponse;
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpResponse<String> exception(Exception fe){
        HttpResponse<String> httpResponse = new HttpResponse<>();
        httpResponse.setCode(500);
        httpResponse.setMsg(fe.getMessage());
        return httpResponse;
    }
}
