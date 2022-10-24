package com.zuiyu.rest;

/**
 * @author zuiyu
 * @date 2022/10/21
 * @description
 * @link https://github.com/zuiyu-main
 */
@FunctionalInterface
public interface RestHandler {
    void handleRequest(RestRequest request) throws Exception;
}
