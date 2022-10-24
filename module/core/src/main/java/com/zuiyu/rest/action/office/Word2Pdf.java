package com.zuiyu.rest.action.office;

import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.action.FileHandlerTypeEnum;
import com.zuiyu.service.AsposeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link https://github.com/zuiyu-main
 */
public class Word2Pdf extends AbstractWordAction {
    public final Logger log = LoggerFactory.getLogger(getClass());

    private String sourceFileType;
    private File sourceFile;
    private File targetFile;

    private final List<String> include;


    public final static String DOC2PDF = "DOC2PDF";

    public Word2Pdf() {
        include = Arrays.asList(
                FileHandlerTypeEnum.DOC.name(),
                FileHandlerTypeEnum.DOCX.name(),
                FileHandlerTypeEnum.XML.name(),
                FileHandlerTypeEnum.TXT.name());
    }

    @Override
    public String getName() {
        return DOC2PDF;
    }

    @Override
    public String getSourceFileType() {
        return sourceFileType;
    }

    @Override
    public String getTargetFileType() {
        return FileHandlerTypeEnum.PDF.name();
    }

    @Override
    public File getSourceFile() {
        return sourceFile;
    }

    @Override
    public File getTargetFile() {
        return targetFile;
    }

    @Override
    public List<String> getIncludeType() {
        return include;
    }



    @Override
    public void doRequest(RestRequest request) throws IOException {
        log.debug("doRequest ======> info");
        File file = request.getFile();
        String name = request.getFile().getName();
        String filename = name.substring(0, name.lastIndexOf("."));
        targetFile = new File(request.getFile().getParentFile().getPath()+"/"+filename+".pdf");
        log.debug("原文件名:{}",request.getFile().getAbsolutePath());
        log.debug("原文件夹地址:{}",request.getFile().getParent());
        log.debug("目标文件名:{}",filename);
        log.debug("目标文件地址:{}",targetFile.getAbsolutePath());
        log.info("source file [{}],target file [{}]",file.getAbsolutePath(),targetFile.getAbsolutePath());
        AsposeService.doc2pdf(file.getPath(),targetFile.getPath());



    }

}
