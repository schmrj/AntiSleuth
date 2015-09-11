/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.ciphers;

import java.security.Security;

import javax.crypto.Cipher;

import com.antisleuthsecurity.asc_api.exceptions.AscException;


public abstract class Ciphers {
	
	protected int mode = Cipher.ENCRYPT_MODE;
	
	public static enum KeyCiphers{
		RSA
	}
	
	public Ciphers(){
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	public abstract int getStrength();
	public abstract int getStrength(Enum strength);
	
	public abstract void setStrength(int strength);
	
	public abstract Cipher getCipher() throws AscException;
	public abstract Cipher getCipher(int mode) throws AscException;
	
	public void setCipherMode(int mode){
		this.mode = mode;
	}
}
