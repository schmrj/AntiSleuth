package com.antisleuthsecurity.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStoreException;

import com.antisleuthsecurity.asc_api.certificates.keymanage.KeystoreManager;
import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.client.auth.AuthenticationTest;
import com.antisleuthsecurity.client.common.WebServiceClient;
import com.antisleuthsecurity.client.crypto.KeyManagerTest;
import com.antisleuthsecurity.client.messaging.MessageServiceTest;
import com.sun.jersey.api.client.WebResource;

public class TestController {
	public static final String connectionUrl = "http://localhost:8080/AS/api";
	public static WebResource resource = null;

	public static String STORE_PASSWORD = "PASSWORD";
	public static KeystoreManager keyStore = new KeystoreManager(
			"E:\\Projects\\GIT\\AntiSleuth\\antisleuth\\AntiSleuthClient\\target\\testKeystore.jks");

	public static void main(String[] args) {
		try {
			keyStore.init(STORE_PASSWORD.toCharArray());

			resource = new WebServiceClient(connectionUrl).getClient(
					connectionUrl, false);

			AuthenticationTest authTest = new AuthenticationTest();
			KeyManagerTest keyManagerTest = new KeyManagerTest();
			MessageServiceTest mst = new MessageServiceTest();

			authTest.testAuthentication(resource);
			keyManagerTest.testKeyManager(authTest.getAccount(), resource);
			mst.run(authTest.getAccount(), resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
