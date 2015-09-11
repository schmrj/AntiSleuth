package com.antisleuthsecurity.client.messaging;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.requests.SendMessageRequest;
import com.antisleuthsecurity.asc_api.rest.responses.SendMessageResponse;
import com.antisleuthsecurity.client.ASClient;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Messaging extends ASClient {

	public SendMessageResponse sendMessage(SendMessageRequest request,
			WebResource resource) throws AscException {
		try {
			ClientResponse response = this.post(request,
					"/messaging/send", resource);

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
}
