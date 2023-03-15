package com.zuiyu.bootstrap.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author zuiyu
 * @date 2023/2/6
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class ClientHandler extends SimpleChannelInboundHandler<Object> {
    public static final Logger log = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) o;
//        byte[] bytes = new byte[buf.readableBytes()];
//        buf.readBytes(bytes);
//        String msg = new String(bytes, StandardCharsets.UTF_8);
        log.info("客户端 收到消息 {}",msg);
//        channelHandlerContext.writeAndFlush(msg);
    }
}
