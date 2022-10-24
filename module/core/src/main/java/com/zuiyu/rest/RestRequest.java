package com.zuiyu.rest;

import java.io.File;

/**
 * @author zuiyu
 * @date 2022/10/21
 * @description
 * @link https://github.com/zuiyu-main
 */
public class RestRequest {

    private final String path;

    private final File file;


    public RestRequest(String path, File file) {
        this.path = path;
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

}
