package com.antisleuthsecurity.server.rest.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.requests.LoginRequest;
import com.antisleuthsecurity.asc_api.rest.requests.RegistrationRequest;
import com.antisleuthsecurity.asc_api.rest.responses.LoginResponse;
import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
import com.antisleuthsecurity.server.rest.AsRestApi;

@Path("/auth")
public class Authentication extends AsRestApi {

	public Authentication() {

	}

	/**
	 * Consume a {@link RegistrationRequest} in order to register a new user
	 * 
	 * @param {@link RegistrationRequest} to consume
	 * @return {@link RegistrationResponse} containing results pertaining to the
	 *         regsitration attempt
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/register")
	public RegistrationResponse register(RegistrationRequest registrationRequest) {
		RegistrationResponse response = new RegistrationResponse();
		try {
			// TODO registration Validation Here
			response.addMessage(MessagesEnum.METHOD_NOT_IMPLEMENTED);
		} catch (Exception e) {
			response.addMessage(MessagesEnum.SYSTEM_ERROR);
		}

		return response;
	}

	/**
	 * Consume a {@link LoginRequest} in order to attempt to authenticate a
	 * user.
	 * 
	 * @param {@link LoginRequest} to consume
	 * @return {@link LoginResponse} containing information pertaining to the
	 *         login attempt
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public LoginResponse login(LoginRequest loginRequest) {
		LoginResponse response = new LoginResponse();
		try {
			// TODO registration Validation Here
			response.addMessage(MessagesEnum.METHOD_NOT_IMPLEMENTED);
		} catch (Exception e) {
			response.addMessage(MessagesEnum.SYSTEM_ERROR);
		}

		return response;
	}
}
