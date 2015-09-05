package com.antisleuthsecurity.asc_api.common.error;

import java.io.Serializable;

public enum MessagesEnum implements Serializable {
	
	// 1 000 000 - 1 009 999 = Generic System Messages
	SYSTEM_ERROR (1000000, MessageType.ERROR, "System Error, please see logs");
	
	// 2 000 000 - 2 009 999 = Account / Registration Messages
	
	// 3 000 000 - 3 009 999 = Validation Messages
	
	private final Integer messageId;
	private final String message;
	private final MessageType messageType;
	
	private MessagesEnum (Integer messageId, MessageType type, String message){
		this.messageId = messageId;
		this.message = message;
		this.messageType = type;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public Integer getMessageId(){
		return this.messageId;
	}
	
	public MessageType getMessageType(){
		return this.messageType;
	}
	
	@Override
	public String toString(){
		return "[" + this.messageType.name() + ": " + this.messageId + "] " + this.getMessage();
	}
}
