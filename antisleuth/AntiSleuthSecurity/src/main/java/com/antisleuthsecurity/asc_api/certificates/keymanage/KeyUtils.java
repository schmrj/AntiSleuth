/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.certificates.keymanage;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.utilities.AntiSleuthProps;


public class KeyUtils {
	KeyStore keyStore = null;
	
	public KeyUtils(KeyStore keyStore){
		this.keyStore = keyStore;
	}
	
	public String[] getAlias() throws KeyStoreException{
		Enumeration<String> alias = this.keyStore.aliases();
		List<String> aliasList = new ArrayList<String>();
		
		while(alias.hasMoreElements()){
			aliasList.add(alias.nextElement());
		}
		
		return (String[]) aliasList.toArray(new String[aliasList.size()]);
	}
	
	public void getPrivateCertificate(X509Certificate certificate, KeyPair pair) throws CertificateException{
		PublicKey publicKey = pair.getPublic();
		PrivateKey privateKey = pair.getPrivate();
		
	}
	
	public X509Certificate getX509Certificate(InputStream is) throws AscException{
		try{
			CertificateFactory factory = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate) factory.generateCertificate(is);
			return certificate;
		}catch(Exception e){
			throw new AscException(e.getMessage(), e);
		}
	}
	
	public X509Certificate getAntiSleuthCertificate() throws AscException, IOException{
		AntiSleuthProps props = new AntiSleuthProps();
		
		InputStream is = props.getResourceStream(props.getSigningKeyLocation()); 
		X509Certificate certificate = getX509Certificate(is);
		
		return certificate;
	}
}
