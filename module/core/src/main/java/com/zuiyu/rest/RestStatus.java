package com.zuiyu.rest;

/**
 * @author zuiyu
 * @date 2022/10/25
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public enum RestStatus {
    /**
     * 响应正常
     */
    OK(200),
    /**
     * 不支持的响应
     */
    NOT_FOUND(404),
    /**
     * 服务器处理失败
     */
    INTERNAL_SERVER_ERROR(500),
    ;
    private final int status;

    RestStatus(int status) {
        this.status = (short) status;
    }

    public int getStatus() {
        return status;
    }
}
