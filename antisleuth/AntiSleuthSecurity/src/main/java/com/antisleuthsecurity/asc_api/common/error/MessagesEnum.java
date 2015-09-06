package com.antisleuthsecurity.asc_api.common.error;

import java.io.Serializable;

public enum MessagesEnum implements Serializable {
	
	// 1 000 000 - 1 009 999 = Generic System Messages
	SYSTEM_ERROR (1000000, MessageType.ERROR, "System Error, please see logs"),
	METHOD_NOT_IMPLEMENTED (1000001, MessageType.ERROR, "Method requested has not been implemented"),
	
	// 2 000 000 - 2 009 999 = Account / Registration Messages
	REGISTRATION_FAILED (2000001, MessageType.ERROR, "Could not register user"),
	LOGIN_FAILED (2000002, MessageType.ERROR, "Login Failed"),
	
	// 3 000 000 - 3 009 999 = Validation Messages
	MISSING_USERNAME (3000001, MessageType.ERROR, "Username is required"),
	MISSING_PASSWORD (3000002, MessageType.ERROR, "Password is required"),
	MISSING_SALT (3000003, MessageType.ERROR, "SALT is required"),
	ACCOUNT_OBJECT_NULL (3000004, MessageType.ERROR, "Account object cannot be null");
	
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
