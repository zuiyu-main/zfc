package com.zuiyu.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


/**
 * @author zuiyu
 * @date 2022/10/17
 * @description
 * @link https://github.com/zuiyu-main
 */
public class ServerHandler implements ChannelInboundHandler {
    public static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {

        log.info("通道注册：");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("通道未注册：");

    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("通道激活：");

    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("通道未激活：");

    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        log.info("通道读取：");
        ByteBuf buf = (ByteBuf) o;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String msg = new String(bytes, StandardCharsets.UTF_8);
        log.info("服务端收到内容：\n{}",msg);
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("你很优秀".getBytes(StandardCharsets.UTF_8)));

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("通道读取完成：");

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        log.info("用户事件触发：");

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("通道可写事件改变：");

    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("添加事件：");

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("移除事件：");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        log.info("异常事件：");

    }
}
