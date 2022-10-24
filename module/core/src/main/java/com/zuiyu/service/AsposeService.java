package com.zuiyu.service;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link https://github.com/zuiyu-main
 */
public class AsposeService {
    public static final Logger log = LoggerFactory.getLogger(AsposeService.class);


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

    public static boolean doc2pdf(String sourceFilePath,String targetFilePath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return false;
        }
        FileOutputStream os = null;
        try {
            long start = System.currentTimeMillis();
            File file = new File(targetFilePath); // 新建一个空白pdf文档
            os = new FileOutputStream(file);
            Document doc = new Document(sourceFilePath); // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            // EPUB, XPS, SWF 相互转换
            log.info("WORD转PDF成功，耗时：{}", System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
