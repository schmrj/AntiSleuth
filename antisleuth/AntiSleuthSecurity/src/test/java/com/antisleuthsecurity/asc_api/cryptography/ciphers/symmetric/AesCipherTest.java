/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric;

import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric.AesCipher;
import com.antisleuthsecurity.asc_api.exceptions.AscException;


public class AesCipherTest extends TestCase {
	
	AesCipher aes = new AesCipher();
	AesCipher.Strength strength;
	AesCipher.CipherInstance cipherInstance;
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetStrength128(){
		int value = aes.getStrength(strength.S128);
		
		Assert.assertEquals(128, value);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetStrength192(){
		int value = aes.getStrength(strength.S192);
		
		Assert.assertEquals(192, value);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetStrength256(){
		int value = aes.getStrength(strength.S256);
		
		Assert.assertEquals(256, value);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetStrengthNone(){
		int value = aes.getStrength(strength.NONE);
		
		Assert.assertEquals(128, value);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetStrengthNull(){
		int value = aes.getStrength(null);
		
		Assert.assertEquals(128, value);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetStrength(){
		int value = aes.getStrength();
		
		Assert.assertEquals(128, value);
	}
	
	@Test
	public void testGenerate128Key() throws AscException, NoSuchAlgorithmException{
		aes.setStrength(strength.S128);
		byte[] key = aes.generateKey();
		
		Assert.assertEquals(16, key.length);
	}
	
	@Test
	public void testGetInstance(){
		String instance = aes.getInstance();
		Assert.assertEquals("AES/CBC/PKCS5Padding", instance);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetInstanceAesCbcPksc7Padding(){
		aes.setInstance(cipherInstance.AESCBCPKSC7Padding);
		String instance = aes.getInstance();
		Assert.assertEquals("AES/CBC/PKCS7Padding", instance);
	}
	
	@Test
	public void testGetInstanceNull(){
		String instance = aes.getInstance(null);
		Assert.assertEquals("AES/CBC/PKCS5Padding", instance);
	}
	
	@Test
	public void testSetKeyStrength128() throws AscException{
		Assert.assertEquals(128, aes.getStrength(strength.S128));
	}
	
	@Test
	public void testSetKeyStrengthNull(){
		Assert.assertEquals(128, aes.getStrength(null));
	}
	
	@Test
	public void testSetInstanceNull(){
		aes.setInstance(null);
		Assert.assertEquals("AES/CBC/PKCS5Padding", aes.getInstance());
	}
	
	@Test
	public void testCreateIv(){
		byte[] generateIv = aes.generateIV();
		Assert.assertEquals(16, generateIv.length);
	}
	 
	@Test
	public void testGenerateKey() throws AscException{
		byte[] key = aes.generateKey();
		Assert.assertEquals(16, key.length);
	}
	
	@Test
	public void testSetIv() throws AscException{
		byte[] iv = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		aes.setIv(iv);
		Assert.assertArrayEquals(iv, aes.getIv());
	}
	
	@Test
	public void testSetKey() throws AscException{
		byte[] key = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		aes.setKey(key);
		Assert.assertArrayEquals(key, aes.getKey());
	}
	
	@Test
	public void testSetKeySub16() throws AscException{
		byte[] key = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		aes.setKey(key);
		Assert.assertEquals(16, aes.getKey().length);
	}
	
	@Test
	public void testSetIvSub16() throws AscException{
		byte[] iv = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		aes.setIv(iv);
		Assert.assertEquals(16, aes.getIv().length);
	}
	
	@Test
	public void testSetIvNull() throws AscException{
		aes.setIv(null);
		Assert.assertEquals(16, aes.getIv().length);
	}
	
	@Test
	public void testSetKeyNull() throws AscException{
		aes.setKey(null);
		Assert.assertEquals(16, aes.getKey().length);
	}
	
	@Test
	public void testGetCipherEncrypt() throws AscException{
		Cipher cipher = aes.getCipher(Cipher.ENCRYPT_MODE);
		Assert.assertNotNull(cipher);
	}
	
	@Test
	public void testGetCipherDecrypt() throws AscException{
		Cipher cipher = aes.getCipher(Cipher.ENCRYPT_MODE);
		Assert.assertNotNull(cipher);
	}
	
	@Test
	public void testCipher() throws AscException, IllegalBlockSizeException, BadPaddingException{
		String toEncrypt = "This is a test String";
		Cipher cipher = aes.getCipher(Cipher.ENCRYPT_MODE);
		
		byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());
		
		if(new String(encrypted).equals(toEncrypt))
			Assert.fail("These strings should not match!");
		
		byte[] decrypted = aes.getCipher(Cipher.DECRYPT_MODE).doFinal(encrypted);
		
		Assert.assertEquals(toEncrypt, new String(decrypted));
	}
}
