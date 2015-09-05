package com.antisleuthsecurity.asc_api.rest.responses;

import java.io.Serializable;
import java.util.TreeMap;

import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;

public abstract class ASResponse implements Serializable {

	private static final long serialVersionUID = 5092015L; // 05 Sep 2015

	protected boolean success = false;
	protected TreeMap<Integer, String> messages = new TreeMap<Integer, String>();
	protected Class<?> responseClass = null;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public TreeMap<Integer, String> getMessages() {
		return messages;
	}

	public void setMessages(TreeMap<Integer, String> messages) {
		this.messages = messages;
	}

	public void addMessage(MessagesEnum message) {
		this.messages.put(message.getMessageId(), message.getMessage());
	}

	public Class<?> getResponseClass() {
		return responseClass;
	}

	public void setResponseClass(Class<?> responseClass) {
		this.responseClass = responseClass;
	}
}
