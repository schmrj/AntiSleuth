package com.antisleuthsecurity.server.rest.validation;

public class Validator {
	
	protected boolean isEmpty(String value) {
		if (value != null && !value.isEmpty())
			return false;
		return true;
	}
	
	protected boolean isEmpty(byte[] value) {
		if (value != null && value.length > 0)
			return false;
		return true;
	}
	
	protected boolean isEmpty(char[] value){
		if(value == null || value.length == 0)
			return true;
		return false;
	}
}
