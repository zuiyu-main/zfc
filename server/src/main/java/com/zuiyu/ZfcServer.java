package com.zuiyu;


import com.zuiyu.bootstrap.common.settings.AppSettings;
import com.zuiyu.bootstrap.handler.HttpServerHandler;
import com.zuiyu.bootstrap.handler.MyHttpServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zuiyu
 * @date 2022/10/16
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@SpringBootApplication
public class ZfcServer {
    public static final Logger log = LoggerFactory.getLogger(ZfcServer.class);

    public static void main(String[] args) {
        AppSettings appSettings = new AppSettings();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_SNDBUF,32*1023)
                .childOption(ChannelOption.SO_RCVBUF,32*1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new MyHttpServerInitializer());
//                .childHandler(new ChannelInitializer<NioSocketChannel>() {
//                    @Override
//                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
//                        socketChannel.pipeline()
//                                .addLast(new StringDecoder())
//                                .addLast(new SimpleChannelInboundHandler<String>(){
//                                    @Override
//                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
//                                        System.out.println("收到消息:"+msg);
//                                        channelHandlerContext.writeAndFlush(msg);
//                                    }
//
//                                })
//                                .addLast(new StringEncoder())
//                                .addLast("codec",new HttpServerCodec())
//                                .addLast("compressor",new HttpContentCompressor())
//                                .addLast("aggregator",new HttpObjectAggregator(65536))
//                                .addLast("logHandler",new LogHandler())
//                                .addLast(new StringDecoder()) // 将ByteBuf转换为字符串
//                                .addLast("serverHandler",new HttpServerHandler());
//                                .addLast("httpServerHandler",new HttpServerHandler());

//                    }
//                });
        try {
            ChannelFuture sync = bootstrap.bind(appSettings.getHost(), appSettings.getPort()).sync();
            if (sync.isSuccess()) {
                log.info("ZFC start success {}:{}",appSettings.getHost(),appSettings.getPort());
            } else {
                log.info("ZFC start failed {}:{}",appSettings.getHost(),appSettings.getPort());
            }
            sync.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("ZFC 启动异常：",e);
            throw new RuntimeException(e);
        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}
