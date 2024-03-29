/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite � 2015
 */
package com.antisleuthsecurity.server.rest.validation;

import java.util.ArrayList;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.rest.UserAccount;

public abstract class Validator {

	protected ArrayList<Message> messages = new ArrayList<Message>();

	protected boolean isEmpty(String value) {
		if (value != null && !value.isEmpty())
			return false;
		return true;
	}
	
	protected boolean isEmpty(UserAccount value) {
		if (value != null && !isEmpty(value.getUsername()))
			return false;
		if (value != null && !isEmpty(value.getUserId()))
			return false;
		return true;
	}
	
	protected boolean isEmpty(Integer value) {
		if (value != null)
			return false;
		return true;
	}

	protected boolean isEmpty(byte[] value) {
		if (value != null && value.length > 0)
			return false;
		return true;
	}

	protected boolean isEmpty(char[] value) {
		if (value == null || value.length == 0)
			return true;
		return false;
	}

	public abstract Message[] getReasons();

	public abstract boolean isValid();
}
