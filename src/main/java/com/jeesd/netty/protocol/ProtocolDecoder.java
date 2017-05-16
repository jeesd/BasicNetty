package com.jeesd.netty.protocol;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ProtocolDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 可读长度必须大于基本长度  
		if(in.readableBytes() >= Constant.HAND_LENGTH) {
			// 防止socket字节流攻击  
            // 防止，客户端传来的数据过大  
            // 因为，太大的数据，是不合理的  
            if (in.readableBytes() > Constant.MESSAGE_MAX) {  
                in.skipBytes(in.readableBytes());  
            } 
            // 获取协议的版本
            int version = in.readInt();
            // 获取消息长度
            int contentLength = in.readInt();
            // 获取SessionId
            byte[] sessionByte = new byte[contentLength];
            in.readBytes(sessionByte);
            String sessionId = new String(sessionByte);

            // 组装协议头
            ProtocolHeader header = new ProtocolHeader(version, contentLength, sessionId);
            // 读取消息内容
            //byte[] content = in.readBytes(in.readableBytes()).array();
            ProtocolMessage message = new ProtocolMessage(header, new String(sessionByte));

            out.add(message);
		}
	}

}

