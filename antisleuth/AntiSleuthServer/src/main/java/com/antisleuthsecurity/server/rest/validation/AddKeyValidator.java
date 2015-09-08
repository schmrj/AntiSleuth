package com.antisleuthsecurity.server.rest.validation;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.requests.AddKeyRequest;

public class AddKeyValidator extends Validator {
	private AddKeyRequest akr = null;

	public AddKeyValidator(AddKeyRequest request) {
		this.akr = request;
	}

	@Override
	public Message[] getReasons() {
		if(akr != null){
			if(isEmpty(akr.getAlias()))
				messages.add(new Message(MessagesEnum.KEY_ALIAS_MISSING));
			if(isEmpty(akr.getKey()))
				messages.add(new Message(MessagesEnum.KEY_MISSING));
			if(isEmpty(akr.getKeyInstance()))
				messages.add(new Message(MessagesEnum.KEY_INSTANCE_MISSING));
		}else{
			messages.add(new Message(MessagesEnum.ADD_KEY_REQUEST_MISSING));
		}
			
		return messages.toArray(new Message[messages.size()]);
	}

	@Override
	public boolean isValid() {
		if(akr != null){
			if(isEmpty(akr.getAlias()))
				return false;
			if(isEmpty(akr.getKey()))
				return false;
			if(isEmpty(akr.getKeyInstance()))
				return false;
		}else{
			return false;
		}
		return true;
	}
	
}
