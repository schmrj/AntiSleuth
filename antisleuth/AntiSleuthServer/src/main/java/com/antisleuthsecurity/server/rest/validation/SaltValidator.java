/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.server.rest.validation;

import java.util.ArrayList;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.UserAccount;

public class SaltValidator extends Validator{
	
	private UserAccount account = null;
	
	public SaltValidator(UserAccount account){
		this.account = account;
	}
	
	/**
	 * Check whether or not the user account is valid.
	 * 
	 * @return
	 */
	public boolean isValid() {
		boolean status = true;

		if (account == null)
			return false;
		
		if (isEmpty(account.getUsername()))
			return false;

		return status;
	}

	public Message[] getReasons() {
		ArrayList<Message> messages = new ArrayList<Message>();

		if (account == null) {
			messages.add(new Message(MessagesEnum.ACCOUNT_OBJECT_NULL));
		} else {
			if (isEmpty(account.getUsername()))
				messages.add(new Message(MessagesEnum.MISSING_USERNAME));
		}
		return messages.toArray(new Message[messages.size()]);
	}
}
