package com.zuiyu.boot.controller;

import com.zuiyu.boot.module.ConvertFileParams;
import com.zuiyu.boot.service.FileConvertService;
import com.zuiyu.response.HttpResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */

@RestController
@RequestMapping("/convert")
public class ConvertController {

    @Resource
    private FileConvertService fileConvertService;

    /**
     * 输入待转换文件接口
     * @param params type {@link com.zuiyu.boot.module.FileHandlerEnum}
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/file")
    public HttpResponse<String> file( ConvertFileParams params) throws Exception {
        return new HttpResponse<>(fileConvertService.fileConvert(params));
    }



}
