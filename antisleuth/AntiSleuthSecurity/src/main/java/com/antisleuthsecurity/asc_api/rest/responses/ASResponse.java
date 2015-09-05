package com.antisleuthsecurity.asc_api.rest.responses;

import java.io.Serializable;
import java.util.ArrayList;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;

public abstract class ASResponse implements Serializable {

	private static final long serialVersionUID = 5092015L; // 05 Sep 2015
	
	protected boolean success = false;
	protected Class<?> responseClass = null;
	private ArrayList<Message> messages = new ArrayList<Message>();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Class<?> getResponseClass() {
		return responseClass;
	}

	public void setResponseClass(Class<?> responseClass) {
		this.responseClass = responseClass;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	public void addMessage(MessagesEnum message){
		this.addMessage(new Message(message));
	}
	
	public void addMessage(Message message){
		this.messages.add(message);
	}
}
