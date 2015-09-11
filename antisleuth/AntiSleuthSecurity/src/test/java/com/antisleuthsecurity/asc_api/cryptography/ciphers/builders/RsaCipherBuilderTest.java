/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.ciphers.builders;

import java.security.KeyPair;

import org.junit.Assert;
import org.junit.Test;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher.CipherInstance;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaTestUtils;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class RsaCipherBuilderTest {

	@Test
	public void testRsaEncryptCipherBuilder() throws AscException {
		RsaCipher cipher = RsaCipherBuilder
				.getInstance()
				.setInstance(CipherInstance.RSANONEOAEPWithSHA256AndMGF1Padding)
				.setStrength(1024)
				.setEncryptMode()
				.build();
		
		Assert.assertNotNull(cipher);
	}
	
	@Test
	public void testRsaDecryptCipherBuilder() throws AscException {
		RsaCipher cipher = RsaCipherBuilder
				.getInstance()
				.setInstance(CipherInstance.RSANONEOAEPWithSHA256AndMGF1Padding)
				.setStrength(1024)
				.setDecryptMode()
				.build();
		
		Assert.assertNotNull(cipher);
	}
	
	@Test
	public void testRsaDecryptWithKeyCipherBuilder() throws AscException {
		RsaCipher cipherProvider = RsaTestUtils.buildEncryptCipher();
		KeyPair pair = cipherProvider.generateKeyPair();
		
		RsaCipher cipher = RsaCipherBuilder
				.getInstance()
				.setInstance(CipherInstance.RSANONEOAEPWithSHA256AndMGF1Padding)
				.strengthByKey()
				.setDecrypt()
				.setPrivateKey(pair.getPrivate())
				.build();
		
		Assert.assertNotNull(cipher);
	}
	
	@Test
	public void testRsaEncryptWithKeyCipherBuilder() throws AscException {
		RsaCipher cipherProvider = RsaTestUtils.buildEncryptCipher();
		KeyPair pair = cipherProvider.generateKeyPair();
		
		RsaCipher cipher = RsaCipherBuilder
				.getInstance()
				.setInstance(CipherInstance.RSANONEOAEPWithSHA256AndMGF1Padding)
				.strengthByKey()
				.setEncrypt()
				.setPublicKey(pair.getPublic())
				.build();
		
		Assert.assertNotNull(cipher);
	}

}
