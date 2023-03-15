package com.zuiyu.bootstrap.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zuiyu.bootstrap.service.ZfcInstance;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zuiyu
 * @date 2023/2/11
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@RestController
public class HttpController {

    @RequestMapping("/hello")
    public String hello(String msg) throws InterruptedException {
        System.out.println("msg="+msg);
        JSONObject objMsg = new JSONObject();
        objMsg.put("msg",msg);
//        ZfcInstance.getChannel().writeAndFlush(Unpooled.copiedBuffer(msg.getBytes(StandardCharsets.UTF_8)));
        ZfcInstance.sendMsg(Unpooled.copiedBuffer(objMsg.toJSONString().getBytes(StandardCharsets.UTF_8)));
        return "";
    }
}
