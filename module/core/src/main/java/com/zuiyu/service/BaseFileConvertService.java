package com.zuiyu.service;

import com.zuiyu.rest.action.FileHandlerEnum;

import java.util.List;

/**
 * @author zuiyu
 * @date 2022/11/28
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public interface BaseFileConvertService {


    List<String> getIncludeType(FileHandlerEnum actionNameEnum);

    void doc2pdf(String sourceFilePath, String targetFilePath) throws Exception;

    void pdf2doc(String sourceFilePath, String targetFilePath) throws Exception;


}
