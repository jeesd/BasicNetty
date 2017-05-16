package com.jeesd.netty.protocol;
/**
 * 协议体
 * @author song
 *
 */
public class ProtocolMessage {

	private ProtocolHeader protocolHeader;
	
	private String content;

	public ProtocolHeader getProtocolHeader() {
		return protocolHeader;
	}

	public void setProtocolHeader(ProtocolHeader protocolHeader) {
		this.protocolHeader = protocolHeader;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ProtocolMessage(ProtocolHeader protocolHeader, String content) {
		super();
		this.protocolHeader = protocolHeader;
		this.content = content;
	}
	
	 @Override
	    public String toString() {
	        return String.format("[version=%d,contentLength=%d,sessionId=%s,content=%s]",
	        		protocolHeader.getVersion(),
	        		protocolHeader.getContentLength(),
	        		protocolHeader.getSessionId(),
	                content);
	    }
}
