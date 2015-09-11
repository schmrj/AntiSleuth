package com.antisleuthsecurity.client.messaging;

import java.io.IOException;
import java.security.KeyStoreException;
import java.util.Calendar;

import org.codehaus.jackson.map.ObjectMapper;

import com.antisleuthsecurity.asc_api.cryptography.Cryptographer;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher.CipherInstance;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.builders.AesCipherBuilder;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.builders.RsaCipherBuilder;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher.Strength;
import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.crypto.ASKey;
import com.antisleuthsecurity.asc_api.rest.crypto.MessageParts;
import com.antisleuthsecurity.asc_api.rest.crypto.SecureMessage;
import com.antisleuthsecurity.asc_api.rest.requests.GetKeyRequest;
import com.antisleuthsecurity.asc_api.rest.requests.SendMessageRequest;
import com.antisleuthsecurity.asc_api.rest.responses.GetKeyResponse;
import com.antisleuthsecurity.asc_api.rest.responses.GetMessageResponse;
import com.antisleuthsecurity.asc_api.rest.responses.SendMessageResponse;
import com.antisleuthsecurity.client.TestController;
import com.antisleuthsecurity.client.crypto.KeyManager;
import com.sun.jersey.api.client.WebResource;

public class MessageServiceTest {

	public void run(UserAccount account, WebResource resource)
			throws AscException, IOException, KeyStoreException {
		Messaging msging = new Messaging();
		KeyManager manager = new KeyManager();

		GetKeyRequest getKeyRequest = new GetKeyRequest();
		getKeyRequest.setAlias("TEST KEY");
		getKeyRequest.setUserId(account.getUserId());
		GetKeyResponse getKeyResponse = manager.getKey(getKeyRequest, resource);
		System.out.println("Get Key Response: " + getKeyResponse.isSuccess());

		if (getKeyResponse.isSuccess()) {

			ASKey[] keys = getKeyResponse.getKeys();
			ASKey key = null;
			if (keys.length == 1) {
				key = keys[0];
			}

			AesCipher aesCipher = AesCipherBuilder
					.setInstance(AesCipher.CipherInstance.AESCBCPKSC7Padding)
					.setStrength(Strength.S256).generateKeys().setEncrypt()
					.build();

			RsaCipher rsaCipher = RsaCipherBuilder
					.getInstance()
					.setInstance(
							CipherInstance.RSANONEOAEPWithSHA256AndMGF1Padding)
					.strengthByKey().setEncrypt().setPublicKey(key.getKey())
					.build();

			System.out
					.println("Have framwork in place to encrypt the message!");

			MessageParts msgParts = new MessageParts();
			SendMessageRequest sendMsgRequest = new SendMessageRequest();
			msgParts.setMessageCipherInstance(aesCipher.getInstance());
			msgParts.setKeyCipherInstance(rsaCipher.getCipherInstance());

			byte[] encryptedKey = new Cryptographer(rsaCipher)
					.process(aesCipher.getKey());
			msgParts.addKey(account.getUsername(), encryptedKey);
			msgParts.addOption("IV", aesCipher.getIv());
			msgParts.addOption("KeyAlias", key.getAlias());

			UserAccount from = new UserAccount();
			from.setUserId(account.getUserId());
			from.setUsername(account.getUsername());
			msgParts.setFrom(from);

			SecureMessage secureMsg = new SecureMessage();
			secureMsg.setFrom(from.getUsername());
			secureMsg.setSubject("This is a test message");
			secureMsg.setBody("This is the body of a test message");
			secureMsg.setSent(Calendar.getInstance().getTime());
			String msg = new ObjectMapper().writeValueAsString(secureMsg);
			byte[] encodedMsg = new Cryptographer(aesCipher).process(msg
					.getBytes());
			msgParts.addMessage(encodedMsg);

			sendMsgRequest.setMsgParts(msgParts);
			SendMessageResponse sndMsgResponse = msging.sendMessage(
					sendMsgRequest, resource);
			System.out.println("Send Message: " + sndMsgResponse.isSuccess());

			GetMessageResponse getMsgResponse = msging.getMessages(resource);
			System.out.println("Get Message Response: "
					+ getMsgResponse.isSuccess());

			MessageDecoder decoder = new MessageDecoder(
					getMsgResponse.getMsgs(), account, TestController.keyStore,
					TestController.STORE_PASSWORD.toCharArray());

			decoder.decodeAll();
		}
	}
}
