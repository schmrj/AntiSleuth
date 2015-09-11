/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.hashes.hash;

import org.junit.Assert;
import org.junit.Test;

import com.antisleuthsecurity.asc_api.cryptography.hashes.hash.SHA256;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class SHA256Test {
	SHA256 sha = new SHA256();
	String toHash = "MySuperSecretPassword";
	String expected = "64c73861b40aea219763ef930607a3295c16fd3a99b4f1f0ec537c09ec732afb";
	
	@Test
	public void testHash() throws AscException {
		String toHash = "MySuperSecretPassword";		
		String str = sha.getHashAsString(toHash);
		Assert.assertEquals(expected, new String(str));
	}
}
