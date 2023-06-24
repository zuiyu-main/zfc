package com.zuiyu.service;

import com.aspose.pdf.*;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.License;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.SaveFormat;
import com.zuiyu.rest.action.FileHandlerEnum;
import com.zuiyu.rest.action.FileTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class AsposeService implements BaseFileConvertService {
    public static final Logger log = LoggerFactory.getLogger(AsposeService.class);
    private static final String COMPONENT_NAME = "Aspose";

    private static final Map<FileHandlerEnum, List<String>> INCLUDE_TYPE_MAP =
            new HashMap<FileHandlerEnum, List<String>>() {{
                /**
                 * TEXT2PDF 支持的文本类型
                 */
                put(FileHandlerEnum.TEXT2PDF, Arrays.asList(
                        FileTypeEnum.DOC.name(),
                        FileTypeEnum.DOCX.name(),
                        FileTypeEnum.XML.name(),
                        FileTypeEnum.TXT.name(),

                        FileTypeEnum.RTF.name(),
                        FileTypeEnum.HTM.name(),
                        FileTypeEnum.HTML.name(),
                        FileTypeEnum.JSON.name()
                ));
                put(FileHandlerEnum.PDF2DOCX, Collections.singletonList(
                        FileTypeEnum.PDF.name()
                ));

            }};

    public static boolean getLicense() {
        boolean result = false;
        try {
            // TODO: 2023/1/9  aspose.xml 路径 resources/aspose.xml 【自行提供授权】
            InputStream is = AsposeService.class.getClassLoader().getResourceAsStream("aspose.xml");
            License asposeLicense = new License();
            asposeLicense.setLicense(is);
            result = true;
        } catch (Exception e) {
            log.error("请检查【resources/aspose.xml】授权是否正确");
            log.error("{} 签名获取异常:",COMPONENT_NAME,e);
        }
        return result;
    }

    @Override
    public List<String> getIncludeType(FileHandlerEnum fileHandlerEnum) {
        log.debug("[{}] getIncludeType by [{}]",COMPONENT_NAME,fileHandlerEnum.name());
        return INCLUDE_TYPE_MAP.get(fileHandlerEnum);
    }

    @Override
    public void doc2pdf(String sourceFilePath, String targetFilePath) throws Exception {
        log.info("[{}] doc2pdf() start ...",COMPONENT_NAME);
        if (!getLicense()) {
            return;
        }
        long start = System.currentTimeMillis();
        try (FileOutputStream os = new FileOutputStream(targetFilePath)) {
            com.aspose.words.Document doc = new com.aspose.words.Document(sourceFilePath);
            doc.save(os, SaveFormat.PDF);
            log.info("Aspose WORD转PDF成功，耗时：{}{}", (System.currentTimeMillis() - start) / 1000, "s");
        } catch (Exception e) {
            log.error("Aspose WORD2PDF 失败：", e);
            throw e;
        }
    }

    @Override
    public void pdf2Text(String sourceFilePath, String targetFilePath,String targetFileType) throws Exception {
        log.info("[{}] pdf2Text() start ...",COMPONENT_NAME);
        if (!getLicense()) {
            return;
        }
//        long start = System.currentTimeMillis();
//        try (FileOutputStream os = new FileOutputStream(targetFilePath)) {
//            Document doc = new Document(sourceFilePath);
//            doc.save(os, com.aspose.pdf.SaveFormat.DocX);
//            log.info("{} pdf2Text 成功，耗时：{}{}",COMPONENT_NAME, (System.currentTimeMillis() - start) / 1000, "s");
//        } catch (Exception e) {
//            log.error("{} pdf2Text 失败：",COMPONENT_NAME, e);
//            throw e;
//        }
// 1. 转换PDF为文本
        Document pdfDocument = new Document(sourceFilePath);
        TextAbsorber textAbsorber = new TextAbsorber();
//            pdfDocument.getPages().accept(textAbsorber);
        PageCollection pages = pdfDocument.getPages();
        DocumentInfo info = pdfDocument.getInfo();
        String subject = info.getSubject();
        for (int i = 0; i < pages.size(); i++) {

            Page item = pages.get_Item(i);
            item.accept(textAbsorber);
        }
        String text = textAbsorber.getText();

        // 2. 写入docx文件
        com.aspose.words.Document docxDocument = new com.aspose.words.Document();
        DocumentBuilder builder = new DocumentBuilder(docxDocument);
        builder.getPageSetup().setLeftMargin(72.0);
        builder.getPageSetup().setRightMargin(72.0);
        builder.getPageSetup().setTopMargin(72.0);
        builder.getPageSetup().setBottomMargin(72.0);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);

        String[] paragraphs = text.split("\\r?\\n\\r?\\n");
        for (String paragraph : paragraphs) {
            builder.writeln(paragraph);
        }

        FileOutputStream out = new FileOutputStream(targetFilePath);
        docxDocument.save(out, com.aspose.words.SaveFormat.DOCX);
        out.close();

        pdfDocument.close();
    }


}
