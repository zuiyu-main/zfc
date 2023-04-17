package com.zuiyu.service;

import com.itextpdf.text.*;

import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.zuiyu.rest.action.FileHandlerEnum;
import com.zuiyu.rest.action.FileTypeEnum;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
//import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.List;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class ItextService implements BaseFileConvertService {
    public static final Logger log = LoggerFactory.getLogger(ItextService.class);
    private static final String COMPONENT_NAME = "Itext";

    private static final Map<FileHandlerEnum, List<String>> INCLUDE_TYPE_MAP =
            new HashMap<FileHandlerEnum, List<String>>() {{
                put(FileHandlerEnum.TEXT2PDF, Arrays.asList(
                        FileTypeEnum.DOC.name(),
                        FileTypeEnum.DOCX.name()

                ));

            }};

    @Override
    public List<String> getIncludeType(FileHandlerEnum fileHandlerEnum) {
        log.debug("[{}] getIncludeType by [{}]",COMPONENT_NAME,fileHandlerEnum.name());
        return INCLUDE_TYPE_MAP.get(fileHandlerEnum);
    }


    @Override
    public void doc2pdf(String sourceFilePath, String targetFilePath) throws Exception {
        log.info("[{}] doc2pdf start ...",COMPONENT_NAME);
//        log.warn("{} doc2pdf 暂未支持，请关注后续版本!!!",COMPONENT_NAME);
        convertWordToPdf2(sourceFilePath,targetFilePath);
    }
    public static void convertWordToPdf2(String inputFile, String outputFile) {

        try (FileInputStream fis = new FileInputStream(new File(inputFile));
             XWPFDocument document = new XWPFDocument(fis)) {
            // Initialize PDF document
            Document pdfDoc = new Document();
            PdfWriter.getInstance(pdfDoc, new FileOutputStream(outputFile));
            pdfDoc.open();
            // 遍历文档中的段落
            for (XWPFParagraph para : document.getParagraphs()) {
                String text = para.getText();
                // Add text to PDF document
                pdfDoc.add(new Paragraph(text));
            }
            // Close PDF document
            pdfDoc.close();
        } catch (IOException | DocumentException e) {
            log.error("{} WORD2PDF 失败：", COMPONENT_NAME, e);

        }
    }
    public static void convertWordToPdf(String inputFile, String outputFile) {
        try {
            // Load input Word document
            InputStream input = new FileInputStream(new File(inputFile));
            HWPFDocument wordDoc = new HWPFDocument(input);

            // Get paragraphs from Word document
            Range range = wordDoc.getRange();

            int numParagraphs = range.numParagraphs();

            List<org.apache.poi.hwpf.usermodel.Paragraph> paragraphs = new LinkedList<>();


            // Initialize PDF document
            Document pdfDoc = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(pdfDoc, new FileOutputStream(outputFile));
            pdfDoc.open();

            // Initialize PDF copy
            PdfCopy copy = new PdfCopy(pdfDoc, new FileOutputStream(outputFile));
            copy.open();
            int currentPageNumber = 1;

            // Process paragraphs in chunks of 10
            for (int i = 0; i < numParagraphs; i++) {
                org.apache.poi.hwpf.usermodel.Paragraph paragraph = range.getParagraph(i);
                paragraphs.add(paragraph);
            }
            for (int i = 0; i < paragraphs.size(); i += 10) {
                // Create PDF chunk containing 10 paragraphs
                int endIndex = Math.min(i + 10, paragraphs.size());
                StringBuilder sb = new StringBuilder();
                for (int j = i; j < endIndex; j++) {
                    String text = ((Range)paragraphs.get(j)).text().trim();
                    sb.append(text);
                    sb.append("\n\n");
                }
                // 设置字体样式
                Font font = new Font(Font.getFamily("Helvetica"), 16, Font.BOLD);
                Paragraph chunk = new Paragraph(sb.toString(),font);
                chunk.setAlignment(Element.ALIGN_JUSTIFIED);

                // Add chunk to current PDF page
                pdfDoc.add(chunk);

                // Add current PDF page to PDF copy
                PdfReader reader = new PdfReader(writer.getDirectContent().toPdf(null));
                copy.addPage(copy.getImportedPage(reader, currentPageNumber));

                // Increment current page number
                currentPageNumber++;
            }

            // Close PDF copy, PDF writer, and PDF document
            copy.close();
            writer.close();
            pdfDoc.close();

            // Close input stream
            input.close();
        }
        catch (Exception e) {
            log.error("{} WORD2PDF 失败：", COMPONENT_NAME, e);
        }
    }

    @Override
    public void pdf2Text(String sourceFilePath, String targetFilePath,String targetFileType) throws Exception {

    }
}
