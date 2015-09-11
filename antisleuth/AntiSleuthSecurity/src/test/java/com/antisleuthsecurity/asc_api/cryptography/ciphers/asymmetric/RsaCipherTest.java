/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric;

import java.security.KeyPair;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher.CipherInstance;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class RsaCipherTest {

	RsaCipher cipher = null;
	KeyPair pair = null;
	
	@Before
	public void prepareTests() throws AscException{
		this.cipher = RsaTestUtils.buildDecryptCipher();
		this.cipher.setStrength(1024);
		this.pair = this.cipher.generateKeyPair();
	}
		
	@Test
	public void testGetEncryptMode() throws AscException{
		RsaCipher cipher = RsaTestUtils.buildEncryptCipher();
		Assert.assertEquals(Cipher.ENCRYPT_MODE, cipher.getMode());
	}
	
	@Test
	public void testGetDencryptMode() throws AscException{
		RsaCipher cipher = RsaTestUtils.buildDecryptCipher();
		Assert.assertEquals(Cipher.DECRYPT_MODE, cipher.getMode());
	}
	
	@Test
	public void testGetInstance(){
		Assert.assertEquals(CipherInstance.RSANONEOAEPWithSHA256AndMGF1Padding, this.cipher.getInstance());
	}
	
	@Test
	public void testGetSetInstance1(){
		Assert.assertEquals("RSA/NONE/OAEPWithSHA256AndMGF1Padding", this.cipher.getCipherInstance());
	}
	
	@Test
	public void testSetStrength(){
		this.cipher.setStrength(2048);
		Assert.assertEquals(2048, this.cipher.getStrength());
	}
	
	@Test
	public void testInvalidStrength() {
		this.cipher.setStrength(1025);
		Assert.assertNotEquals(1025, this.cipher.getStrength());
	}

	@Test
	public void testGetDecryptCipher() throws AscException{
		Cipher cipher = this.cipher.getCipher();
		Assert.assertNotNull(cipher);
	}
	
	@Test
	public void testGetEncryptCipher() throws AscException{
		Cipher cipher = RsaTestUtils.buildEncryptCipher().getCipher();
		Assert.assertNotNull(cipher);
	}
	
	@Test
	public void testEncryptContent() throws AscException, IllegalBlockSizeException, BadPaddingException{
		String toEncrypt = "This is a sample string";
		
		RsaCipher cipher = RsaTestUtils.buildEncryptCipher();
		Cipher realCipher = cipher.getCipher();
		
		byte[] encrypted = realCipher.doFinal(toEncrypt.getBytes());
		Assert.assertNotEquals(toEncrypt, new String(encrypted));
		
		PrivateKey key = cipher.getPrivateKey();
		cipher.setMode(Cipher.DECRYPT_MODE);
		realCipher = cipher.getCipher();
		byte[] decrypted = realCipher.doFinal(encrypted);
		
		Assert.assertEquals(toEncrypt, new String(decrypted));
	}
}
