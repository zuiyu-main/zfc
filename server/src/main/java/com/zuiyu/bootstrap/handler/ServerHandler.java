package com.zuiyu.bootstrap.handler;

import com.alibaba.fastjson.JSONObject;
import com.zuiyu.response.HttpResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author zuiyu
 * @date 2023/3/12
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf){
            System.out.println("将ByteBuf类型的数据转换为字符串");
            // 将ByteBuf类型的数据转换为字符串
            ByteBuf buf = (ByteBuf) msg;
            String str = buf.toString(CharsetUtil.UTF_8);

            String content = "测试ByteBuf请求";
            ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            ctx.channel().writeAndFlush(response);
        }
        if (msg instanceof HttpRequest) {
            // 处理 HTTP 请求
            HttpRequest request = (HttpRequest) msg;
            HttpResponse<String> result = new HttpResponse<>();
            result.setData("测试http请求");
            result.setCode(200);
            result.setMsg("成功");
            ByteBuf byteBuf = Unpooled.copiedBuffer(JSONObject.toJSONString(result), CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            ctx.channel().writeAndFlush(response);
        }
        else if (msg instanceof String) {
            // 处理字符串内容
            String content = "字符串信息";
            ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            ctx.channel().writeAndFlush(response);
        }else{
            System.out.println("未知类型："+msg.getClass());
            String content = "未知类型";
            ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            ctx.channel().writeAndFlush(response);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 关闭连接
        ctx.close();
    }
}
