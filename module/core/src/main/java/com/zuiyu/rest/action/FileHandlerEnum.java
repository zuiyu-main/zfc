package com.zuiyu.rest.action;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public enum FileHandlerEnum implements BaseEnum{
    TEXT2PDF(1),PDF2DOCX(2);

    private final Integer value;
    FileHandlerEnum(Integer value) {
        this.value=value;
    }

    public Integer getValue() {
        return value;
    }
}
