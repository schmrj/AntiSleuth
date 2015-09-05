package com.antisleuthsecurity.server.rest.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.requests.RegistrationRequest;
import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
import com.antisleuthsecurity.server.rest.AsRestApi;

@Path("/auth")
public class Authentication extends AsRestApi{

	public Authentication(){
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/register")
	public RegistrationResponse register(RegistrationRequest requestString) {
		RegistrationResponse response = null;
		try{
			// TODO registration Validation Here
			
			response = new RegistrationResponse();
			response.setSuccess(false);
			response.addMessage(MessagesEnum.SYSTEM_ERROR);
			response.addMessage(MessagesEnum.REGISTRATION_FAILED);
			response.setUserAccount(requestString.getAccount());
			response.setResponseClass(requestString.getAccount().getClass());
			
		}catch(Exception e){
			response = new RegistrationResponse();
			response.setSuccess(false);
			response.addMessage(MessagesEnum.SYSTEM_ERROR);
		}
		
		return response;
	}
}
