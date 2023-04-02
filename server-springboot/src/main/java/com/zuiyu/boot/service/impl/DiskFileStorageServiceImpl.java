package com.zuiyu.boot.service.impl;

import com.zuiyu.boot.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author zuiyu
 * @date 2023/3/16
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@Service
public class DiskFileStorageServiceImpl implements FileStorageService {

    public final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * @param filepath
     * @param saveFilename
     * @param file
     * @param index
     * @param chunkSize
     */
    @Override
    public Integer saveChunkFile(String saveFilename, MultipartFile file, Integer index, Long chunkSize) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(saveFilename, "rw")) {

            // 偏移量
            long offset = (long) chunkSize * (index - 1);
            // 定位到该分片的偏移量
            randomAccessFile.seek(offset);
            // 写入
            randomAccessFile.write(file.getBytes());

            return 1;

        } catch (IOException e) {
            log.error("缓存磁盘文件失败:",e);
            return 0;
        }
    }
}
