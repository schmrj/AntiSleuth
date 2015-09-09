package com.antisleuthsecurity.server.rest.validation;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.requests.DeleteKeyRequest;

public class DeleteKeyValidator extends Validator {

	private DeleteKeyRequest request = null;

	public DeleteKeyValidator(DeleteKeyRequest request) {
		this.request = request;
	}

	@Override
	public Message[] getReasons() {

		if (request != null) {
			LoginValidator loginValidator = new LoginValidator(
					request.getAccount());

			if (!loginValidator.isValid()) {
				Message[] messages = loginValidator.getReasons();
				for (Message msg : messages)
					this.messages.add(msg);
			}

			if (isEmpty(request.getKeyAlias()))
				this.messages.add(new Message(MessagesEnum.KEY_ALIAS_MISSING));
		}

		return this.messages.toArray(new Message[this.messages.size()]);
	}

	@Override
	public boolean isValid() {
		if (request != null) {
			LoginValidator loginValidator = new LoginValidator(
					request.getAccount());

			if (!loginValidator.isValid()) {
				return false;
			}

			if (isEmpty(request.getKeyAlias()))
				return false;
		} else
			return false;

		return true;
	}

}
