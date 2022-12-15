package com.zuiyu.rest.channel;


import com.zuiyu.rest.RestChannel;
import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zuiyu
 * @date 2022/10/26
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class DefaultRestChannel extends AbstractRestChannel implements RestChannel {
    public final Logger log = LoggerFactory.getLogger(getClass());

    public DefaultRestChannel(RestRequest request) {
        super(request);
    }


    @Override
    public void response(RestResponse response) throws Exception {
        log.debug("DefaultRestChannel:{}", response.content());
        log.debug("DefaultRestChannel:{}", response.status());
        content = response.content();
        status = response.status();

    }

    @Override
    public RestRequest request() {
        return request;
    }


}
