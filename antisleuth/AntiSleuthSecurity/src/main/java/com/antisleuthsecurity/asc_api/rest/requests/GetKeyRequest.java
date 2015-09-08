package com.antisleuthsecurity.asc_api.rest.requests;

public class GetKeyRequest {
	private String alias = null;
	private Integer userId = null;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
