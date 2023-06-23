package com.zuiyu.service;

import com.itextpdf.io.font.FontConstants;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.zuiyu.rest.action.FileHandlerEnum;
import com.zuiyu.rest.action.FileTypeEnum;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.ListTables;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.model.StyleDescription;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFPicture;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description Itext 转换
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

    /**
     * doc 文件转换pdf，
     * @param sourceFilePath
     * @param targetFilePath
     * @throws Exception
     */
    @Override
    public void doc2pdf(String sourceFilePath, String targetFilePath) throws Exception {
        log.info("[{}] doc2pdf start ...",COMPONENT_NAME);
        convertWordToPdfWith97_2003(sourceFilePath,targetFilePath);
    }
    public static void convertWordToPdfWith97_2003(String inputFile, String outputFile) throws Exception {
        docxToPdf(inputFile,outputFile);
    }

    public static void main(String... args) throws Exception {
        // Word 文件路径和 PDF 文件路径
        String wordPath = "D:\\zfc\\mydoc.docx";
        String pdfPath = "D:\\zfc\\doc1.pdf";
        docxToPdf(wordPath,pdfPath);
    }
    public static void docxToPdf(String inputFilePath, String outputFilePath) throws Exception {
        // Load the input Word document
        XWPFDocument document = new XWPFDocument(new FileInputStream(inputFilePath));

        // Create the PDF output file
        FileOutputStream pdfOutputStream = new FileOutputStream(new File(outputFilePath));
        PdfWriter writer = new PdfWriter(pdfOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document pdfLayout = new Document(pdfDocument);
        // Add custom font provider to the layout
        // 使用中文字体写入文本
        PdfFont font = PdfFontFactory.createFont("fonts/方正仿宋_GBK.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        pdfLayout.setFont(font);
        // Iterate through the paragraphs and tables in the Word document and add them to the PDF
        for (IBodyElement element : document.getBodyElements()) {
            if (element instanceof XWPFParagraph) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                for (XWPFRun run : paragraph.getRuns()) {
                    for (XWPFPicture picture : run.getEmbeddedPictures()) {
                        byte[] pictureData = picture.getPictureData().getData();
                        Image image = new Image(ImageDataFactory.create(pictureData));
                        pdfLayout.add(image);
                    }
                    String text = run.getText(-1);
                    if (text != null) {
                        byte[] utf8Bytes = text.getBytes(StandardCharsets.UTF_8);
                        String utf8String = new String(utf8Bytes, StandardCharsets.UTF_8);
                        pdfLayout.add(new Paragraph(utf8String));
                    }
                }
            } else if (element instanceof XWPFTable) {
                XWPFTable table = (XWPFTable) element;
                int numColumns = table.getRow(0).getTableCells().size();
                Table pdfTable = new Table(numColumns);
                pdfTable.setWidth(UnitValue.createPercentValue(100f));
                pdfTable.setAutoLayout();
                // 为表格中的所有单元格设置边框
                Border border = new SolidBorder(ColorConstants.BLACK, 1);
                for (int i = 0; i < table.getNumberOfRows(); i++) {
                    XWPFTableRow row = table.getRow(i);
                    for (int j = 0; j < row.getTableCells().size(); j++) {
                        XWPFTableCell cell = row.getCell(j);
                        // 获取单元格的文本内容
                        String text = cell.getText();
                        // 创建单元格对象，并设置文本内容
                        Cell pdfCell = new Cell().add(new Paragraph(text));
                        // 设置单元格边框
                        pdfCell.setBorder(border);
                        // 如果单元格在表格的第一行，就将其视为标题单元格
                        // 将标题单元格的字体设置为粗体，在 PDF 文件中展示为表格标题
                        if (i == 0) {
                            pdfCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                            pdfCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                        } else {
                            // 如果单元格不是在表格的第一行，就将其视为正文单元格
                            // 在 PDF 文件中展示为表格正文内容
                            if (j == 0) {
                                // 如果单元格是在表格的第一列，就将其垂直居中对齐
                                pdfCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                            }
                        }
                        // 把单元格对象加入表格
                        pdfTable.addCell(pdfCell);
                    }
                }
                pdfLayout.add(pdfTable);
            }
        }
        // Save the PDF
        pdfLayout.close();
        pdfDocument.close();
        pdfOutputStream.close();
        document.close();
    }
    @Override
    public void pdf2Text(String sourceFilePath, String targetFilePath,String targetFileType) throws Exception {

    }
}
