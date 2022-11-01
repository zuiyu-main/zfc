package com.zuiyu.boot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class ZFileUtils {
    /**
     * 缓存文件地址
     */
    public static final String FILE_PATH = System.getProperty("user.dir")+File.separator+"tmp";

    public static final Logger logger = LoggerFactory.getLogger(ZFileUtils.class);
    public static File mFile2File(MultipartFile mfile) throws IOException {
        String originalFilename = mfile.getOriginalFilename();
        File file = new File(FILE_PATH+originalFilename);
        mfile.transferTo(file);
        return file;
    }
    public static MultipartFile file2MFile(File file) throws IOException {
        return new MockMultipartFile("file", file.getName(), null, Files.newInputStream(file.toPath()));
    }

    public static String getFilePath(File file){
        return file.getParentFile().getAbsolutePath();
    }
    public static String getFilePath(MultipartFile file) throws IOException {
        return getFilePath(mFile2File(file));
    }
    public static String getFileName(File file){
        return file.getName().substring(0,file.getName().lastIndexOf("."));
    }
    public static String getFileName(MultipartFile file) throws IOException {
        return getFileName(mFile2File(file));
    }

    /**
     * 获取缓存文件地址
     * @return
     */
    public static String getTmpDir(){
        File file = new File(FILE_PATH);
        if (!file.exists()){
            file.mkdir();
        }
        return FILE_PATH;
    }

    /**
     * 删除缓存文件
     * @throws IOException
     */
    public static void deleteTmpFile() throws IOException {
//        Files.delete(Paths.get(FILE_PATH));
        logger.warn("即将进行删除文件夹[{}]下所有文件",FILE_PATH);
    }
    public static void main(String[] args) throws IOException {


        System.out.println();
        deleteTmpFile();
    }
}
