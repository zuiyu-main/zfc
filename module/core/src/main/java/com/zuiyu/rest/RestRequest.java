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
    private final String targetFileDir;


    public RestRequest(String targetFileDir,File file) {
        this.file = file;
        this.targetFileDir = targetFileDir;
    }


    public File getFile() {
        return file;
    }

    public String getTargetFileDir() {
        return targetFileDir;
    }
}
