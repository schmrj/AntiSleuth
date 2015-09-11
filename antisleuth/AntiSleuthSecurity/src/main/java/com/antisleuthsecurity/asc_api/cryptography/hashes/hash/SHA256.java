/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.hashes.hash;

import java.io.InputStream;
import java.security.MessageDigest;

import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class SHA256 extends Hasher{
	
	@Override
	public byte[] hash(InputStream is) throws AscException {
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256", "BC");
			
			byte[] buffer = new byte[1024];
			int read = is.read(buffer);
			while(read > 0){
				md.update(buffer, 0, read);
				read = is.read(buffer);
			}
			
			return md.digest();
		}catch(Exception e){
			throw new AscException(e.getMessage(), e);
		}
	}
}
