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
 * @date 2022/10/22
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class Text2Pdf extends AbstractWordAction {
    public final Logger log = LoggerFactory.getLogger(getClass());

    public final List<String> include;


    public Text2Pdf() {
        // 返回支持的输入类型
        include = BaseFileConvertService.getSupportType(FileHandlerEnum.TEXT2PDF);
    }

    @Override
    public FileHandlerEnum getName() {
        return FileHandlerEnum.TEXT2PDF;
    }

//    @Override
//    public String getSourceFileType() {
//        return sourceFileType;
//    }
//
//    @Override
//    public String getTargetFileType() {
//        return FileTypeEnum.PDF.name();
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
                .doc2pdf(file.getPath(), targetFile.getPath());
        File f = new File(targetFile.getPath());
        if (!f.exists()) {
            int index = new Random().nextInt(BaseFileConvertService.DEFAULT_CONVERT_TYPE_SUM);
            ConvertTypeEnum cte = ConvertTypeEnum.values()[index];
            log.warn("Word2Pdf 失败，文件[{}]不存在,开启一次随机降级转换", targetFile.getPath());
            log.warn("Word2Pdf 降级执行目标[{}=>{}]", convertTypeEnum.name(), cte.name());
            cte.getBaseFileConvertService().doc2pdf(file.getPath(), targetFile.getPath());
        }
        return channel -> channel.response(new TextRestResponse(RestStatus.OK, targetFile.getAbsolutePath()));
    }

}
