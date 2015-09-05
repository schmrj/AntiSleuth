package com.antisleuthsecurity.client.auth;

import javax.ws.rs.core.MediaType;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.RegistrationRequest;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Authentication {
	
	ASLog logger = new ASLog("Client");
	
	public UserAccount registerUser(RegistrationRequest request, WebResource resource) throws AscException{
		
		ClientResponse response = resource.path("/auth/register")
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class);
		
		if(response.getStatus() == 200){
			String responseString = response.getEntity(String.class);
			logger.trace("Received: " + responseString);
		}else{
			throw new AscException("Invalid Response: " + response.getStatus() + " " + response.getStatusInfo().getReasonPhrase());
		}
		
		return null;
	}
}
