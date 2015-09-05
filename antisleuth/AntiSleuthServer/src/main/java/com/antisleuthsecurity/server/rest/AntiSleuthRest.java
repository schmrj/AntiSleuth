package com.antisleuthsecurity.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.antisleuthsecurity.ServerConstants;

@Path("/")
public class AntiSleuthRest {
	
	@GET
	@Path("/version")
	public Response getVersion(){
		return Response.ok("Version: " + ServerConstants.version).build();
	}
	
	@GET
	@Path("/unauthorizedTest")
	public Response testUnauthorized(){
		return Response.ok("Version: " + ServerConstants.version).build();
	}
}
