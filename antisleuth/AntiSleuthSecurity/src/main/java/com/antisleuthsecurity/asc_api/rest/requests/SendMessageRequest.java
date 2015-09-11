package com.antisleuthsecurity.asc_api.rest.requests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

import com.antisleuthsecurity.asc_api.rest.UserAccount;

public class SendMessageRequest extends ASRequest implements Serializable {

	private String keyCipherInstance = null;
	private String messageCipherInstance = null;

	private UserAccount from = null;

	private TreeMap<String, byte[]> keys = new TreeMap<String, byte[]>();
	private TreeMap<String, Object> options = new TreeMap<String, Object>();
	private TreeMap<String, byte[]> messages = new TreeMap<String, byte[]>();

	public UserAccount getFrom() {
		return from;
	}

	public void setFrom(UserAccount from) {
		this.from = from;
	}

	public TreeMap<String, byte[]> getKeys() {
		return keys;
	}

	public void setKeys(TreeMap<String, byte[]> keys) {
		this.keys = keys;
	}

	public void addKey(String username, byte[] key) {
		this.keys.put(username, key);
	}

	public TreeMap<String, byte[]> getMessages() {
		return messages;
	}

	public void setMessages(TreeMap<String, byte[]> messages) {
		this.messages = messages;
	}

	public void addMessage(String username, byte[] message) {
		this.messages.put(username, message);
	}

	public String getKeyCipherInstance() {
		return keyCipherInstance;
	}

	public void setKeyCipherInstance(String keyCipherInstance) {
		this.keyCipherInstance = keyCipherInstance;
	}

	public String getMessageCipherInstance() {
		return messageCipherInstance;
	}

	public void setMessageCipherInstance(String messageCipherInstance) {
		this.messageCipherInstance = messageCipherInstance;
	}

	public TreeMap<String, Object> getOptions() {
		return options;
	}

	public void setOptions(TreeMap<String, Object> options) {
		this.options = options;
	}

	public void addOption(String name, Object item) {
		this.options.put(name, item);
	}
}
