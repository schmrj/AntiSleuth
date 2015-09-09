package com.antisleuthsecurity.asc_api.rest.requests;

import com.antisleuthsecurity.asc_api.rest.UserAccount;

public class DeleteKeyRequest extends ASRequest {
	private UserAccount account = null;
	private String keyAlias = null;

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

	public String getKeyAlias() {
		return keyAlias;
	}

	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}

}
