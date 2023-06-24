package com.zuiyu.boot.model;

/**
 * @author zuiyu
 * @date 2023/5/2
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */

public class FileResultDTO {
    /**
     * 开始转换时间
     */
    private String date;
    /**
     * 文件访问URL
     */
    private String url;
    /**
     * 文件名
     */
    private String name;
    /**
     * 转换时间
     */
    private String time;
    /**
     * 转换结果
     */
    private String result;
    /**
     * 转换类型
     */
    private String type;
    /**
     * 源文件大小
     */
    private Long size0;
    /**
     * 输出文件大小
     */
    private Long size1;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize0() {
        return size0;
    }

    public void setSize0(Long size0) {
        this.size0 = size0;
    }

    public Long getSize1() {
        return size1;
    }

    public void setSize1(Long size1) {
        this.size1 = size1;
    }
}
