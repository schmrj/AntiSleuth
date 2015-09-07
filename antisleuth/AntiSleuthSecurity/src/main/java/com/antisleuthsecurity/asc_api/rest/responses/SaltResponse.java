package com.antisleuthsecurity.asc_api.rest.responses;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.antisleuthsecurity.asc_api.rest.UserAccount;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SaltResponse extends ASResponse {
	private UserAccount account = null;

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

}
