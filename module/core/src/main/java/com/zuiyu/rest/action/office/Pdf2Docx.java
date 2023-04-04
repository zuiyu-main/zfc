package com.zuiyu.rest.action.office;

import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.RestStatus;
import com.zuiyu.rest.action.ConvertTypeEnum;
import com.zuiyu.rest.action.FileHandlerEnum;
import com.zuiyu.rest.response.TextRestResponse;
import com.zuiyu.service.BaseFileConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * @author zuiyu
 * @date 2023/1/8
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class Pdf2Docx extends AbstractWordAction{
    public final Logger log = LoggerFactory.getLogger(getClass());

    public final List<String> include;

    public Pdf2Docx() {
        // 返回支持的输出类型
        this.include = BaseFileConvertService.getSupportType(FileHandlerEnum.PDF2DOCX);
    }

    @Override
    public FileHandlerEnum getName() {
        return FileHandlerEnum.PDF2DOCX;
    }

//    @Override
//    public String getSourceFileType() {
//        return sourceFileType;
//    }
//
//    @Override
//    public String getTargetFileType() {
//        String name = targetFile.getName();
//        return name.substring(name.lastIndexOf(".")+1);
//    }
//
//    @Override
//    public File getSourceFile() {
//        return sourceFile;
//    }
//
//    @Override
//    public File getTargetFile() {
//        return targetFile;
//    }

    @Override
    public List<String> getIncludeType() {
        return include;
    }

    @Override
    public RestChannelConsumer doRequest(RestRequest request, ConvertTypeEnum convertTypeEnum) throws Exception {
        log.debug("doRequest() ======> info");
        File file = request.getFile();
        convertTypeEnum
                .getBaseFileConvertService()
                .pdf2Text(file.getPath(), targetFile.getPath(),request.getTargetFileType());
        File f = new File(targetFile.getPath());
        if (!f.exists()) {
            int index = new Random().nextInt(BaseFileConvertService.DEFAULT_CONVERT_TYPE_SUM);
            ConvertTypeEnum cte = ConvertTypeEnum.values()[index];
            log.warn("PDF2TEXT 失败，文件[{}]不存在,系统奖励一次随缘降级转换", targetFile.getPath());
            log.warn("PDF2TEXT 降级执行目标[{}=>{}]", convertTypeEnum.name(), cte.name());
            cte.getBaseFileConvertService().pdf2Text(file.getPath(), targetFile.getPath(),request.getTargetFileType());
        }
        return channel -> channel.response(new TextRestResponse(RestStatus.OK, targetFile.getAbsolutePath()));
    }
}
