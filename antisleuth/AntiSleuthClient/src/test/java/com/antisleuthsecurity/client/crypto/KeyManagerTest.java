package com.antisleuthsecurity.client.crypto;

import java.security.KeyPair;
import java.security.KeyStoreException;

import com.antisleuthsecurity.asc_api.certificates.keymanage.KeystoreManager;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher;
import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.AddKeyRequest;
import com.antisleuthsecurity.asc_api.rest.requests.GetKeyRequest;
import com.antisleuthsecurity.asc_api.rest.responses.AddKeyResponse;
import com.antisleuthsecurity.asc_api.rest.responses.GetKeyResponse;
import com.sun.jersey.api.client.WebResource;

public class KeyManagerTest {
	public void testKeyManager(UserAccount account, WebResource resource) throws AscException, KeyStoreException{
		KeyManager manager = new KeyManager();
		char[] STORE_PASSWORD = "PASSWORD".toCharArray();
		
		KeystoreManager keyStore = new KeystoreManager("E:\\Projects\\GIT\\AntiSleuth\\antisleuth\\AntiSleuthClient\\target\\testKeystore.jks");
		keyStore.init(STORE_PASSWORD);
		
		RsaCipher rsa = new RsaCipher();
		rsa.setStrength(2048);
		KeyPair pair = rsa.generateKeyPair();
		
		AddKeyRequest addKeyRequest = new AddKeyRequest();
		addKeyRequest.setAlias("TEST Key 2");
		addKeyRequest.setKey(pair.getPublic().getEncoded());
		addKeyRequest.setKeyInstance(pair.getPublic().getAlgorithm());
		AddKeyResponse addKeyResponse = manager.addKey(addKeyRequest, resource);
		System.out.println("Add Key Response: " + addKeyResponse.isSuccess());
		
		GetKeyRequest getKeyRequest = new GetKeyRequest();
		getKeyRequest.setAlias("TEST KEY");
		getKeyRequest.setUserId(account.getUserId());
		GetKeyResponse getKeyResponse = manager.getKey(getKeyRequest, resource);
		System.out.println("Get Key Response: " + getKeyResponse.isSuccess());
		
		GetKeyResponse getAllKeyResponse = manager.getAllUserKey(getKeyRequest, resource);
		System.out.println("Get All Keys Response: " + getKeyResponse.isSuccess());
	}
}
