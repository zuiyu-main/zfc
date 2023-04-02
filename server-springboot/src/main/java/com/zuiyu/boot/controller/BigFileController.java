package com.zuiyu.boot.controller;

import com.zuiyu.boot.service.FileStorageService;
import com.zuiyu.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zuiyu
 * @date 2023/3/16
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class BigFileController {
    public static Logger log = LoggerFactory.getLogger(BigFileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("/checkMd5")
    public HttpResponse<String> checkMd5(@RequestParam("filename")String filename,@RequestParam("md5")String md5){
        log.debug("文件MD5检查[{}]",md5);
        HttpResponse<String> httpResponse = new HttpResponse<>();
        httpResponse.setCode(HttpStatus.OK.value());
        httpResponse.setMsg(HttpStatus.OK.getReasonPhrase());
        Object o = redisTemplate.opsForHash().get(filename, md5);
        if (null == o){
            return httpResponse;
        }
        httpResponse.setData(o.toString());
        return httpResponse;
    }

    @RequestMapping("/chunk")
    public HttpResponse chunkUpload(@RequestParam("chunkFile") MultipartFile chunkFile,
                                    @RequestParam("chunkMd5") String chunkMd5,
                                    @RequestParam("index") Integer index,
                                    @RequestParam("chunkTotal")Integer chunkTotal,
                                    @RequestParam("fileSize")Long fileSize,
                                    @RequestParam("fileName")String fileName,
                                    @RequestParam("chunkSize")Long chunkSize){
        String[] splits = fileName.split("\\.");
        String type = splits[splits.length-1];
        String saveFileName = "/tmp"+chunkMd5+"."+type;
        Integer integer = fileStorageService.saveChunkFile(saveFileName, chunkFile, index, chunkSize);

        return null;

    }
}
