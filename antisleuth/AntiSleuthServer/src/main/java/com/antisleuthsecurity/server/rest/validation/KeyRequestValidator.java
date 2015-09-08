package com.antisleuthsecurity.server.rest.validation;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.requests.GetKeyRequest;

public class KeyRequestValidator extends Validator {

	private GetKeyRequest request = null;

	public KeyRequestValidator(GetKeyRequest request) {
		this.request = request;
	}

	@Override
	public Message[] getReasons() {
		if (request != null) {
			if (isEmpty(request.getAlias()))
				messages.add(new Message(MessagesEnum.KEY_ALIAS_MISSING));
			if (isEmpty(request.getUserId()))
				messages.add(new Message(MessagesEnum.MISSING_USERID));
		}
		return this.messages.toArray(new Message[this.messages.size()]);
	}

	@Override
	public boolean isValid() {
		if (request != null) {
			if (isEmpty(request.getAlias()))
				return false;
			if (isEmpty(request.getUserId()))
				return false;
		}else
			return false;

		return true;
	}

}
