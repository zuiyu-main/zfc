package com.zuiyu.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
     * 获取暂不支持的类型
     * @return
     */
    public abstract List<String> getExcludeType();

    /**
     * 具体的执行方法
     * @param request
     * @throws IOException
     */
    public abstract void preRequest(RestRequest request) throws IOException;





    @Override
    public final void handleRequest(RestRequest request) throws Exception {
        log.info("handleRequest ======> info");
        // 验证参数、校验类型是否支持
        List<String> includeType = getIncludeType();
        preRequest(request);
    }
}
