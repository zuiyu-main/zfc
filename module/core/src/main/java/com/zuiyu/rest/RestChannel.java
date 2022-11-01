package com.zuiyu.rest;



/**
 * @author zuiyu
 * @date 2022/10/25
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public interface RestChannel {
    void response(RestResponse response) throws Exception;

    RestRequest request();

}
