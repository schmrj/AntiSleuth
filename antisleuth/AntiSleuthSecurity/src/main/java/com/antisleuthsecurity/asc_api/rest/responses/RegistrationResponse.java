package com.antisleuthsecurity.asc_api.rest.responses;

public class RegistrationResponse extends ASResponse{

	private static final long serialVersionUID = 5092015L; // 05 Sep 2015

	@Override
	public <T> T getResponse() {
		return (T) this.responseClass.cast(response);
	}

}
