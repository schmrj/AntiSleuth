package com.antisleuthsecurity.client.auth;

import org.junit.Test;

import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.client.common.WebServiceClient;
import com.sun.jersey.api.client.WebResource;

public class AuthenticationTest {

	@Test
	public void test() throws AscException {
		WebResource resource = new WebServiceClient("http://localhost:8080/AS/api").getClient("http://localhost:8080/AS/api");
		
		Authentication authTest = new Authentication();
		authTest.registerUser(null, resource);
	}

}
