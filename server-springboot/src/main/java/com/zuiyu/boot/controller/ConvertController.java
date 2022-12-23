package com.zuiyu.boot.controller;

import com.zuiyu.boot.model.ConvertFileParams;
import com.zuiyu.boot.service.FileConvertService;
import com.zuiyu.response.HttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
     * @param params type {@link com.zuiyu.rest.action.FileHandlerEnum}
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/file")
    public HttpResponse<String> file( ConvertFileParams params) throws Exception {
        return new HttpResponse<>(fileConvertService.fileConvert(params));
    }

    @PostMapping(value = "/getSupportType")
    public HttpResponse<List<String>> getSupportType(ConvertFileParams params) throws Exception {
        return new HttpResponse<>(fileConvertService.getSupportType(params));
    }

}
