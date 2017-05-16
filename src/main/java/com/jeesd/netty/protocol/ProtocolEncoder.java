package com.jeesd.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtocolEncoder extends MessageToByteEncoder<ProtocolMessage> {


	@Override
	protected void encode(ChannelHandlerContext ctx, ProtocolMessage msg, ByteBuf out) throws Exception {
		// 将Message转换成二进制数据
        ProtocolHeader header = msg.getProtocolHeader();

        // 这里写入的顺序就是协议的顺序.
        // 写入Header信息
        out.writeInt(header.getVersion());
        out.writeInt(msg.getContent().length());
        out.writeBytes(header.getSessionId().getBytes());

        // 写入消息主体信息
        out.writeBytes(msg.getContent().getBytes());
		
	}

}
