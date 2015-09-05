package com.antisleuthsecurity.asc_api.rest.responses;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.antisleuthsecurity.asc_api.rest.UserAccount;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RegistrationResponse extends ASResponse {

	private static final long serialVersionUID = 5092015L; // 05 Sep 2015
	protected UserAccount account = null;

	public UserAccount getUserAccount() {
		return account;
	}

	public void setUserAccount(UserAccount account) {
		this.account = account;
		this.setResponseClass(account.getClass());
	}

}
