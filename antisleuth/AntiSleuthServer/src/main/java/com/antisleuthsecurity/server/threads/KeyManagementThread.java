package com.antisleuthsecurity.server.threads;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.TreeMap;

import com.antisleuthsecurity.ServerConstants.KeyMgmtConstants;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher;
import com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric.RsaCipher.CipherInstance;
import com.antisleuthsecurity.asc_api.utilities.ASLog;

public class KeyManagementThread extends Thread {

	private RsaCipher rsa = new RsaCipher();
	private int numActiveKeys = 1;
	private int activeKey = 0;
	TreeMap<Integer, KeyPair> activeKeys = new TreeMap<Integer, KeyPair>();
		
	public KeyManagementThread() {
		rsa.setInstance(CipherInstance.RSANONEOAEPWithSHA256AndMGF1Padding);
		rsa.setStrength(Integer.parseInt(KeyMgmtConstants.KEY_STRENGTH));
		this.numActiveKeys = Integer.parseInt(KeyMgmtConstants.EXPIRED_KEY_BUFFER);
	}
		
	public PublicKey getActivePublicKey() {
		return this.activeKeys.get(this.activeKey).getPublic();
	}
	
	public PrivateKey getActivePrivateKey(){
		return this.activeKeys.get(this.activeKey).getPrivate();
	}
	
	public int getActiveKeyIndex(){
		return this.activeKey;
	}

	public TreeMap<Integer, KeyPair> getAllKeyPairs(){
		return this.activeKeys;
	}
	
	private KeyPair generateKeys(){
		try{
			KeyPair pair = this.rsa.generateKeyPair();
			activeKey++;
			
			if(activeKey >= numActiveKeys){
				activeKey = 0;
			}
			
			this.activeKeys.put(this.activeKey, pair);
			return pair;
		}catch(Exception e){
			ASLog.fatal("Could not generate required Authentication Keys", e);
			System.exit(1);
		}
		return null;
	}
	
	public void run(){
		while(true){
			KeyPair pair = this.generateKeys();
			ASLog.trace("Secure key generated");
			synchronized (this) {
				this.notifyAll();
			}
			
			try{
				Thread.sleep(Integer.parseInt(KeyMgmtConstants.KEY_LIFETIME_MINUTES) * 1000 * 60);
			}catch(Exception e){
				ASLog.debug("Could not sleep between key generation", e);
			}
		}
	}
}
