package com.zuiyu.service;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.zuiyu.rest.action.FileTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class AsposeService extends BaseFileConvertService {
    public static final Logger log = LoggerFactory.getLogger(AsposeService.class);

    @Override
    public List<String> getIncludeType() {
        return INCLUDE_TYPE;
    }

    private static final List<String> INCLUDE_TYPE = Arrays.asList(
            FileTypeEnum.DOC.name(),
            FileTypeEnum.DOCX.name(),
            FileTypeEnum.XML.name(),
            FileTypeEnum.TXT.name(),

            FileTypeEnum.RTF.name(),
            FileTypeEnum.HTM.name(),
            FileTypeEnum.HTML.name(),
            FileTypeEnum.JSON.name()
    );

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = AsposeService.class.getClassLoader().getResourceAsStream("aspose.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void doc2pdf(String sourceFilePath, String targetFilePath) throws Exception{
        if (!getLicense()) {
            return;
        }
        File file = new File(targetFilePath);
        try(FileOutputStream os = new FileOutputStream(file)) {
            long start = System.currentTimeMillis();
            Document doc = new Document(sourceFilePath);
            doc.save(os, SaveFormat.PDF);
            log.info("Aspose WORD转PDF成功，耗时：{}{}", (System.currentTimeMillis() - start)/1000,"s");
        } catch (Exception e) {
            log.error("Aspose WORD2PDF 失败：",e);
            throw e;
        }
    }


}
