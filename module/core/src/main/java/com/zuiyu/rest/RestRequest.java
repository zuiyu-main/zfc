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
    final private File file;
    /**
     * 输出文件地址
     */
    final private String targetFileDir;
    /**
     * 转换文件方式
     * {@link com.zuiyu.rest.action.ConvertTypeEnum}
     */
    final private String convertType;
    /**
     * 输出文件类型
     */
    final private String targetFileType;


    public RestRequest(String targetFileDir, File file, String convertType,String targetFileType) {
        this.file = file;
        this.targetFileDir = targetFileDir;
        this.convertType = convertType;
        this.targetFileType = targetFileType;
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

    public String getTargetFileType() {
        return targetFileType;
    }
}
