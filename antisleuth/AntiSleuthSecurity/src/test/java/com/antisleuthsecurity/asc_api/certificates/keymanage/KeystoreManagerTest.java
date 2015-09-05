package com.antisleuthsecurity.asc_api.certificates.keymanage;

import java.io.File;
import java.security.KeyStoreException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.antisleuthsecurity.asc_api.certificates.keymanage.KeystoreManager;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class KeystoreManagerTest {

	String fakeKeystore = "fakeKeystore.jks";
	char[] password = "password".toCharArray();
	
	@BeforeClass
	public static void cleanEnvironment(){
		File file = new File("fakeKeystore.jks");
		try{
			if(file.exists())
				file.delete();
		}catch(Exception e){	}
	}
	

	@AfterClass
	public static void cleanup(){
		cleanEnvironment();
	}
	
	@Test
	public void testInitKeystoreManager() throws KeyStoreException, AscException {
		KeystoreManager manager = new KeystoreManager(this.fakeKeystore);
		System.out.println(new File(fakeKeystore).getAbsolutePath());
		manager.init(password);
		
		Assert.assertTrue(new File(fakeKeystore).exists());
	}
	
	@Test
	public void testInitKeystoreManagerWithParent() throws KeyStoreException, AscException {
		String key = "test/" + this.fakeKeystore;
		new File(key).deleteOnExit();
		
		KeystoreManager manager = new KeystoreManager(key);
		manager.init(password);
		
		Assert.assertTrue(new File(key).exists());
	}
}
