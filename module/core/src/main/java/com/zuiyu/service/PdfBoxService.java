package com.zuiyu.service;

import com.zuiyu.rest.action.FileHandlerEnum;
import com.zuiyu.rest.action.FileTypeEnum;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class PdfBoxService implements BaseFileConvertService {
    private static final Logger log = LoggerFactory.getLogger(PdfBoxService.class);

    private static final String COMPONENT_NAME = "PDFBox";


    private static final Map<FileHandlerEnum, List<String>> INCLUDE_TYPE_MAP =
            new HashMap<FileHandlerEnum, List<String>>() {{
                put(FileHandlerEnum.TEXT2PDF, Arrays.asList(
                        FileTypeEnum.TXT.name()
                ));

            }};


    @Override
    public List<String> getIncludeType(FileHandlerEnum fileHandlerEnum) {
        log.debug("[{}] getIncludeType by [{}]",COMPONENT_NAME,fileHandlerEnum.name());
        return INCLUDE_TYPE_MAP.get(fileHandlerEnum);
    }

    /**
     * 近支持纯文本、英文
     * @param sourceFilePath
     * @param targetFilePath
     * @throws Exception
     */
    @Override
    public void doc2pdf(String sourceFilePath, String targetFilePath) throws Exception {
        log.info(COMPONENT_NAME);
        PDDocument document = new PDDocument();
        PDPage pdPage = new PDPage();
        document.addPage(pdPage);
        PDPageContentStream contents = new PDPageContentStream(document, pdPage);
        contents.beginText();


        PDFont font = PDType1Font.HELVETICA_BOLD;
//        ClassPathResource classPathResource = new ClassPathResource("fonts/方正仿宋_GBK.TTF");
//        PDFont font1 = PDType0Font.load(document, classPathResource.getFile());
//        fonts.add(font1);
        contents.setFont(font, 30);

        contents.newLineAtOffset(50, 700);
        // 读取文件内容文本 start
        File file = new File(sourceFilePath);
        BufferedReader bf = new BufferedReader(new FileReader(file));
        StringBuilder str = new StringBuilder();
        String s;
        while ((s = bf.readLine()) != null) {
            str.append(s);
        }
        // end
        contents.showText(str.toString());
        contents.endText();
        contents.close();
        document.save(targetFilePath);
        document.close();

    }

    @Override
    public void pdf2doc(String sourceFilePath, String targetFilePath) throws Exception {

    }
}
