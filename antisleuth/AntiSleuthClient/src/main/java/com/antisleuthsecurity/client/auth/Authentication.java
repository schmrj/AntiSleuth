package com.antisleuthsecurity.client.auth;

import javax.ws.rs.core.MediaType;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.RegistrationRequest;
import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.asc_api.utilities.GeneralUtils;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Authentication {

	ASLog logger = new ASLog("Client");

	public UserAccount registerUser(RegistrationRequest request,
			WebResource resource) throws AscException {

		try {
//			String jsonString = new GeneralUtils().createJsonString(request);

			ClientResponse response = resource.path("/auth/register")
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, request);

			if (response.getStatus() == 200) {
				RegistrationResponse regResponse = response.getEntity(RegistrationResponse.class);
				UserAccount account = regResponse.getResponse();
				System.out.println("Received: " + regResponse.getResponseClass());
			} else {
				throw new AscException("Invalid Response: "
						+ response.getStatus() + " "
						+ response.getStatusInfo().getReasonPhrase());
			}
		} catch (Exception e) {
			throw new AscException("Could not complete request", e);
		}

		return null;
	}
}
