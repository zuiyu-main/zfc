package com.zuiyu.bootstrap.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zuiyu
 * @date 2022/10/17
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class LogHandler implements ChannelInboundHandler {
    public static final Logger log = LoggerFactory.getLogger(LogHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {

        log.info("LogHandler Channel 创建后被注册到 EventLoop 上");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("LogHandler Channel 创建后未注册或者从 EventLoop 取消注册\n");

    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("LogHandler Channel 处于就绪状态，可以被读写");

    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("LogHandler Channel 处于非就绪状态");

    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        log.info("LogHandler channelRead start ...");
        log.info("LogHandler channelRead end ...");
        String response = "Hello, World!log";
        ByteBuf buf = Unpooled.copiedBuffer(response, CharsetUtil.UTF_8);
        channelHandlerContext.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf));

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("LogHandler Channel 读取数据完成");

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        log.info("LogHandler 用户事件触发：");

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("LogHandler 通道可写事件改变：");

    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("LogHandler 添加事件：");

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("LogHandler 移除事件：");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        log.info("LogHandler 异常事件：");

    }
}
