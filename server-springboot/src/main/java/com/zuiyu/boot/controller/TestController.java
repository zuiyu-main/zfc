package com.zuiyu.boot.controller;

import com.zuiyu.response.HttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zuiyu
 * @date 2022/11/3
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/get")
    public HttpResponse<String> get(){
        return new HttpResponse<>("");
    }
    @GetMapping("/getHaveParams")
    public HttpResponse<String> getHaveParams(@RequestParam String params){
        return new HttpResponse<>(params);
    }
    @PostMapping("/post")
    public HttpResponse<String> post(@RequestParam String params){
        return new HttpResponse<>(params);
    }
    @PostMapping("/postJson")
    public HttpResponse<String> postJson(@RequestBody String params){
        return new HttpResponse<>(params);
    }
    @PostMapping("/postFile")
    public HttpResponse<String> postJson(@RequestParam String params, MultipartFile file){
        return new HttpResponse<>(params+file.getOriginalFilename());
    }
}
