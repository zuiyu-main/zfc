package com.zuiyu.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.zuiyu.rest.action.FileHandlerEnum;
import com.zuiyu.rest.action.FileTypeEnum;
import org.apache.poi.xwpf.usermodel.*;
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
        convertWordToPdfWith97_2003(sourceFilePath,targetFilePath);
    }
    public static void convertWordToPdfWith97_2003(String inputFile, String outputFile) {


    }

    public static void main(String[] args) throws Exception {
        // 定义Word文件路径和PDF文件路径
        String docPath = "example.docx";
        String pdfPath = "example.pdf";
        // 初始化PDF文档
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(pdfPath)));
        Document doc = new Document(pdfDoc);
        // 使用Apache POI解析Word文档
        FileInputStream fis = new FileInputStream(new File(docPath));
        XWPFDocument document = new XWPFDocument(fis);
        // 遍历Word文档中的所有段落
        for (XWPFParagraph para : document.getParagraphs()) {
            // 创建新的段落
            Paragraph pdfPara = new Paragraph(para.getText());
            // 设置段落样式
            if (para.getAlignment() != null) {
                if (HorizontalAlignment.CENTER.equals(para.getAlignment())) {
                    pdfPara.setTextAlignment(TextAlignment.CENTER);
                } else if (HorizontalAlignment.RIGHT.equals(para.getAlignment())) {
                    pdfPara.setTextAlignment(TextAlignment.RIGHT);
                }
            }
            // 将段落添加到PDF文档中
            doc.add(pdfPara);
        }
        // 遍历Word文档中的所有表格
        for (XWPFTable tbl : document.getTables()) {
            // 获取表格的行数和列数
            int rows = tbl.getNumberOfRows();
            int cols = tbl.getRow(0).getTableCells().size();
            // 创建新的表格
            Table pdfTbl = new Table(UnitValue.createPercentArray(new float[cols])).useAllAvailableWidth();
            // 遍历表格中的所有单元格
            for (int i = 0; i < rows; i++) {
                XWPFTableRow row = tbl.getRow(i);
                for (int j = 0; j < cols; j++) {
                    XWPFTableCell cell = row.getCell(j);
                    // 创建新的单元格
                    Paragraph content = new Paragraph(cell.getText());
                    // 设置单元格样式
                    content.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    content.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    // 将单元格添加到表格中
                    pdfTbl.addCell(content);
                }
            }
            // 将表格添加到PDF文档中
            doc.add(pdfTbl);
        }
        // 遍历Word文档中的所有图片
        for (XWPFParagraph para : document.getParagraphs()) {
            for (XWPFRun run : para.getRuns()) {
                // 判断是否为图片
                if (run.getEmbeddedPictures().size() > 0) {
                    for (XWPFPicture pic : run.getEmbeddedPictures()) {
                        // 获取图片数据和格式
                        byte[] data = pic.getPictureData().getData();
                        String contentType = pic.getPictureData().getPackagePart().getContentType();
                        String picDesc = pic.getDescription();
                        // 设置图片描述信息
                        // 创建新的图片
                        ImageData imageData = ImageDataFactory.create(data);
                        Image pdfImg = new Image(imageData);
                        pdfImg.getAccessibilityProperties().setAlternateDescription(picDesc);

                        // 将图片添加到PDF文档中
                        doc.add(pdfImg);
                    }
                }
            }
        }

        // 关闭PDF文档和输入流
        doc.close();
        pdfDoc.close();
        fis.close();
    }

    @Override
    public void pdf2Text(String sourceFilePath, String targetFilePath,String targetFileType) throws Exception {

    }
}
