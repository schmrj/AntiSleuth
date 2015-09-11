package com.antisleuthsecurity.client.crypto;

import java.security.KeyPair;
import java.security.KeyStoreException;

import com.antisleuthsecurity.asc_api.certificates.keymanage.KeystoreManager;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher;
import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.AddKeyRequest;
import com.antisleuthsecurity.asc_api.rest.requests.DeleteKeyRequest;
import com.antisleuthsecurity.asc_api.rest.requests.GetKeyRequest;
import com.antisleuthsecurity.asc_api.rest.responses.AddKeyResponse;
import com.antisleuthsecurity.asc_api.rest.responses.DeleteKeyResponse;
import com.antisleuthsecurity.asc_api.rest.responses.GetKeyResponse;
import com.sun.jersey.api.client.WebResource;

public class KeyManagerTest {
	public void testKeyManager(UserAccount account, WebResource resource) throws AscException, KeyStoreException{
		KeyManager manager = new KeyManager();
		char[] STORE_PASSWORD = "PASSWORD".toCharArray();
		
		KeystoreManager keyStore = new KeystoreManager("D:\\_Personal Git\\AntiSleuth\\antisleuth\\AntiSleuthClient\\target\\testKeystore.jks");
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
		
		GetKeyRequest getKeyRequest = new GetKeyRequest();
		getKeyRequest.setAlias("TEST KEY");
		getKeyRequest.setUserId(account.getUserId());
		GetKeyResponse getKeyResponse = manager.getKey(getKeyRequest, resource);
		System.out.println("Get Key Response: " + getKeyResponse.isSuccess());
		
		GetKeyResponse getAllKeyResponse = manager.getAllUserKey(getKeyRequest, resource);
		System.out.println("Get All Keys Response: " + getKeyResponse.isSuccess());
		
		DeleteKeyRequest deleteKeyRequest = new DeleteKeyRequest();
		deleteKeyRequest.setAccount(account);
		deleteKeyRequest.setKeyAlias("TEST KEY");
		DeleteKeyResponse deleteKeyResponse = manager.deleteKey(deleteKeyRequest, resource);
		System.out.println("Delete Key Response: " + deleteKeyResponse.isSuccess());
		
		DeleteKeyRequest panicRequest = new DeleteKeyRequest();
		panicRequest.setAccount(account);
		DeleteKeyResponse panicDeleteresponse = manager.panicDeleteKeys(panicRequest, resource);
		System.out.println("Panic Delete Keye: " + panicDeleteresponse.isSuccess());
	}
}
