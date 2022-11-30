package com.zuiyu.rest.action;

import com.zuiyu.service.*;

/**
 * @author zuiyu
 * @date 2022/11/28
 * @description 转换工具枚举
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public enum ConvertTypeEnum {
    ASPOSE(new AsposeService()),
    ITEXT(new ItextService()),
    PDFBOX(new PdfBoxService()),
    SPIRE(new SpirePdfService());

    final private BaseFileConvertService baseFileConvertService;
    ConvertTypeEnum(BaseFileConvertService baseFileConvertService) {
        this.baseFileConvertService = baseFileConvertService;
    }

    public BaseFileConvertService getBaseFileConvertService() {
        return baseFileConvertService;
    }

}
