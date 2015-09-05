package com.antisleuthsecurity.server.rest.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.annotate.JsonValue;

import com.antisleuthsecurity.server.rest.AsRestApi;

@Path("/auth")
public class Authentication extends AsRestApi{

	public Authentication(){
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/register")
	public String register(){
		System.out.println("This is a test");
		return "";
	}
}
