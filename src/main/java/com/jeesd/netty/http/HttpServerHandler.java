package com.jeesd.netty.http;
  
import static io.netty.handler.codec.http.HttpResponseStatus.OK;  
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1; 
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {

	private ByteBufToBytes reader;
	
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        if (msg instanceof HttpRequest) {  
            HttpRequest request = (HttpRequest) msg;  
            System.out.println("messageType:" + request.headers().get("messageType"));  
            System.out.println("businessType:" + request.headers().get("businessType"));  
            if (HttpHeaders.isContentLengthSet(request)) {  
                reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(request));  
            }  
        }  
  
        if (msg instanceof HttpContent) {  
            HttpContent httpContent = (HttpContent) msg;  
            ByteBuf content = httpContent.content();  
            reader.reading(content);  
            content.release();  
  
            if (reader.isEnd()) {  
                String resultStr = new String(reader.readFull());  
                System.out.println("Client said:" + resultStr);  
  
                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("I am ok"  
                        .getBytes()));  
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");  
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());  
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);  
                ctx.write(response);  
                ctx.flush();  
            }  
        }  
    }  
    
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {  
        ctx.flush();  
    }  
    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
