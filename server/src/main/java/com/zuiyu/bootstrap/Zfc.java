package com.zuiyu.bootstrap;

import com.zuiyu.common.settings.AppSettings;
import com.zuiyu.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zuiyu
 * @date 2022/10/16
 * @description
 * @link https://github.com/zuiyu-main
 */
public class Zfc {
    public static final Logger log = LoggerFactory.getLogger(Zfc.class);

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
                .childHandler(new ServerHandler());
        try {
            ChannelFuture sync = bootstrap.bind(appSettings.getHost(), appSettings.getPort()).sync();
            log.info("ZFC start success {}:{}",appSettings.getHost(),appSettings.getPort());
            sync.channel().closeFuture().sync();
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        } catch (InterruptedException e) {
            log.error("ZFC 启动异常：",e);
            throw new RuntimeException(e);
        }

    }
}
