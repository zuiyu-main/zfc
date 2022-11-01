package com.zuiyu.boot.module;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class ConvertFileParams {

    private MultipartFile file;
    private FileHandlerEnum type;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public FileHandlerEnum getType() {
        return type;
    }

    public void setType(FileHandlerEnum type) {
        this.type = type;
    }
}
