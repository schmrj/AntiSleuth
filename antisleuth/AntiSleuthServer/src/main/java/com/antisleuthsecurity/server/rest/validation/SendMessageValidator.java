package com.antisleuthsecurity.server.rest.validation;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.crypto.MessageParts;
import com.antisleuthsecurity.asc_api.rest.requests.SendMessageRequest;

public class SendMessageValidator extends Validator {

	SendMessageRequest request = null;

	public SendMessageValidator(SendMessageRequest request) {
		this.request = request;
	}

	@Override
	public Message[] getReasons() {

		if (request != null) {
			if (request.getMsgParts() != null) {
				MessageParts parts = request.getMsgParts();

				if (isEmpty(parts.getMessage()))
					this.messages
							.add(new Message(MessagesEnum.MESSAGE_REQUIRED));
				if (isEmpty(parts.getFrom()))
					this.messages.add(new Message(
							MessagesEnum.MESSAGE_FROM_REQUIRED));
				if (isEmpty(parts.getKeyCipherInstance()))
					this.messages.add(new Message(
							MessagesEnum.MESSAGE_KEY_INST_REQUIRED));
				if (isEmpty(parts.getMessageCipherInstance()))
					this.messages.add(new Message(
							MessagesEnum.MESSAGE_MSG_INST_REQUIRED));
				if (parts.getKeys() != null && parts.getKeys().size() == 0)
					this.messages.add(new Message(
							MessagesEnum.MESSAGE_KEY_REQUIRED));
			}
		} else {
			this.messages.add(new Message(MessagesEnum.SYSTEM_ERROR));
		}

		return this.messages.toArray(new Message[this.messages.size()]);
	}

	@Override
	public boolean isValid() {
		if (request != null) {
			if (request.getMsgParts() != null) {
				MessageParts parts = request.getMsgParts();

				if (isEmpty(parts.getMessage()))
					return false;
				if (isEmpty(parts.getFrom()))
					return false;
				if (isEmpty(parts.getKeyCipherInstance()))
					return false;
				if (isEmpty(parts.getMessageCipherInstance()))
					return false;
				if (parts.getKeys() != null && parts.getKeys().size() == 0)
					return false;
			}
		} else {
			return false;
		}

		return true;
	}

}
