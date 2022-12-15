package com.zuiyu.service;

import com.zuiyu.rest.action.FileHandlerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class ItextService implements BaseFileConvertService {
    public static final Logger log = LoggerFactory.getLogger(ItextService.class);
    private static final String COMPONENT_NAME = "Itext";

    private static final Map<FileHandlerEnum, List<String>> INCLUDE_TYPE_MAP =
            new HashMap<FileHandlerEnum, List<String>>() {{
                put(FileHandlerEnum.TEXT2PDF, Arrays.asList(

                ));

            }};

    @Override
    public List<String> getIncludeType(FileHandlerEnum fileHandlerEnum) {
        log.debug("[{}] getIncludeType by [{}]",COMPONENT_NAME,fileHandlerEnum.name());
        return INCLUDE_TYPE_MAP.get(fileHandlerEnum);
    }


    @Override
    public void doc2pdf(String sourceFilePath, String targetFilePath) throws Exception {
        log.info("Itext into ==>");
    }

    @Override
    public void pdf2doc(String sourceFilePath, String targetFilePath) throws Exception {

    }
}
