package com.antisleuthsecurity.client.messaging;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.util.Iterator;
import java.util.TreeMap;

import org.bouncycastle.util.encoders.Base64;

import com.antisleuthsecurity.asc_api.certificates.keymanage.KeystoreManager;
import com.antisleuthsecurity.asc_api.cryptography.Cryptographer;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.builders.AesCipherBuilder;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.builders.RsaCipherBuilder;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher;
import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.crypto.MessageParts;

public class MessageDecoder {

	private TreeMap<Integer, MessageParts> messages = null;
	private char[] storePassword = null;
	private KeystoreManager keyStore = null;
	private UserAccount myAccount = null;

	public MessageDecoder(TreeMap<Integer, MessageParts> messages,
			UserAccount myAccount, KeystoreManager storeManager,
			char[] storePassword) throws KeyStoreException, AscException {
		this.messages = messages;
		this.keyStore = storeManager;
		this.storePassword = storePassword;
		this.myAccount = myAccount;
	}

	public void decodeMsg(MessageParts part) throws AscException, IOException {
		String msgInstance = part.getMessageCipherInstance();
		String keyInstance = part.getKeyCipherInstance();

		TreeMap<String, Object> options = part.getOptions();
		byte[] iv = (byte[]) Base64.decode(((String) options.get("IV"))
				.getBytes());
		String alias = (String) options.get("KeyAlias");

		byte[] message = part.getMessage();
		byte[] key = part.getKeys().get(this.myAccount.getUsername());

		PrivateKey rsaKey = (PrivateKey) this.keyStore.getPrivateKey(
				alias.toLowerCase(), this.storePassword);

		RsaCipher rsaCipher = RsaCipherBuilder.getInstance()
				.setInstance(keyInstance).strengthByKey().setDecrypt()
				.setPrivateKey(rsaKey).build();

		Cryptographer keyCrytpo = new Cryptographer(rsaCipher);
		byte[] aesKey = keyCrytpo.process(key);
		System.out.println("Got AES Key");

		AesCipher aesCipher = AesCipherBuilder.setInstance(msgInstance)
				.setStrengthByKey().setDecryptMode()
				.setInitializationVector(iv).setKey(aesKey).build();

		Cryptographer aesCrypto = new Cryptographer(aesCipher);
		byte[] msgDecoded = aesCrypto.process(message);
		System.out.println("Message: " + new String(msgDecoded));
	}

	public void decodeAll() {
		Iterator<Integer> messageKeys = this.messages.keySet().iterator();

		while (messageKeys.hasNext()) {
			try {
				Integer msgKey = messageKeys.next();

				MessageParts part = this.messages.get(msgKey);
				this.decodeMsg(part);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
}
