package com.antisleuthsecurity.asc_api.rest.requests;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.antisleuthsecurity.asc_api.rest.UserAccount;

public class RegistrationRequest extends ASRequest implements Serializable {

	private static final long serialVersionUID = 5092015L; // 05 Sep 2015
	private UserAccount account = null;

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

}
