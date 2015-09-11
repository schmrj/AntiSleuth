/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.Ciphers;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class Cryptographer {
	
	private Ciphers cipher = null;
	private int bufferSize = 1024;
	
	public Cryptographer(Ciphers cipher){
		this.cipher = cipher;
	}

	public Ciphers getCipher() {
		return cipher;
	}

	public OutputStream getOutputStream(OutputStream os) throws AscException {
		return new CipherOutputStream(os, this.cipher.getCipher());
	}

	public void processStream(InputStream is, OutputStream os)
			throws AscException, IOException {
		Cipher cipher = this.cipher.getCipher();

		byte[] array = null;

		if (is.available() >= this.bufferSize) {
			array = new byte[this.bufferSize];
		} else {
			array = new byte[is.available()];
		}

		CipherInputStream input = (CipherInputStream) this.getInputStream(is);

		int read = input.read(array);
		while (read > 0) {
			os.write(array, 0, read);
			read = input.read(array);
		}
	}

	public void setCipherMode(int mode) {
		this.cipher.setCipherMode(mode);
	}

	public InputStream getInputStream(InputStream is) throws AscException {
		return new CipherInputStream(is, this.cipher.getCipher());
	}

	public byte[] process(String toProcess) throws AscException, IOException {
		return process(toProcess.getBytes());
	}

	public byte[] process(byte[] toProcess) throws AscException, IOException {
		Cipher cipher = this.cipher.getCipher();
		int block = cipher.getBlockSize();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayInputStream bais = new ByteArrayInputStream(toProcess);
		InputStream cis = getInputStream(bais);

		byte[] buffer = new byte[block];

		int read = cis.read(buffer);

		while (read > 0) {
			baos.write(buffer, 0, read);
			read = cis.read(buffer);
		}

		return baos.toByteArray();
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public void setCipher(Ciphers cipher) {
		this.cipher = cipher;
	}
	
	public static byte[] generateSalt(int size) {
		final Random r = new SecureRandom();
		byte[] salt = new byte[32];
		r.nextBytes(salt);

		return salt;
	}
}
