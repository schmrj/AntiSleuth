/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric;

import static org.junit.Assert.assertNotNull;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import static org.junit.Assert.*;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class DESCipherTest {

	DESCipher des = new DESCipher();
	
	@Test
	public void testGenerateIV(){
		byte[] iv = this.des.generateIV();
		assertNotNull(iv);
		System.out.println("DES IV: " + new String(iv));
	}

	@Test
	public void testGenerateKey() throws NoSuchAlgorithmException {
		byte[] key = this.des.generateKey();
		assertNotNull(key);
		System.out.println("DES Key: " + new String(key));
	}

	@Test
	public void testGetCipher() throws Exception{
		try{
			this.des.generateKey();
			this.des.generateIV();
			this.des.setMode(Cipher.ENCRYPT_MODE);
			Cipher cipher = this.des.getCipher();
			assertNotNull(cipher);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testEncryption() throws Exception{
		try{
			this.des.generateKey();
			this.des.generateIV();
			this.des.setMode(Cipher.ENCRYPT_MODE);
			Cipher cipher = this.des.getCipher();
			assertNotNull(cipher);
			
			byte[] testString = "This is a test to encrypt".getBytes();
			byte[] encrypted = cipher.doFinal(testString);
			
			System.out.println("Original: " + new String(Base64.encode(testString)));
			System.out.println("Encrypted: " + new String(Base64.encode(encrypted)));
			assertNotEquals(new String(testString), new String(encrypted));
			
			cipher = this.des.getCipher(Cipher.DECRYPT_MODE);
			byte[] decrypted = cipher.doFinal(encrypted);
			System.out.println("Decrypted: " + new String(Base64.encode(decrypted)));
			assertEquals(new String(testString), new String(decrypted));
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
