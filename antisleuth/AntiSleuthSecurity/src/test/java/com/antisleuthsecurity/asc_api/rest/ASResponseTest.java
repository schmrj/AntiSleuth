/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.rest;

import org.junit.Test;

import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import static org.junit.Assert.*;

public class ASResponseTest {

	ASLog logger = new ASLog("ASResponseTest");

	@Test
	public void testSetResponseType() {
		UserAccount account = new UserAccount();
		account.setUserId(1);
		account.setUsername("Testing");
		account.setEmailAddress("test@test.com");
		
		RegistrationResponse response = new RegistrationResponse();
		response.setSuccess(true);
		response.setUserAccount(account);
		
		assertTrue(response.isSuccess());
	}
}
