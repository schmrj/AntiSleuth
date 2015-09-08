package com.antisleuthsecurity.client.crypto;

import javax.ws.rs.core.MediaType;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.requests.AddKeyRequest;
import com.antisleuthsecurity.asc_api.rest.responses.AddKeyResponse;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class KeyManager {
	
	public AddKeyResponse addKey(AddKeyRequest request,
			WebResource resource) throws AscException {
		try {
			ClientResponse response = resource.path("/crypto/keys/addKey")
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, request);

			if (response.getStatus() == 200) {
				return response.getEntity(AddKeyResponse.class);
			} else {
				throw new AscException("Invalid Response: "
						+ response.getStatus() + " "
						+ response.getStatusInfo().getReasonPhrase());
			}
		} catch (Exception e) {
			throw new AscException("Could not complete request", e);
		}
	}
}
