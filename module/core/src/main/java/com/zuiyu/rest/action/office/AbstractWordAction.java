package com.zuiyu.rest.action.office;

import com.zuiyu.rest.BaseRestHandler;
import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.action.ConvertTypeEnum;
import com.zuiyu.rest.action.FileTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;
import java.util.Locale;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public abstract class AbstractWordAction extends BaseRestHandler {
    public final Logger log = LoggerFactory.getLogger(getClass());
    public String sourceFileType;
    public File sourceFile;
    public File targetFile;

    @Override
    public final RestChannelConsumer preRequest(RestRequest request, ConvertTypeEnum convertTypeEnum) throws Exception {

        log.debug("preRequest ======> info");
        String targetFileDir = request.getTargetFileDir();
        File file = new File(targetFileDir);
        if (!file.exists()){
            boolean mkdirs = file.mkdirs();
            log.warn("缓存文件夹[{}]不存在，默认创建[{}]",targetFileDir,mkdirs);
        }
        String outputPath;
        if (targetFileDir.endsWith("/")){
            outputPath = targetFileDir + LocalDate.now()+File.separator;
        }else{
            outputPath = targetFileDir + File.separator + LocalDate.now() + File.separator;
        }

        File f1 = new File(outputPath);
        if (!f1.exists()){
            boolean mkdir = f1.mkdir();
            log.warn("缓存文件夹[{}]不存在，默认创建[{}]",outputPath,mkdir);

        }
        String name = request.getFile().getName();
        String filename = name.substring(0, name.lastIndexOf("."));
        String targetFileName=filename+"."+ FileTypeEnum.PDF.name().toLowerCase(Locale.ROOT);
        sourceFileType = name.substring(name.lastIndexOf(".")+1);
        if (request.getTargetFileDir().endsWith("/")){
            targetFile = new File(outputPath+targetFileName);
        }else{
            targetFile = new File(outputPath+File.separator+targetFileName);
        }
        log.debug("原文件名:{}",request.getFile().getName());
        log.debug("原文件地址:{}",request.getFile().getAbsolutePath());
        log.debug("目标文件名:{}",targetFileName);
        log.debug("目标文件地址:{}",targetFile.getAbsolutePath());
        log.info("source [{}],target [{}]",request.getFile().getAbsolutePath(),targetFile.getAbsolutePath());
        return doRequest(request,convertTypeEnum);
    }

    public abstract RestChannelConsumer doRequest(RestRequest request,ConvertTypeEnum convertTypeEnum) throws Exception;

}
