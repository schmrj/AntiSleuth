package com.antisleuthsecurity.server.rest.validation;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.requests.SendMessageRequest;

public class SendMessageValidator extends Validator {

	SendMessageRequest request = null;
	
	public SendMessageValidator(SendMessageRequest request){
		this.request = request;
	}
	
	@Override
	public Message[] getReasons() {

		if(request != null){
			if(request.getMessages() == null || request.getMessages().size() == 0)
				this.messages.add(new Message(MessagesEnum.MESSAGE_REQUIRED));
			if(isEmpty(request.getFrom()))
				this.messages.add(new Message(MessagesEnum.MESSAGE_FROM_REQUIRED));
			if(isEmpty(request.getKeyCipherInstance()))
				this.messages.add(new Message(MessagesEnum.MESSAGE_KEY_INST_REQUIRED));
			if(isEmpty(request.getMessageCipherInstance()))
				this.messages.add(new Message(MessagesEnum.MESSAGE_MSG_INST_REQUIRED));
			if(request.getKeys() != null && request.getKeys().size() == 0)
				this.messages.add(new Message(MessagesEnum.MESSAGE_KEY_REQUIRED));
		}else{
			this.messages.add(new Message(MessagesEnum.SYSTEM_ERROR));
		}
		
		return this.messages.toArray(new Message[this.messages.size()]);
	}

	@Override
	public boolean isValid() {
		if(request != null){
			if(request.getMessages() == null || request.getMessages().size() == 0)
				return false;
			if(isEmpty(request.getFrom()))
				return false;
			if(isEmpty(request.getKeyCipherInstance()))
				return false;
			if(isEmpty(request.getMessageCipherInstance()))
				return false;
			if(request.getKeys() != null && request.getKeys().size() == 0)
				return false;
		}else{
			return false;
		}
		
		return true;
	}

}
