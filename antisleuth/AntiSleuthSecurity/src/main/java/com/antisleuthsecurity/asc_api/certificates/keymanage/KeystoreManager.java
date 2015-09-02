package com.antisleuthsecurity.asc_api.certificates.keymanage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class KeystoreManager {
	private boolean loaded = false;

	private KeyStore keystore = null;
	private KeyUtils util = null;

	private String storeLocation = null;
	private String instance = null;

	private X509Certificate signingCertificate;

	public KeystoreManager(String storeLocation) {
		this.instance = "JKS";
		this.storeLocation = storeLocation;
	}

	public KeystoreManager(String instance, String storeLocation) {
		this.instance = instance;
		this.storeLocation = storeLocation;
	}

	public String getStoreLocation() {
		return storeLocation;
	}

	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}

	public void init(char[] password) throws KeyStoreException, AscException {
		this.init(this.storeLocation, password);
	}

	public void init(String filename, char[] password)
			throws KeyStoreException, AscException {
		this.keystore = KeyStore.getInstance(instance);
		try {
			File store = new File(filename);
			if (store.exists() && store.isFile()) {
				this.keystore.load(new FileInputStream(new File(filename)),
						password);
				this.loaded = true;
			} else {
				if (store.getParent() != null) {
					File parent = new File(store.getParent());
					parent.mkdirs();
				}

				this.keystore.load(null, password);
				this.keystore.store(new FileOutputStream(new File(filename)),
						password);
				init(filename, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AscException("Could not load keystore!", e);
		}
	}

	public String[] getAlias() throws AscException {
		try {
			this.util = new KeyUtils(this.keystore);
			return this.util.getAlias();
		} catch (KeyStoreException kse) {
			throw new AscException("Could not get Aliases", kse);
		}
	}

	public KeyStore getKeyStore() {
		return this.keystore;
	}

	public boolean isLoaded() {
		return this.loaded;
	}

	public X509Certificate getCertificate(InputStream is) throws AscException {
		return this.util.getX509Certificate(is);
	}

	public void putKey(String alias, char[] password, KeyPair pair,
			X509Certificate certificate) throws AscException{
		this.insertKey(alias, password, pair, certificate);
	}
	
	public void insertKey(String alias, char[] password, KeyPair pair,
			X509Certificate certificate) throws AscException {
		try {
			this.authenticate(password);

			X509Certificate[] chain = new X509Certificate[1];
			chain[0] = certificate;
			this.keystore
					.setKeyEntry(alias, pair.getPrivate(), password, chain);
			this.keystore.store(new FileOutputStream(new File(
					this.storeLocation)), password);
		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException e) {
			throw new AscException(e.getMessage(), e);
		}
	}

	public void deleteKey(String alias, char[] password) throws AscException {
		try {
			this.authenticate(password);

			this.keystore.deleteEntry(alias);
			this.keystore.store(new FileOutputStream(new File(
					this.storeLocation)), password);
		} catch (Exception e) {
			throw new AscException(e.getMessage(), e);
		}
	}

	public PublicKey getPublicKey(String alias) throws AscException {
		try {
			return this.keystore.getCertificate(alias).getPublicKey();
		} catch (Exception e) {
			throw new AscException(e.getMessage(), e);
		}
	}

	public Key getPrivateKey(String alias, char[] password) throws AscException {
		try {
			return (Key) this.keystore.getKey(alias, password);
		} catch (Exception e) {
			throw new AscException(e.getMessage(), e);
		}
	}

	private void authenticate(char[] password) throws KeyStoreException,
			AscException {
		// Validate Password, throws an exception on invalid PW.
		this.init(this.storeLocation, password);
	}

	public X509Certificate getSigningCertificate() {
		return signingCertificate;
	}

	public void setSigningCertificate(X509Certificate signingCertificate) {
		this.signingCertificate = signingCertificate;
	}

}
