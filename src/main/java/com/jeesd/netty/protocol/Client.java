package com.jeesd.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	public void connect(int port, String host) throws Exception {  
        // 配置客户端NIO线程组  
        EventLoopGroup group = new NioEventLoopGroup();  
        try {  
            // 客户端辅助启动类 对客户端配置  
            Bootstrap b = new Bootstrap();  
            b.group(group)//  
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)  
                    .handler(new ClientChannelHandler());  
            // 异步链接服务器 同步等待链接成功  
            ChannelFuture f = b.connect(host, port).sync();  
  
            // 等待链接关闭  
            f.channel().closeFuture().sync();  
  
        } finally {  
            group.shutdownGracefully();  
            System.out.println("客户端优雅的释放了线程资源...");  
        }    
	}
  
	public static void main(String[] args) throws Exception {
		new Client().connect(8889, "127.0.0.1"); 
	}
}
