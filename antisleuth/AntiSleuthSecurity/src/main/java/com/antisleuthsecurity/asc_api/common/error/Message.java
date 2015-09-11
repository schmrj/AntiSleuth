/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.common.error;

public class Message {
	private MessagesEnum messageEnum = null;
	private String message = null;
	private Integer messageId = null;
	private MessageType messageType = null;

	public Message(){
		
	}
	
	public Message(MessagesEnum message){
		this.setMessageEnum(message);
	}

	public MessagesEnum getMessageEnum() {
		return messageEnum;
	}

	public void setMessageEnum(MessagesEnum message) {
		this.messageEnum = message;
		this.message = message.getMessage();
		this.messageId = message.getMessageId();
		this.messageType = message.getMessageType();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

}
