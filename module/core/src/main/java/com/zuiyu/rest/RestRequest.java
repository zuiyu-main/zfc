package com.zuiyu.rest;

import java.io.File;

/**
 * @author zuiyu
 * @date 2022/10/21
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class RestRequest {

    /**
     * 待处理源文件
     */
    private final File file;
    /**
     * 输出文件地址
     */
    private final String targetFileDir;
    /**
     * 转换文件方式
     * {@link com.zuiyu.rest.action.ConvertTypeEnum}
     */
    private final String convertType ;


    public RestRequest(String targetFileDir,File file,String convertType) {
        this.file = file;
        this.targetFileDir = targetFileDir;
        this.convertType = convertType;
    }


    public File getFile() {
        return file;
    }

    public String getTargetFileDir() {
        return targetFileDir;
    }

    public String getConvertType() {
        return convertType;
    }
}
