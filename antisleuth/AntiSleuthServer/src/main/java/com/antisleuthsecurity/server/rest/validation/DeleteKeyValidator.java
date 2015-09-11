/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.server.rest.validation;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.requests.DeleteKeyRequest;

public class DeleteKeyValidator extends Validator {

	private DeleteKeyRequest request = null;
	private boolean checkAlias = true;

	public DeleteKeyValidator(DeleteKeyRequest request) {
        this.request = request;
    }
	
	public DeleteKeyValidator(DeleteKeyRequest request, boolean checkAlias) {
        this.request = request;
        this.checkAlias = checkAlias;
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

			if (checkAlias && isEmpty(request.getKeyAlias()))
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

			if (checkAlias && isEmpty(request.getKeyAlias()))
				return false;
		} else
			return false;

		return true;
	}

}
