/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.client;

import javax.ws.rs.core.MediaType;

import com.antisleuthsecurity.asc_api.rest.requests.ASRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ASClient {
	protected ClientResponse post(ASRequest request, String path,
			WebResource resource) {
		ClientResponse response = resource.path(path)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, request);

		return response;
	}

	protected ClientResponse get(String path,
			WebResource resource) {
		ClientResponse response = resource.path(path)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		return response;
	}
}
