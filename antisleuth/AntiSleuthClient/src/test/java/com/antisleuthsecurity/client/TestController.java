package com.antisleuthsecurity.client;

import java.io.UnsupportedEncodingException;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.client.auth.AuthenticationTest;
import com.antisleuthsecurity.client.common.WebServiceClient;
import com.antisleuthsecurity.client.crypto.KeyManagerTest;
import com.sun.jersey.api.client.WebResource;

public class TestController {
	public static final String connectionUrl = "http://localhost:8080/AS/api";
	public static WebResource resource = null;

	public static void main(String[] args) throws UnsupportedEncodingException,
			AscException {
		resource = new WebServiceClient(connectionUrl).getClient(connectionUrl,
				false);

		AuthenticationTest authTest = new AuthenticationTest();
		KeyManagerTest keyManagerTest = new KeyManagerTest();
		
		authTest.testAuthentication(resource);
		keyManagerTest.testKeyManager(resource);
	}
}
