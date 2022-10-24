package com.zuiyu.rest;

import com.zuiyu.rest.action.FileHandlerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author zuiyu
 * @date 2022/10/21
 * @description
 * @link https://github.com/zuiyu-main
 */
public abstract class BaseRestHandler implements RestHandler{

    public final Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 处理名称
     * @return
     */
    public abstract String getName();

    /**
     * 源文件类型
     * @return
     */
    public abstract String getSourceFileType();

    /**
     * 目标文件类型
     * @return
     */
    public abstract String getTargetFileType();

    /**
     * 源文件
     * @return
     */
    public abstract File getSourceFile();

    /**
     * 目标文件
     * @return
     */
    public abstract File getTargetFile();

    /**
     * 获取支持的类型
     * @return
     */
    public abstract List<String> getIncludeType();


    /**
     * 具体的执行方法
     * @param request
     * @throws IOException
     */
    public abstract void preRequest(RestRequest request) throws IOException;





    @Override
    public final void handleRequest(RestRequest request) throws Exception {
        log.debug("handleRequest ======> info");
        // 验证参数、校验类型是否支持
        List<String> includeType = getIncludeType();
        String name = request.getFile().getName();
        String fileType = name.substring(name.lastIndexOf("." )+1);
        boolean contains = includeType.contains(fileType.toUpperCase(Locale.ROOT));
        log.debug("文件类型校验 [{}] ",contains);
        if (!contains){
            throw new IllegalArgumentException(String.format("文件类型不支持:%s",fileType));
        }
        preRequest(request);
    }
}
