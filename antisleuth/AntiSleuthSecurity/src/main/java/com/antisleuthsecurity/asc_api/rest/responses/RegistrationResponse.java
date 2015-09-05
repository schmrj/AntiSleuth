package com.antisleuthsecurity.asc_api.rest.responses;

import com.antisleuthsecurity.asc_api.rest.UserAccount;

public class RegistrationResponse extends ASResponse {

	private static final long serialVersionUID = 5092015L; // 05 Sep 2015
	protected UserAccount userAccount = null;

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
