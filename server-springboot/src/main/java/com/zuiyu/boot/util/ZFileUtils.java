package com.zuiyu.boot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@Component
public class ZFileUtils implements DisposableBean, InitializingBean {
    /**
     * 缓存文件地址
     */
    public static final String FILE_PATH = System.getProperty("user.dir")+File.separator+"tmp"+File.separator;
    /**
     * 源文件缓存删除标记
     */
    @Value("${file.cache.source.delete}")
    private Boolean fileCacheSourceDelete;
    /**
     * 目标转换文件删除标记
     */
    @Value("${file.cache.target.delete}")
    private Boolean fileTargetSourceDelete;
    /**
     * 目标文件输出路径
     */
    @Value("${file.output.path}")
    private String fileOutputPath;


    public static final Logger logger = LoggerFactory.getLogger(ZFileUtils.class);
    public static File mFile2File(MultipartFile mfile) throws IOException {
        String originalFilename = mfile.getOriginalFilename();
        File file = new File(FILE_PATH+originalFilename);
        if (file.exists()){
            file.delete();
            file.createNewFile();
        }
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
    public static void deleteTmpFile() throws IOException {
        deleteTmpFile(FILE_PATH);
    }

    /**
     *  删除缓存文件
     * @param path 要删除的文件夹路径或者文件路径
     * @throws IOException
     */
    public static void deleteTmpFile(String path) throws IOException {
        logger.warn("文件夹[{}]下所有文件（包括文件夹）即将被删除",path);

        Files.walkFileTree(Paths.get(path),
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file,
                                                     BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        logger.debug("文件[{}]被删除",file);
                        return FileVisitResult.CONTINUE;
                    }
                    @Override
                    public FileVisitResult postVisitDirectory(Path dir,
                                                              IOException exc) throws IOException {
                        Files.delete(dir);
                        logger.debug("文件夹[{}]被删除",dir);
                        return FileVisitResult.CONTINUE;
                    }

                }
        );

    }


    @Override
    public void destroy() throws Exception {
        logger.info("ZFileUtils 开始销毁，缓存文件开始删除," +
                "fileCacheSourceDelete [{}]:[{}]," +
                "fileTargetSourceDelete [{}]:[{}],",
                FILE_PATH,fileCacheSourceDelete,
                fileOutputPath,fileTargetSourceDelete);
        if (fileCacheSourceDelete){
            deleteTmpFile();
        }
        if (fileTargetSourceDelete){
            deleteTmpFile(fileOutputPath);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("ZFileUtils 初始化 加载缓存文件路径[{}]",FILE_PATH);
        File f = new File(FILE_PATH);
        if (!f.exists()){
            boolean mkdirs = f.mkdirs();
            logger.debug("缓存文件路径[{}]创建:[{}]",FILE_PATH,mkdirs);
        }
    }
}
