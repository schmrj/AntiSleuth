package com.antisleuthsecurity.asc_api.rest.responses;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.antisleuthsecurity.asc_api.rest.crypto.ASKey;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GetKeyResponse extends ASResponse {

	private ASKey[] keys = null;

	public ASKey[] getKeys() {
		return keys;
	}

	public void setKeys(ASKey[] keys) {
		this.keys = keys;
	}
}
