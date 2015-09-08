package com.antisleuthsecurity.server.rest.crypto;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.AddKeyRequest;
import com.antisleuthsecurity.asc_api.rest.responses.AddKeyResponse;
import com.antisleuthsecurity.server.PropsEnum;
import com.antisleuthsecurity.server.rest.AsRestApi;

@Path("/crypto/keys")
public class KeyManager extends AsRestApi {

	private static final long serialVersionUID = 8092015L; // 08 Sep 2015

	/**
	 * Method used to consume {@link AddKeyRequest} in order to register a
	 * public key with the server.
	 * 
	 * @param {@link AddKeyRequest request}, this is the request object which
	 *        contains the key to add.
	 * @return {@link AddKeyResponse} returns the status of whether or not the
	 *         key was accepted by the server
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addKey")
	public AddKeyResponse addKey(AddKeyRequest request) {
		AddKeyResponse response = new AddKeyResponse();

		// Try to get the session, but don't create a new one if it doesn't
		// exist!
		HttpSession session = this.servletRequest.getSession(false);

		if (session != null) {
			// TODO Validate the addition of a new key
			UserAccount account = (UserAccount) session
					.getAttribute(PropsEnum.USER_ACCOUNT.getProperty());

			if (account != null) {

			}
		}

		response.addMessage(MessagesEnum.METHOD_NOT_IMPLEMENTED);
		return response;
	}
}
