package com.zuiyu.boot.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zuiyu
 * @date 2023/3/16
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public interface FileStorageService {
    /**
     *
     * @param saveFilename 保存文件名称[/tmp/test.txt]
     * @param file 保存的文件
     * @param index 第几块
     * @param chunkSize 当前块的大小
     */
    Integer saveChunkFile( String saveFilename, MultipartFile file,Integer index,Long chunkSize);
}
