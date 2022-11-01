package com.zuiyu.response;


/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */

public class HttpResponse<T> {

    private T data;
    private String msg;
    private Integer code;

    public HttpResponse() {
    }

    public HttpResponse(T data) {
        this.data = data;
        this.code = 200;
        this.msg = "SUCCESS";
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
