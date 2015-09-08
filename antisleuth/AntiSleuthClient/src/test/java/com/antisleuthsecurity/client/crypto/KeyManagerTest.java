package com.antisleuthsecurity.client.crypto;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.requests.AddKeyRequest;
import com.antisleuthsecurity.asc_api.rest.responses.AddKeyResponse;
import com.sun.jersey.api.client.WebResource;

public class KeyManagerTest {
	public void testKeyManager(WebResource resource) throws AscException{
		KeyManager manager = new KeyManager();
		
		AddKeyRequest addKeyRequest = new AddKeyRequest();
		AddKeyResponse addKeyResponse = manager.addKey(addKeyRequest, resource);
	}
}
