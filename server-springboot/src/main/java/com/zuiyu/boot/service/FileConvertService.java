package com.zuiyu.boot.service;

import com.zuiyu.boot.factory.FileConvertFactory;
import com.zuiyu.boot.module.ConvertFileParams;
import com.zuiyu.boot.util.ZFileUtils;
import com.zuiyu.rest.BaseRestHandler;
import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.channel.DefaultRestChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@Service
public class FileConvertService {

    public final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${file.output.path}")
    private String outputDir;
    @Value("${file.output.host}")
    private String outputFileHost;
    public String fileConvert(ConvertFileParams params) throws Exception {
        logger.info("文件转换前准备,操作类型 [{}]",params.getType().name());
        MultipartFile mfile = params.getFile();
        try {
            File file = ZFileUtils.mFile2File(mfile);
            BaseRestHandler restHandler = FileConvertFactory.buildRestHandler(params.getType());
            RestRequest restRequest = new RestRequest(outputDir,file);
            DefaultRestChannel defaultRestChannel = new DefaultRestChannel(restRequest);
            restHandler.handleRequest(restRequest,defaultRestChannel);
            return buildResponse(defaultRestChannel.content);
        }catch (Exception e){
            logger.error("接收文件转换失败:",e);
            throw e;
        }
    }
    public String buildResponse(String content){
        return outputFileHost+content;
    }
}
