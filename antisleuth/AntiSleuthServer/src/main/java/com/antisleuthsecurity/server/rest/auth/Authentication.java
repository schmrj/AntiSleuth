package com.antisleuthsecurity.server.rest.auth;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bouncycastle.util.encoders.Base64;

import com.antisleuthsecurity.asc_api.common.error.Message;
import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.LoginRequest;
import com.antisleuthsecurity.asc_api.rest.requests.RegistrationRequest;
import com.antisleuthsecurity.asc_api.rest.responses.LoginResponse;
import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.server.ASServer;
import com.antisleuthsecurity.server.rest.AsRestApi;
import com.antisleuthsecurity.server.rest.validation.LoginValidator;
import com.antisleuthsecurity.server.rest.validation.NewAccountValidator;

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
	public RegistrationResponse register(RegistrationRequest request) {
		RegistrationResponse response = new RegistrationResponse();
		try {
			if (request != null) {
				NewAccountValidator av = new NewAccountValidator(
						request.getAccount());
				Message[] messages = av.getReasons();

				if (av.isValid()) {
					UserAccount account = request.getAccount();
					String[] accountParams = new String[] {
							account.getUsername(),
							new String(Base64.encode(new String(account
									.getPassword()).getBytes())),
							new String(Base64.encode(account.getSalt()
									.getBytes())) };

					// TODO REgister Account
					String query = "INSERT INTO Users (username, password, salt) VALUES (?, ?, ?)";
					ASServer.sql.execute(query, accountParams);

					accountParams = new String[] {
							account.getUsername(),
							new String(Base64.encode(new String(account
									.getPassword()).getBytes())) };
					query = "SELECT id FROM Users WHERE username=? AND password=?";
					ResultSet rs = ASServer.sql.query(query, accountParams);

					while (rs.next()) {
						account.setUserId(rs.getInt("id"));
						response.setUserAccount(account);
						break;
					}

					if (account.getUserId() != null) {
						response.setSuccess(true);
						ASLog.debug("Account registered: "
								+ account.getUsername());
					} else {
						response.addMessage(MessagesEnum.REGISTRATION_FAILED);
					}
				} else {
					// Return error response!
					response.addMessages(messages);
				}
			}
		} catch (SQLException sqle) {
			ASLog.debug("Could not register user: "
					+ request.getAccount().getUsername(), sqle);
			response.addMessage(MessagesEnum.REGISTRATION_FAILED);
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
	public LoginResponse login(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		try {
			if (request != null) {
				LoginValidator lv = new LoginValidator(request.getAccount());
				Message[] messages = lv.getReasons();

				if (lv.isValid()) {
					UserAccount account = request.getAccount();

					String query = "SELECT id FROM Users WHERE username=? AND password=?";
					ResultSet rs = ASServer.sql.query(
							query,
							new String[] {
									account.getUsername(),
									new String(Base64.encode(new String(account
											.getPassword()).getBytes())) });

					while (rs.next()) {
						account.setUserId(rs.getInt("id"));
						response.setAccount(account);
						response.setSuccess(true);
					}

					if (account.getUserId() != null) {
						response.setSuccess(true);
					} else {
						response.addMessage(MessagesEnum.LOGIN_FAILED);
					}
				} else {
					response.addMessages(messages);
					response.addMessage(MessagesEnum.LOGIN_FAILED);
				}

				// TODO registration Validation Here

			}
		} catch (Exception e) {
			response.addMessage(MessagesEnum.SYSTEM_ERROR);
		}

		return response;
	}
}
