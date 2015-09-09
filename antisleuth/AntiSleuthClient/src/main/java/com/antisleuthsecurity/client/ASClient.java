package com.antisleuthsecurity.client;

import javax.ws.rs.core.MediaType;

import com.antisleuthsecurity.asc_api.rest.requests.ASRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ASClient {
	protected ClientResponse post(ASRequest request, String path, WebResource resource){
		ClientResponse response = resource.path(path)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, request);
		
		return response;
	}
}