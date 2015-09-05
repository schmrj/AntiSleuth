package com.antisleuthsecurity.client.auth;

import javax.ws.rs.core.MediaType;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.requests.RegistrationRequest;
import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Authentication {

	ASLog logger = new ASLog("Client");

	/**
	 * 
	 * @param {@link RegistrationRequest} containing the information of the user
	 *        you wish to register
	 * @param {@link WebResource} for the API Endpoint
	 * @return {@link RegistrationResponse} containing the results of the
	 *         registration attempt
	 * @throws AscException
	 */
	public RegistrationResponse registerUser(RegistrationRequest request,
			WebResource resource) throws AscException {
		try {
			ClientResponse response = resource.path("/auth/register")
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, request);

			if (response.getStatus() == 200) {
				return response.getEntity(RegistrationResponse.class);
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
