package com.zuiyu.boot.model;

import com.zuiyu.rest.action.FileHandlerEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class ConvertFileParams {
    /**
     * 待转换待文件
     */
    private MultipartFile file;
    /**
     * 执行转换的类型
     */
    private FileHandlerEnum type;
    /**
     * 使用的转换方式
     */
    private String convertFileType;

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

    public String getConvertFileType() {
        return convertFileType;
    }

    public void setConvertFileType(String convertFileType) {
        this.convertFileType = convertFileType;
    }
}
