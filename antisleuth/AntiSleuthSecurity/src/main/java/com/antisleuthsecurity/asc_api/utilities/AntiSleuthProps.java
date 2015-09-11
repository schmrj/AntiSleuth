/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.antisleuthsecurity.asc_api.Constants;

public class AntiSleuthProps {
	private Properties props = new Properties();
	
	public AntiSleuthProps() throws IOException{
		props.load(AntiSleuthProps.class.getClassLoader().getResourceAsStream(Constants.Files.PROPS));
	}
	
	public AntiSleuthProps(String file) throws IOException{
		props.load(AntiSleuthProps.class.getClassLoader().getResourceAsStream(file));
	}
	
	public String getKeyStoreLocation(){
		return props.getProperty(Constants.System.keyStore);
	}
	
	public String getProperty(String propert){
		return props.getProperty(propert);
	}
	
	public InputStream getResourceStream(String location){
		return AntiSleuthProps.class.getClassLoader().getResourceAsStream(location);
	}
	
	public String getSigningKeyLocation(){
		return props.getProperty(Constants.System.signingKey);
	}
	
	public String getConfigLocation(){
		return props.getProperty(Constants.System.configLocation);
	}
}
