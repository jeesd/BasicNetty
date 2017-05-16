package com.jeesd.netty;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty服务器端
 * @author song
 *
 */
public class NettyServer {
	
	public void start(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup =  new NioEventLoopGroup();
		try {
			ServerBootstrap  sb = new ServerBootstrap();
			sb.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {  
	                @Override  
	                public void initChannel(SocketChannel ch)  
	                        throws Exception {  
	                    // 注册handler  
	                    ch.pipeline().addLast(new HelloServerInHandler());  
	                }  
	            }).option(ChannelOption.SO_BACKLOG, 128)  
	            .childOption(ChannelOption.SO_KEEPALIVE, true); 
			
			ChannelFuture cf = sb.bind(port).sync();
			cf.channel().closeFuture().sync(); 
			
		} finally {
			workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		NettyServer nettyServer = new NettyServer();
		nettyServer.start(8888);
	}
}
