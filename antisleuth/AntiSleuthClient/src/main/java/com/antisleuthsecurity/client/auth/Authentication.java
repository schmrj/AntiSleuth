package com.antisleuthsecurity.client.auth;

import javax.ws.rs.core.MediaType;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.LoginRequest;
import com.antisleuthsecurity.asc_api.rest.requests.RegistrationRequest;
import com.antisleuthsecurity.asc_api.rest.requests.SaltRequest;
import com.antisleuthsecurity.asc_api.rest.responses.LoginResponse;
import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
import com.antisleuthsecurity.asc_api.rest.responses.SaltResponse;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Authentication {

	ASLog logger = new ASLog("Client");

	/**
	 * Use to register with the AntiSleuth API
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

	/**
	 * Use to login to the AntiSleuth API with a given {@link UserAccount}
	 * 
	 * @param {@link LoginRequest} containing the information of the user you
	 *        wish to authenticate
	 * @param {@link WebResource} for the API Endpoint
	 * @return {@link RegistrationResponse} containing the results of the
	 *         authentication attempt
	 * @throws AscException
	 */
	public LoginResponse login(LoginRequest request, WebResource resource)
			throws AscException {
		try {
			ClientResponse response = resource.path("/auth/login")
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, request);

			if (response.getStatus() == 200) {
				return response.getEntity(LoginResponse.class);
			} else {
				throw new AscException("Invalid Response: "
						+ response.getStatus() + " "
						+ response.getStatusInfo().getReasonPhrase());
			}
		} catch (Exception e) {
			throw new AscException("Could not complete request", e);
		}
	}
	
	/**
	 * Use to retreive a SALT for the given {@link UserAccount}
	 * 
	 * @param {@link SaltRequest} containing the Salt information of the user you
	 *        wish to authenticate
	 * @param {@link WebResource} for the API Endpoint
	 * @return {@link SaltResponse} containing the results of the
	 *         {@link SaltResponse} attempt
	 * @throws AscException
	 */
	public SaltResponse getSalt(SaltRequest request, WebResource resource)
			throws AscException {
		try {
			ClientResponse response = resource.path("/auth/salt")
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, request);

			if (response.getStatus() == 200) {
				return response.getEntity(SaltResponse.class);
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
