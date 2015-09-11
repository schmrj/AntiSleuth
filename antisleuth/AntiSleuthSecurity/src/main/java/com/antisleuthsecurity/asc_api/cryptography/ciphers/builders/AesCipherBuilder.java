package com.antisleuthsecurity.asc_api.cryptography.ciphers.builders;

import javax.crypto.Cipher;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher.CipherInstance;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher.Strength;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class AesCipherBuilder {
	private AesCipherBuilder() {

	}

	public static SetStrength setInstance(CipherInstance instance) {
		return new Builder().setInstance(instance);
	}

	public static SetStrength setInstance(String instance) {
		return new Builder().setInstance(instance);
	}

	public static interface SetInstance {
		SetStrength setInstance(CipherInstance instance);

		SetStrength setInstance(String instance);
	}

	public static interface SetStrength {
		GenerateKey setStrength(Strength strength);

		SetMode setStrengthByKey();
	}

	public static interface SetMode {
		SetInitializationVector setEncryptMode();

		SetInitializationVector setDecryptMode();
	}

	public static interface SetInitializationVector {
		SetKey setInitializationVector(byte[] iv);
	}

	public static interface SetKey {
		GetBuilder setKey(byte[] key);
	}

	public static interface GenerateKey {
		SetCipherMode generateKeys() throws AscException;
	}

	public static interface SetCipherMode {
		GetBuilder setEncrypt() throws AscException;

		GetBuilder setDecrypt() throws AscException;
	}

	public static interface GetBuilder {
		AesCipher build() throws AscException;
	}

	static class Builder implements SetInstance, SetStrength, SetMode,
			SetInitializationVector, SetKey, GenerateKey, GetBuilder, SetCipherMode {

		private AesCipher cipher = new AesCipher();
		private Strength strength = Strength.S128;
		private byte[] key = null;
		private byte[] iv = null;
		private int mode;
		private CipherInstance instance;

		public AesCipher build() throws AscException {
			this.cipher.setInstance(instance);
			this.cipher.setMode(mode);

			if (this.key != null) {
				this.cipher.setKey(key);
				this.cipher.setIv(iv);
			} else
				this.cipher.setStrength(strength);
			return cipher;
		}

		public void generateKeyParts() throws AscException {
			cipher.generateKey();
			cipher.generateIV();
		}

		public GetBuilder setEncrypt() throws AscException {
			generateKeyParts();
			setEncryptMode();
			return this;
		}

		public GetBuilder setDecrypt() throws AscException {
			generateKeyParts();
			setDecryptMode();
			return this;
		}

		public SetCipherMode generateKeys() throws AscException {
			AesCipher cipher = new AesCipher(this.strength);
			this.cipher.setKey(cipher.generateKey());
			this.cipher.setIv(cipher.generateIV());

			return this;
		}

		public GetBuilder setKey(byte[] key) {
			this.key = key;
			return this;
		}

		public SetKey setInitializationVector(byte[] iv) {
			this.iv = iv;
			return this;
		}

		public SetInitializationVector setEncryptMode() {
			this.mode = Cipher.ENCRYPT_MODE;
			return this;
		}

		public SetInitializationVector setDecryptMode() {
			this.mode = Cipher.DECRYPT_MODE;
			return this;
		}

		public SetMode setStrengthByKey() {
			return this;
		}

		public SetStrength setInstance(CipherInstance instance) {
			this.instance = instance;
			return this;
		}

		public SetStrength setInstance(String instance) {
			CipherInstance cInstance = null;
			CipherInstance[] instances = CipherInstance.values();

			for (CipherInstance inst : instances) {
				if (inst.getValue().equalsIgnoreCase(instance)) {
					cInstance = inst;
					break;
				} else
					cInstance = CipherInstance.AESCBCPKCS5Padding;
			}

			return new Builder().setInstance(cInstance);
		}

		public GenerateKey setStrength(Strength strength) {
			this.strength = strength;
			return this;
		}

	}
}
