package com.antisleuthsecurity.asc_api.rest.crypto;

import java.io.Serializable;
import java.util.TreeMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.antisleuthsecurity.asc_api.rest.UserAccount;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MessageParts implements Serializable {
	private String keyCipherInstance = null;
	private String messageCipherInstance = null;

	private UserAccount from = null;

	private TreeMap<String, byte[]> keys = new TreeMap<String, byte[]>();
	private TreeMap<String, Object> options = new TreeMap<String, Object>();
	private byte[] message = null;

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

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public void addMessage(byte[] message) {
		this.message = message;
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
