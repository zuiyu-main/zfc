package com.zuiyu.bootstrap.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @author zuiyu
 * @date 2022/10/17
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@Component
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpRequest> {
    public static final Logger log = LoggerFactory.getLogger(HttpServerHandler.class);


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
        log.info("请求URI[{}]",msg.uri());

        String response = "<html><body>你好，世界！</body></html>";
        ByteBuf buf = Unpooled.copiedBuffer(response, CharsetUtil.UTF_8);
        FullHttpResponse httpResponse =
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,response.getBytes().length);
        ctx.writeAndFlush(httpResponse);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        log.info("ServerHandler 异常事件：",throwable);
        channelHandlerContext.close();

    }


}
