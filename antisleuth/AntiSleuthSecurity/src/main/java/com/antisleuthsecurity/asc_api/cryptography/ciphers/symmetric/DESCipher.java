/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.Ciphers;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class DESCipher extends Ciphers {

	private SecretKey key = null;
	private IvParameterSpec iv = null;
	private Integer mode = null;

	public DESCipher() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	public byte[] generateKey() throws NoSuchAlgorithmException {
		if (key == null) {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			this.key = generator.generateKey();
			return this.key.getEncoded();
		} else
			return this.key.getEncoded();
	}

	public byte[] generateIV() {
		this.iv = new IvParameterSpec(new SecureRandom().generateSeed(8));
		return this.iv.getIV();
	}

	public void setIV(byte[] iv) {
		this.iv = new IvParameterSpec(iv);
	}

	public void setKey(byte[] key) {
		this.key = new SecretKeySpec(this.key.getEncoded(), "DES");
	}

	public void setKey(SecretKey key) {
		this.key = key;
	}

	public void setKey(SecretKeySpec keySpec) {
		this.key = keySpec;
	}

	@Override
	public int getStrength() {
		// DO nothing, DES Is 56 Bit!
		return 56;
	}

	@Deprecated
	@Override
	public int getStrength(Enum strength) {
		// DO nothing, DES Is 56 Bit!
		return getStrength();
	}

	@Deprecated
	@Override
	public void setStrength(int strength) {
		// DO nothing, DES Is 56 Bit!
	}

	@Override
	public Cipher getCipher() throws AscException {
		return this.getCipher(this.mode);
	}

	@Override
	public Cipher getCipher(int mode) throws AscException {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DES/CBC/PKCS5Padding", "BC");
			cipher.init(mode, this.key, iv);
		} catch (Exception e) {
			throw new AscException("Could not generate Cipher", e);
		}

		return cipher;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
}
