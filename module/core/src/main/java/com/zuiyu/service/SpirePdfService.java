package com.zuiyu.service;


import com.spire.doc.Document;
import com.zuiyu.rest.action.FileTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description 友情提示: 免费版有 10 页的页数输出限制，在输出结果文档时只能输出前 10 页。
 * 将 PDF 文档转换为图片、Word、HTML、XPS 等格式时，仅支持转换前 3 页。
 * 如超出限制，可升级到商业版，我们仅对免费版进行不定期维护。免费版不提供技术服务或其他支持服务。
 * https://www.e-iceblue.cn/Introduce/Free-Spire-Doc-JAVA.html
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class SpirePdfService extends BaseFileConvertService{
    public static final Logger log = LoggerFactory.getLogger(SpirePdfService.class);

    private static final String COMPONENT_NAME="Spire.pdf.free";

    @Override
    public List<String> getIncludeType() {
        return INCLUDE_TYPE;
    }

    private static final List<String> INCLUDE_TYPE = Arrays.asList(
            FileTypeEnum.DOC.name(),
            FileTypeEnum.DOCX.name(),
            FileTypeEnum.TXT.name(),

            FileTypeEnum.RTF.name(),
            FileTypeEnum.HTM.name(),
            FileTypeEnum.HTML.name(),
            FileTypeEnum.JSON.name()
    );
    @Override
    public void doc2pdf(String sourceFilePath, String targetFilePath) throws Exception {

        try(FileInputStream inputStream = new FileInputStream(sourceFilePath);) {
            long start = System.currentTimeMillis();
            Document document = new Document();
            document.loadFromStream(inputStream, com.spire.doc.FileFormat.Auto);
            //保存为PDF
            document.saveToFile(targetFilePath);
            log.info("{} WORD转PDF成功，耗时：{}{}",COMPONENT_NAME, (System.currentTimeMillis() - start)/1000,"s");
        } catch (Exception e) {
            log.error("{} WORD2PDF 失败：",COMPONENT_NAME,e);
            throw e;
        }
    }
}
