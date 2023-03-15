package com.zuiyu.bootstrap.service;

import com.zuiyu.bootstrap.handler.ClientHandler;
import com.zuiyu.bootstrap.handler.HttpServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author zuiyu
 * @date 2023/2/11
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@Component
public class ZfcInstance implements InitializingBean {
    public static final Logger log = LoggerFactory.getLogger(ZfcInstance.class);

    private static Channel channel = null;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("====== ZFC 客户端实例启动 ======");
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1",8888));
            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

//                             添加 HTTP 相关的 handlers
//                    pipeline.addLast("httpServerCodec", new HttpServerCodec());
//                    pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65536)); // 将 HTTP 消息合并成 FullHttpRequest 和 FullHttpResponse
//                    pipeline.addLast("httpContentCompressor", new HttpContentCompressor()); // 压缩 HTTP 消息体
//                    pipeline.addLast("httpserverHandler", new HttpServerHandler());
//                             添加字符串编解码 handlers
//                    pipeline.addLast("stringDecoder", new StringDecoder());
                    pipeline
                            .addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8))
                            .addLast(new ClientHandler());
                }
            });
            ChannelFuture future = bootstrap.connect().sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        log.info("测试请求发送成功");
                    }else{
                        log.error("测试请求发送失败");
                    }
                }
            });
            channel = future.channel();
            channel.writeAndFlush(Unpooled.copiedBuffer("hello server ".getBytes(StandardCharsets.UTF_8)));


        }catch (Exception e){
            e.printStackTrace();
            log.error("报错",e);
        } finally {
//            eventLoopGroup.shutdownGracefully();
        }
    }
    public static Channel getChannel() {
        return channel;
    }
    public static void sendMsg(Object msg){
        ChannelFuture future = channel.writeAndFlush(msg);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    log.info("sendMsg 测试请求发送成功");
                }else{
                    log.error("sendMsg 测试请求发送失败");
                    Throwable cause = channelFuture.cause();
                    System.out.println("Connect failed: " + cause.getMessage());

                }
            }
        });


//        channel.read();
    }
}
