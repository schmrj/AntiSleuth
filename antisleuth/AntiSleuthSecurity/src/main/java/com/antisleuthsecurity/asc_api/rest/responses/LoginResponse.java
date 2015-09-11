/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.rest.responses;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.antisleuthsecurity.asc_api.rest.UserAccount;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LoginResponse extends ASResponse {

	private static final long serialVersionUID = 5092015L; // 05 Sep 2015
	private UserAccount account = null;

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

}
