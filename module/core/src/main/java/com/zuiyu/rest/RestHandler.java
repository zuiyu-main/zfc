package com.zuiyu.rest;

//import com.zuiyu.rest.action.RestActionListener;

/**
 * @author zuiyu
 * @date 2022/10/21
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@FunctionalInterface
public interface RestHandler {
    void handleRequest(RestRequest request, RestChannel channel) throws Exception;

}
