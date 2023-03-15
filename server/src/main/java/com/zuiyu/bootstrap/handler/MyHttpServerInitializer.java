package com.zuiyu.bootstrap.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author zuiyu
 * @date 2023/3/12
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class MyHttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
//        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
//        pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
//        // 添加 HTTP 相关的 handlers
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65536)); // 将 HTTP 消息合并成 FullHttpRequest 和 FullHttpResponse
//        pipeline.addLast("httpContentCompressor", new HttpContentCompressor()); // 压缩 HTTP 消息体
//        pipeline.addLast("httpserverHandler", new HttpServerHandler());
        // 添加字符串编解码 handlers
//        pipeline.addLast("stringDecoder", new StringDecoder());
//        pipeline.addLast("stringEncoder", new StringEncoder());


        pipeline.addLast("serverHandler", new ServerHandler());
    }
}
