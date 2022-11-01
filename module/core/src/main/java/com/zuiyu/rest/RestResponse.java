package com.zuiyu.rest;

import com.zuiyu.util.Maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zuiyu
 * @date 2022/10/25
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public abstract class RestResponse  {
    private Map<String, List<String>> customHeaders;
    public abstract String contentType();
    public abstract String content();
    public abstract RestStatus status();



    public void addHeader(String name, String value) {
        if (customHeaders == null) {
            customHeaders = Maps.newMapWithExpectedSize(2);
        }
        List<String> header = customHeaders.get(name);
        if (header == null) {
            header = new ArrayList<>();
            customHeaders.put(name, header);
        }
        header.add(value);
    }
    public Map<String, List<String>> getHeaders() {
        if (customHeaders == null) {
            return Collections.emptyMap();
        } else {
            return customHeaders;
        }
    }
    public Map<String, List<String>> filterHeaders(Map<String, List<String>> headers) {
        return headers;
    }

}
