package com.antisleuthsecurity.server.rest.validation;

import java.util.ArrayList;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.UserAccount;

public class NewAccountValidator extends Validator {
	private UserAccount account = null;

	public NewAccountValidator(UserAccount account) {
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

		// Do not check for email, email will
		// be voluntary
		if (isEmpty(account.getUsername()))
			return false;
		if (isEmpty(account.getSalt()))
			return false;
		if (isEmpty(account.getPassword()))
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
			if (isEmpty(account.getSalt()))
				messages.add(new Message(MessagesEnum.MISSING_SALT));
			if (isEmpty(account.getPassword()))
				messages.add(new Message(MessagesEnum.MISSING_PASSWORD));
		}
		return messages.toArray(new Message[messages.size()]);
	}
}
