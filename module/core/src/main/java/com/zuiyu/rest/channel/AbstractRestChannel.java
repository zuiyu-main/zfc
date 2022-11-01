package com.zuiyu.rest.channel;

import com.zuiyu.rest.RestChannel;
import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.RestStatus;

/**
 * @author zuiyu
 * @date 2022/10/26
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public abstract class AbstractRestChannel implements RestChannel {
    public String content;
    public RestStatus status;
    protected final RestRequest request;


    protected AbstractRestChannel(RestRequest request) {
        this.request = request;
    }

}
