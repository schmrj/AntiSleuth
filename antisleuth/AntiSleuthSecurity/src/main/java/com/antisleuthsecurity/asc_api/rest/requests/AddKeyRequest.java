package com.antisleuthsecurity.asc_api.rest.requests;

import java.io.Serializable;

import com.antisleuthsecurity.asc_api.rest.UserAccount;

public class AddKeyRequest implements Serializable {
	private UserAccount account = null;
	private String alias = null;
	private String keyInstance = null;
	private byte[] key = null;

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public String getKeyInstance() {
		return keyInstance;
	}

	public void setKeyInstance(String keyInstance) {
		this.keyInstance = keyInstance;
	}

}
