/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.client.crypto;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.requests.AddKeyRequest;
import com.antisleuthsecurity.asc_api.rest.requests.DeleteKeyRequest;
import com.antisleuthsecurity.asc_api.rest.requests.GetKeyRequest;
import com.antisleuthsecurity.asc_api.rest.responses.AddKeyResponse;
import com.antisleuthsecurity.asc_api.rest.responses.DeleteKeyResponse;
import com.antisleuthsecurity.asc_api.rest.responses.GetKeyResponse;
import com.antisleuthsecurity.client.ASClient;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class KeyManager extends ASClient {

	/**
	 * Used to upload a key to the server
	 * 
	 * @param {@link AddKeyRequest request}
	 * @param resource
	 * @return
	 * @throws AscException
	 */
	public AddKeyResponse addKey(AddKeyRequest request, WebResource resource)
			throws AscException {
		try {
			ClientResponse response = this.post(request, "/crypto/addKey",
					resource);

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

	/**
	 * User to retrieve a specific key by user id and alias
	 * 
	 * @param {@link GetKeyRequest request}
	 * @param resource
	 * @return
	 * @throws AscException
	 */
	public GetKeyResponse getKey(GetKeyRequest request, WebResource resource)
			throws AscException {
		try {
			ClientResponse response = this.post(request, "/crypto/getKey",
					resource);

			if (response.getStatus() == 200) {
				return response.getEntity(GetKeyResponse.class);
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
	 * User to retrieve all keys by user id
	 * 
	 * @param {@link GetKeyRequest request}
	 * @param resource
	 * @return
	 * @throws AscException
	 */
	public GetKeyResponse getAllUserKey(GetKeyRequest request,
			WebResource resource) throws AscException {
		try {
			ClientResponse response = this.post(request,
					"/crypto/getAllKeys", resource);

			if (response.getStatus() == 200) {
				return response.getEntity(GetKeyResponse.class);
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
     * Delete a single key from the database, re-authentication required
     * 
     * @param {@link DeleteKeyRequest request}
     * @param resource
     * @return
     * @throws AscException
     */
    public DeleteKeyResponse deleteKey(DeleteKeyRequest request,
            WebResource resource) throws AscException {
        try {
            ClientResponse response = this.post(request,
                    "/crypto/deleteKey", resource);

            if (response.getStatus() == 200) {
                return response.getEntity(DeleteKeyResponse.class);
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
     * Delete a single key from the database, re-authentication required
     * 
     * @param {@link DeleteKeyRequest request}
     * @param resource
     * @return
     * @throws AscException
     */
    public DeleteKeyResponse panicDeleteKeys(DeleteKeyRequest request,
            WebResource resource) throws AscException {
        try {
            ClientResponse response = this.post(request,
                    "/crypto/panicDeleteKeys", resource);

            if (response.getStatus() == 200) {
                return response.getEntity(DeleteKeyResponse.class);
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
