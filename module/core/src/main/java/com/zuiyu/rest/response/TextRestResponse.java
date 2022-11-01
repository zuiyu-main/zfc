package com.zuiyu.rest.response;

import com.zuiyu.rest.RestResponse;
import com.zuiyu.rest.RestStatus;

/**
 * @author zuiyu
 * @date 2022/10/25
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class TextRestResponse extends RestResponse {
    public static final String TEXT_CONTENT_TYPE = "text/plain; charset=UTF-8";

    public String content;
    public RestStatus status;

    public TextRestResponse(RestStatus status, String content) {
        this.status=status;
        this.content=content;
    }

    @Override
    public String contentType() {
        return TEXT_CONTENT_TYPE;
    }

    @Override
    public String content() {
        return content;
    }

    @Override
    public RestStatus status() {
        return status;
    }
}
