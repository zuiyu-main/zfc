package com.zuiyu.service;


import com.spire.doc.Document;
import com.zuiyu.rest.action.FileHandlerEnum;
import com.zuiyu.rest.action.FileTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description 友情提示: 免费版有 10 页的页数输出限制，在输出结果文档时只能输出前 10 页。
 * 将 PDF 文档转换为图片、Word、HTML、XPS 等格式时，仅支持转换前 3 页。
 * 如超出限制，可升级到商业版，我们仅对免费版进行不定期维护。免费版不提供技术服务或其他支持服务。
 * https://www.e-iceblue.cn/Introduce/Free-Spire-Doc-JAVA.html
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class SpirePdfService implements BaseFileConvertService {
    public static final Logger log = LoggerFactory.getLogger(SpirePdfService.class);

    private static final String COMPONENT_NAME = "Spire.pdf.free";


    private static final Map<FileHandlerEnum, List<String>> INCLUDE_TYPE_MAP =
            new HashMap<FileHandlerEnum, List<String>>() {{
                put(FileHandlerEnum.TEXT2PDF, Arrays.asList(
                        FileTypeEnum.DOC.name(),
                        FileTypeEnum.DOCX.name(),
                        FileTypeEnum.TXT.name(),

                        FileTypeEnum.RTF.name(),
                        FileTypeEnum.HTM.name(),
                        FileTypeEnum.HTML.name(),
                        FileTypeEnum.JSON.name()
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

        try (FileInputStream inputStream = new FileInputStream(sourceFilePath);) {
            long start = System.currentTimeMillis();
            Document document = new Document();
            document.loadFromStream(inputStream, com.spire.doc.FileFormat.Auto);
            //保存为PDF
            document.saveToFile(targetFilePath);
            log.info("{} WORD转PDF成功，耗时：{}{}", COMPONENT_NAME, (System.currentTimeMillis() - start) / 1000, "s");
        } catch (Exception e) {
            log.error("{} WORD2PDF 失败：", COMPONENT_NAME, e);
            throw e;
        }
    }

    @Override
    public void pdf2doc(String sourceFilePath, String targetFilePath) throws Exception {

    }


}
