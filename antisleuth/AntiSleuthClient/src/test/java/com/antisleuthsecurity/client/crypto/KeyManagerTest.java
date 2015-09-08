package com.antisleuthsecurity.client.crypto;

import java.security.KeyPair;
import java.security.KeyStoreException;

import com.antisleuthsecurity.asc_api.certificates.keymanage.KeystoreManager;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher;
import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.requests.AddKeyRequest;
import com.antisleuthsecurity.asc_api.rest.responses.AddKeyResponse;
import com.sun.jersey.api.client.WebResource;

public class KeyManagerTest {
	public void testKeyManager(WebResource resource) throws AscException, KeyStoreException{
		KeyManager manager = new KeyManager();
		char[] STORE_PASSWORD = "PASSWORD".toCharArray();
		
		KeystoreManager keyStore = new KeystoreManager("E:\\Projects\\GIT\\AntiSleuth\\antisleuth\\AntiSleuthClient\\target\\testKeystore.jks");
		keyStore.init(STORE_PASSWORD);
		
		RsaCipher rsa = new RsaCipher();
		rsa.setStrength(2048);
		KeyPair pair = rsa.generateKeyPair();
		
		AddKeyRequest addKeyRequest = new AddKeyRequest();
		addKeyRequest.setAlias("TEST Key");
		addKeyRequest.setKey(pair.getPublic().getEncoded());
		addKeyRequest.setKeyInstance(pair.getPublic().getAlgorithm());
		AddKeyResponse addKeyResponse = manager.addKey(addKeyRequest, resource);
		System.out.println("Add Key Response: " + addKeyResponse.isSuccess());
		
	}
}
