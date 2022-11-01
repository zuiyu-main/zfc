package com.zuiyu.rest.action.office;

import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.RestStatus;
import com.zuiyu.rest.action.FileTypeEnum;
import com.zuiyu.rest.response.TextRestResponse;
import com.zuiyu.service.AsposeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class Word2Pdf extends AbstractWordAction {
    public final Logger log = LoggerFactory.getLogger(getClass());


    public final List<String> include;


    public final static String DOC2PDF = "DOC2PDF";

    public Word2Pdf() {
        include = Arrays.asList(
                FileTypeEnum.DOC.name(),
                FileTypeEnum.DOCX.name(),
                FileTypeEnum.XML.name(),
                FileTypeEnum.TXT.name());
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
        return FileTypeEnum.PDF.name();
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
    public RestChannelConsumer doRequest(RestRequest request) throws Exception {
        log.debug("doRequest ======> info");
        File file = request.getFile();
        AsposeService.doc2pdf(file.getPath(),targetFile.getPath());
        return channel->channel.response(new TextRestResponse(RestStatus.OK,targetFile.getAbsolutePath()));



    }

}
