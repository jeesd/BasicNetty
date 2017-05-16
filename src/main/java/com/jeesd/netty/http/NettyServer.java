package com.jeesd.netty.http;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class NettyServer {
	
	public void start(int port) throws Exception {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {   
	                @Override  
	                public void initChannel(SocketChannel ch) throws Exception {  
	                    // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码  
	                    ch.pipeline().addLast(new HttpResponseEncoder());  
	                    // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码  
	                    ch.pipeline().addLast(new HttpRequestDecoder());  
	                    ch.pipeline().addLast(new HttpServerHandler());  
	                }  
	            }).option(ChannelOption.SO_BACKLOG, 128) 
				.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			 ChannelFuture cf = sb.bind(port).sync();
			 cf.channel().closeFuture().sync(); 
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		NettyServer nettyServer = new NettyServer();
		nettyServer.start(8880);
	}

}
