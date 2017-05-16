package com.jeesd.netty.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {
	
	 public void start(int port) throws Exception {  
        // 配置NIO线程组  
        EventLoopGroup bossGroup = new NioEventLoopGroup();  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            // 服务器辅助启动类配置  
            ServerBootstrap sb = new ServerBootstrap();  
            sb.group(bossGroup, workerGroup)  
                    .channel(NioServerSocketChannel.class)  
                    .handler(new LoggingHandler(LogLevel.INFO))  
                    .option(ChannelOption.SO_BACKLOG, 1024) // 设置tcp缓冲区   
                    .childOption(ChannelOption.SO_KEEPALIVE, true)  
		            .childHandler(new ChildChannelHandler());  
            // 绑定端口 同步等待绑定成功  
            ChannelFuture f = sb.bind(port).sync();
            // 等到服务端监听端口关闭  
            f.channel().closeFuture().sync();  
        } finally {  
            // 优雅释放线程资源  
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }
	 }
	 
	 public static void main(String[] args) throws Exception {
		 new Server().start(8889);  
	}
}
