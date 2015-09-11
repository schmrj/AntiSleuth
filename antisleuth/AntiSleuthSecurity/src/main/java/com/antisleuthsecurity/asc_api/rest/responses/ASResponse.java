/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.rest.responses;

import java.io.Serializable;
import java.util.ArrayList;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
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

	public void addMessage(MessagesEnum message) {
		this.addMessage(new Message(message));
	}

	public void addMessage(Message message) {
		this.messages.add(message);
	}

	public void addMessages(Message[] messages) {
		if (messages != null) {
			for (Message message : messages) {
				this.messages.add(message);
			}
		}
	}
}
