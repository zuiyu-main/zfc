package com.zuiyu.rest.action.office;

import com.zuiyu.rest.BaseRestHandler;
import com.zuiyu.rest.RestRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link https://github.com/zuiyu-main
 */
public abstract class AbstractWordAction extends BaseRestHandler {
    public final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public final void preRequest(RestRequest request) throws IOException {

        log.info("preRequest ======> info");
        doRequest(request);
    }

    public abstract void doRequest(RestRequest request) throws IOException;

}
