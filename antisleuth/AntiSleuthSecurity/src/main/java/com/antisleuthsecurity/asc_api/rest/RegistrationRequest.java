package com.antisleuthsecurity.asc_api.rest;

import java.io.Serializable;

public class RegistrationRequest implements Serializable {
	private UserAccount account = null;

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

}
