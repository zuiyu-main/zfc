package com.zuiyu.service;

import com.zuiyu.rest.action.FileHandlerEnum;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zuiyu
 * @date 2022/11/28
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public interface BaseFileConvertService {

    /**
     * 获取该操作支持的输入文件类型
     * @param actionNameEnum
     * @return
     */
    List<String> getIncludeType(FileHandlerEnum actionNameEnum);

    void doc2pdf(String sourceFilePath, String targetFilePath) throws Exception;

    void pdf2Text(String sourceFilePath, String targetFilePath,String targetFileType) throws Exception;

    /**
     * 获取 支持 fileHandlerEnum 的所有转换方式的并集
     * @param fileHandlerEnum
     * @return 所有转换方式支持的文件类型
     */
    static List<String> getSupportType(FileHandlerEnum fileHandlerEnum){
        List<String> includeType1 = new AsposeService().getIncludeType(fileHandlerEnum);
        List<String> includeType2 = new ItextService().getIncludeType(fileHandlerEnum);
        List<String> includeType3 = new PdfBoxService().getIncludeType(fileHandlerEnum);
        List<String> includeType4 = new SpirePdfService().getIncludeType(fileHandlerEnum);

        List<String> all = new LinkedList<>();
        if (!CollectionUtils.isEmpty(includeType1)){
            all.addAll(includeType1);
        }
        if (!CollectionUtils.isEmpty(includeType2)){
            all.addAll(includeType2);
        }
        if (!CollectionUtils.isEmpty(includeType3)){
            all.addAll(includeType3);
        }
        if (!CollectionUtils.isEmpty(includeType4)){
            all.addAll(includeType4);
        }
        return all.stream().distinct().collect(Collectors.toList());
    }
    /**
     * 转换方式总计数量
     */
    Integer DEFAULT_CONVERT_TYPE_SUM = 4;



}
