package com.zuiyu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class ItextService extends BaseFileConvertService{
    public static final Logger log = LoggerFactory.getLogger(ItextService.class);

    @Override
    public void doc2pdf(String sourceFilePath, String targetFilePath) throws Exception {
        log.info("Itext into ==>");
    }
}
