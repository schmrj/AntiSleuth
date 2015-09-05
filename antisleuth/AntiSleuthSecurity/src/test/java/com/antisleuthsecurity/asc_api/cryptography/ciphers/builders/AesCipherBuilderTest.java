package com.antisleuthsecurity.asc_api.cryptography.ciphers.builders;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import static org.junit.Assert.*;
import org.junit.Test;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.builders.AesCipherBuilder;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher.CipherInstance;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher.Strength;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class AesCipherBuilderTest {

	@Test
	public void testAesCipherBuilderEncrypt() throws AscException {
		AesCipher cipher = AesCipherBuilder.setInstance(CipherInstance.AESCBCPKCS5Padding)
				.setStrength(Strength.S128)
				.setEncrypt()
				.build();
		
		assertNotNull(cipher);
	}

	@Test
	public void testAesCipherBuilderDecrypt() throws AscException {
		AesCipher cipher = AesCipherBuilder.setInstance(CipherInstance.AESCBCPKCS5Padding)
				.setStrength(Strength.S128)
				.setDecrypt()
				.build();
		
		assertNotNull(cipher);
	}
	
	@Test
	public void testAesCipherBuilderEncryptSetKey() throws AscException {
		AesCipher cipher = AesCipherBuilder.setInstance(CipherInstance.AESCBCPKCS5Padding)
				.setStrengthByKey()
				.setEncryptMode()
				.setInitializationVector(new byte[] { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 })
				.setKey(new byte[] { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 })
				.build();
		assertNotNull(cipher);
	}
	
	@Test
	public void testAesCipherBuilder() throws AscException, IllegalBlockSizeException, BadPaddingException {
		AesCipher cipher = AesCipherBuilder.setInstance(CipherInstance.AESCBCPKCS5Padding)
				.setStrength(Strength.S128)
				.setEncrypt()
				.build();
		assertNotNull(cipher);
		
		String toEncrypt = "This is a test string!"; 
		Cipher realCipher = cipher.getCipher();
		
		byte[] encrypted = realCipher.doFinal(toEncrypt.getBytes());
		assertNotEquals(toEncrypt, new String(encrypted));
		
		realCipher = cipher.getCipher(Cipher.DECRYPT_MODE);
		
		byte[] decrypted = realCipher.doFinal(encrypted);
		assertEquals(toEncrypt, new String(decrypted));
	}
}
