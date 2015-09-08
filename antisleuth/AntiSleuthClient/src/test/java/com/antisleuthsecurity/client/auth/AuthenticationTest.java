package com.antisleuthsecurity.client.auth;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;

import com.antisleuthsecurity.asc_api.cryptography.Cryptographer;
import com.antisleuthsecurity.asc_api.cryptography.hashes.hash.SHA256;
import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.LoginRequest;
import com.antisleuthsecurity.asc_api.rest.requests.RegistrationRequest;
import com.antisleuthsecurity.asc_api.rest.requests.SaltRequest;
import com.antisleuthsecurity.asc_api.rest.responses.LoginResponse;
import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
import com.antisleuthsecurity.asc_api.rest.responses.SaltResponse;
import com.antisleuthsecurity.client.common.WebServiceClient;
import com.sun.jersey.api.client.WebResource;

public class AuthenticationTest {

	public static final String connectionUrl = "http://localhost:8080/AS/api";

	public static void main(String[] args) throws AscException, UnsupportedEncodingException {

		String password = "TEST PASSWORD";

		WebResource resource = new WebServiceClient(connectionUrl).getClient(
				connectionUrl, false);
		Authentication auth = new Authentication();

		UserAccount account = new UserAccount();
		account.setUsername("testUsername");
		byte[] salt = Cryptographer.generateSalt(32);
		account.setSalt(salt);

		String hashedPassword = Authentication.saltPassword(password, salt);

		account.setPassword(hashedPassword);
		account.setEmailAddress("Test@Email.Address");

		RegistrationRequest regRequest = new RegistrationRequest();
		regRequest.setAccount(account);

		System.out.println("Attempting to send Registration Request");
		RegistrationResponse regResponse = auth.registerUser(regRequest,
				resource);

		SaltRequest saltRequest = new SaltRequest();
		saltRequest.setAccount(account);
		SaltResponse saltResponse = auth.getSalt(saltRequest, resource);

//		password = "T";
		if (saltResponse.isSuccess()) {
			UserAccount saltAccount = saltResponse.getAccount();
			hashedPassword = Authentication.saltPassword(password,
					saltAccount.getSalt());
		}
		account.setPassword(hashedPassword);

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setAccount(account);
		LoginResponse loginResponse = auth.login(loginRequest, resource);
		System.out.println("Login Response: " + loginResponse.isSuccess());

		System.out.println("Finished");
	}
}
