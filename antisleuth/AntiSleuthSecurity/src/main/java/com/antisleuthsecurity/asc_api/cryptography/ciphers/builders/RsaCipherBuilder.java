/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.ciphers.builders;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher.CipherInstance;
import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.utilities.ASLog;

public class RsaCipherBuilder {

	private RsaCipherBuilder() {

	}

	public static SetInstance getInstance() {
		return new Builder();
	}

	public static interface SetInstance {
		SetStrength setInstance(CipherInstance instance);

		SetStrength setInstance(String instance);
	}

	public static interface SetStrength {
		GenerateKeyPair setStrength(int strength);

		SetCipherMode strengthByKey();
	}

	public static interface SetCipherMode {
		SetPublicKey setEncrypt();

		SetPrivateKey setDecrypt();
	}

	public static interface GenerateKeyPair {
		GetBuilder setEncryptMode();

		GetBuilder setDecryptMode();
	}

	public static interface SetPublicKey {
		GetBuilder setPublicKey(PublicKey key);

		GetBuilder setPublicKey(byte[] key);
	}

	public static interface SetPrivateKey {
		GetBuilder setPrivateKey(PrivateKey key);
	}

	public static interface GetBuilder {
		RsaCipher build() throws AscException;
	}

	static class Builder implements SetInstance, SetCipherMode, SetStrength,
			SetPublicKey, SetPrivateKey, GetBuilder, GenerateKeyPair {
		private int strength = 1024;
		private PrivateKey privateKey = null;
		private PublicKey publicKey = null;
		private CipherInstance instance = null;
		private int mode = Cipher.ENCRYPT_MODE;

		public RsaCipher build() throws AscException {
			RsaCipher cipher = new RsaCipher();
			cipher.setStrength(strength);
			cipher.setInstance(instance);
			cipher.setMode(mode);

			if (privateKey == null && publicKey == null) {
				KeyPair pair = cipher.generateKeyPair();
			} else {
				if (mode == Cipher.ENCRYPT_MODE)
					cipher.setPublicKey(publicKey);
				else
					cipher.setPrivateKey(privateKey);
			}

			return cipher;
		}

		public GetBuilder setPrivateKey(PrivateKey key) {
			this.privateKey = key;
			return this;
		}

		public GetBuilder setPublicKey(PublicKey key) {
			this.publicKey = key;
			return this;
		}

		public GetBuilder setPublicKey(byte[] key) {
			try {
				PublicKey pKey = null;
				pKey = KeyFactory.getInstance("RSA").generatePublic(
						new X509EncodedKeySpec(key));
				this.publicKey = pKey;
			} catch (Exception e) {
				ASLog.error("Could not set public key", e);
			}
			return this;
		}

		public GenerateKeyPair setStrength(int strength) {
			this.strength = strength;
			return this;
		}

		public SetCipherMode strengthByKey() {
			return this;
		}

		public SetPublicKey setEncrypt() {
			this.mode = Cipher.ENCRYPT_MODE;
			return this;
		}

		public SetPrivateKey setDecrypt() {
			this.mode = Cipher.DECRYPT_MODE;
			return this;
		}

		public SetStrength setInstance(CipherInstance instance) {
			this.instance = instance;
			return this;
		}

		public SetStrength setInstance(String cipherInstance) {
			CipherInstance[] instances = RsaCipher.CipherInstance.values();

			for (CipherInstance inst : instances) {
				if (inst.getValue().equalsIgnoreCase(cipherInstance)) {
					this.instance = inst;
					break;
				} else
					this.instance = CipherInstance.RSANONEOAEPWithMD5AndMGF1Padding;
			}

			return this;
		}

		public GetBuilder setEncryptMode() {
			this.mode = Cipher.ENCRYPT_MODE;
			return this;
		}

		public GetBuilder setDecryptMode() {
			this.mode = Cipher.DECRYPT_MODE;
			return this;
		}
	}
}
