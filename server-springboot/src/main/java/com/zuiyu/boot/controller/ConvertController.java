package com.zuiyu.boot.controller;

import com.zuiyu.boot.model.ConvertFileParams;
import com.zuiyu.boot.service.FileConvertService;
import com.zuiyu.boot.util.ZDateUtils;
import com.zuiyu.response.HttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public HttpResponse<Map<String,String>> file( ConvertFileParams params) throws Exception {
        Map<String,String> result = new HashMap<>(16);
        result.put("date", ZDateUtils.LocalDateTime2String(LocalDateTime.now()));
        long start = System.currentTimeMillis();
        String url = fileConvertService.fileConvert(params);
        long time = (System.currentTimeMillis()-start)/1000;
        result.put("url",url);
        result.put("name",params.getFile().getOriginalFilename());
        result.put("time",time+"s");
        result.put("result", StringUtils.isEmpty(url)?"失败":"成功");
        result.put("type",params.getConvertFileType());
        return new HttpResponse<>(result);
    }

    @PostMapping(value = "/getSupportType")
    public HttpResponse<List<String>> getSupportType(ConvertFileParams params) throws Exception {
        return new HttpResponse<>(fileConvertService.getSupportType(params));
    }

}
