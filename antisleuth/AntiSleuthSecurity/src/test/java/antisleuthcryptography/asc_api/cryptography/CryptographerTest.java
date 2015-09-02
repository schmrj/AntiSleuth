package antisleuthcryptography.asc_api.cryptography;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.crypto.Cipher;

import org.junit.Assert;
import org.junit.Test;

import com.antisleuthsecurity.asc_api.cryptography.Cryptographer;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.Ciphers;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.builders.AesCipherBuilder;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher.CipherInstance;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher.Strength;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class CryptographerTest {
	@Test
	public void testProcessByteArray() throws AscException, IOException{
		String toEncrypt = "This is a string to encrypt";
		AesCipher cipher = AesCipherBuilder.setInstance(CipherInstance.AESCBCPKCS5Padding)
				.setStrength(Strength.S128)
				.setEncrypt()
				.build();
		Assert.assertNotNull(cipher);
		
		Cryptographer crypter = new Cryptographer(cipher);
		byte[] encrypted = crypter.process(toEncrypt.getBytes());
		Assert.assertNotEquals(toEncrypt, new String(encrypted));
		
		crypter.setCipherMode(Cipher.DECRYPT_MODE);
		byte[] decrypted = crypter.process(encrypted);
		
		Assert.assertEquals(toEncrypt, new String(decrypted));
	}
	
	@Test
	public void testGetCipher() throws AscException{
		AesCipher cipher = AesCipherBuilder.setInstance(CipherInstance.AESCBCPKCS5Padding)
				.setStrength(Strength.S128)
				.setEncrypt()
				.build();
		Assert.assertNotNull(cipher);
		Cryptographer crypter = new Cryptographer(cipher);
		
		Ciphers ciphers = crypter.getCipher();
		Assert.assertNotNull(ciphers);
	}
	
	@Test
	public void testProcessString() throws AscException, IOException{
		String toEncrypt = "This is a string to encrypt";
		AesCipher cipher = AesCipherBuilder.setInstance(CipherInstance.AESCBCPKCS5Padding)
				.setStrength(Strength.S128)
				.setEncrypt()
				.build();
		Assert.assertNotNull(cipher);
		
		Cryptographer crypter = new Cryptographer(cipher);
		byte[] encrypted = crypter.process(toEncrypt);
		Assert.assertNotEquals(toEncrypt, new String(encrypted));
		
		crypter.setCipherMode(Cipher.DECRYPT_MODE);
		byte[] decrypted = crypter.process(encrypted);
		
		Assert.assertEquals(toEncrypt, new String(decrypted));
	}
	
	@Test
	public void testProcessStream() throws AscException, IOException{
		String toEncryptString = "This is my super secret text";
		AesCipher cipher = AesCipherBuilder.setInstance(CipherInstance.AESCBCPKCS5Padding)
				.setStrength(Strength.S128)
				.setEncrypt()
				.build();
		Assert.assertNotNull(cipher);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(toEncryptString.getBytes());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		Cryptographer crypter = new Cryptographer(cipher);
		crypter.processStream(bais, baos);
		
		Assert.assertNotEquals(toEncryptString, new String(baos.toByteArray()));
		crypter.setCipherMode(Cipher.DECRYPT_MODE);
		bais = new ByteArrayInputStream(baos.toByteArray());
		baos = new ByteArrayOutputStream();
		
		crypter.processStream(bais, baos);
		
		Assert.assertEquals(toEncryptString, new String(baos.toByteArray()));
	}
}
