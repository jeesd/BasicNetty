package com.jeesd.netty.protocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ClientChannelHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// 添加自定义协议的编解码工具  
        ch.pipeline().addLast(new ProtocolEncoder());  
        ch.pipeline().addLast(new ProtocolDecoder());  
        // 处理网络IO  
        ch.pipeline().addLast(new ClientHandler());  
		
	}

}
