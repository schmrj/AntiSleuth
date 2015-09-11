/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.hashes.hash;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Security;

import org.apache.commons.codec.binary.Hex;

import com.antisleuthsecurity.asc_api.exceptions.AscException;

public abstract class Hasher {
	
	public Hasher(){
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	protected abstract byte[] hash(InputStream is) throws AscException;
	
	public byte[] hash(String content) throws AscException{
		try{
			ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes("UTF-8"));
			return hash(bais);
		}catch(Exception e){
			throw new AscException(e.getMessage(), e);
		}
	}
	
	private byte[] hash(byte[] content) throws AscException{
		return hash(content);
	}
	
	public String getHashAsString(String content) throws AscException{
		return Hex.encodeHexString(hash(content));
	}
	
//	public String getHashAsString(byte[] content) throws AscException{
//		return Hex.encodeHexString(hash(content));
//	}
//	
//	public String getHashAsString(InputStream content) throws AscException{
//		return Hex.encodeHexString(hash(content));
//	}
}
