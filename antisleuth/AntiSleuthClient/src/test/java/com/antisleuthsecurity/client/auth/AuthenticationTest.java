package com.antisleuthsecurity.client.auth;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.LoginRequest;
import com.antisleuthsecurity.asc_api.rest.requests.RegistrationRequest;
import com.antisleuthsecurity.asc_api.rest.responses.LoginResponse;
import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
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
		RegistrationResponse regResponse = auth.registerUser(regRequest, resource);
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setAccount(account);
		LoginResponse response = auth.login(loginRequest, resource);
		
		System.out.println("Finished");
	}
}
