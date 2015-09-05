package com.antisleuthsecurity.client.auth;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.RegistrationRequest;
import com.antisleuthsecurity.client.common.WebServiceClient;
import com.sun.jersey.api.client.WebResource;

public class AuthenticationTest {

	public static final String connectionUrl = "http://localhost:8080/AS/api";
	
	public static void main(String [] args) throws AscException{
		WebResource resource = new WebServiceClient(connectionUrl).getClient(connectionUrl, false);
		
		UserAccount account = new UserAccount();
		account.setUsername("testUsername");
		account.setPassword("Test Password".toCharArray());
		account.setEmailAddress("Test@Email.Address");
		
		RegistrationRequest regRequest = new RegistrationRequest();
		regRequest.setAccount(account);
		
		System.out.println("Attempting to send Registration Request");
		Authentication auth = new Authentication();
		auth.registerUser(regRequest, resource);
	}
}
