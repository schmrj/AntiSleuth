package com.antisleuthsecurity.client.messaging;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.requests.SendMessageRequest;
import com.antisleuthsecurity.asc_api.rest.responses.GetMessageResponse;
import com.antisleuthsecurity.asc_api.rest.responses.SendMessageResponse;
import com.antisleuthsecurity.client.ASClient;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Messaging extends ASClient {

	/**
	 * Send a message to a single user or a group of users. To identify mulriple
	 * recipients, include multiple encrypted keys in the {@link
	 * MessageParts#addKey(String username, byte[])} Where the username is the
	 * recipient
	 * 
	 * @param {@link SendMessageRequest request} Request containing the
	 *        information for the message to send
	 * @param {@link WebResource resource}
	 * @return {@link SendMessageRequest}
	 * @throws AscException
	 */
	public SendMessageResponse sendMessage(SendMessageRequest request,
			WebResource resource) throws AscException {
		try {
			ClientResponse response = this.post(request, "/messaging/send",
					resource);

			if (response.getStatus() == 200) {
				return response.getEntity(SendMessageResponse.class);
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
	 * Retreive all messages sent to the current user
	 * 
	 * @param {@link WebResource resource}
	 * @return {@link GetMessageResponse}
	 * @throws AscException
	 */
	public GetMessageResponse getMessages(WebResource resource)
			throws AscException {
		try {
			ClientResponse response = this.get("/messaging/receive", resource);

			if (response.getStatus() == 200) {
				return response.getEntity(GetMessageResponse.class);
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
