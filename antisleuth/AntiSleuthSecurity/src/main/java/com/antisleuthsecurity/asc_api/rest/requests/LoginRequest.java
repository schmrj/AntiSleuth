/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.rest.requests;

import java.io.Serializable;

import com.antisleuthsecurity.asc_api.rest.UserAccount;

public class LoginRequest extends ASRequest implements Serializable{
	private UserAccount account = null;

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}
	
}
