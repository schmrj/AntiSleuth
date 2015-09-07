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
import com.antisleuthsecurity.asc_api.cryptography.Cryptographer;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.LoginRequest;
import com.antisleuthsecurity.asc_api.rest.requests.RegistrationRequest;
import com.antisleuthsecurity.asc_api.rest.requests.SaltRequest;
import com.antisleuthsecurity.asc_api.rest.responses.LoginResponse;
import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
import com.antisleuthsecurity.asc_api.rest.responses.SaltResponse;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.server.ASServer;
import com.antisleuthsecurity.server.rest.AsRestApi;
import com.antisleuthsecurity.server.rest.validation.LoginValidator;
import com.antisleuthsecurity.server.rest.validation.NewAccountValidator;
import com.antisleuthsecurity.server.rest.validation.SaltValidator;

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
									.getPassword()).getBytes()), "UTF-8"),
							new String(Base64.encode(account.getSalt()),
									"UTF-8") };

					// TODO REgister Account
					String query = "INSERT INTO Users (username, password, salt) VALUES (?, ?, ?)";
					ASServer.sql.execute(query, accountParams);

					accountParams = new String[] {
							account.getUsername(),
							new String(Base64.encode(new String(account
									.getPassword()).getBytes()), "UTF-8") };
					query = "SELECT id FROM Users WHERE username=? AND password=?";
					ResultSet rs = ASServer.sql.query(query, accountParams);

					while (rs.next()) {
						account.setUserId(rs.getInt("id"));
						response.setUserAccount(account);
						break;
					}

					rs.close();

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
					+ request.getAccount().getUsername() + ", [" + sqle.getErrorCode() + "] " + sqle.getMessage());
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
					ResultSet rs = ASServer.sql
							.query(query,
									new String[] {
											account.getUsername(),
											new String(Base64.encode(account
													.getPassword().getBytes()),
													"UTF-8") });

					while (rs.next()) {
						account.setUserId(rs.getInt("id"));
						response.setAccount(account);
						response.setSuccess(true);
					}

					rs.close();

					if (account.getUserId() != null) {
						response.setSuccess(true);
					} else {
						response.addMessage(MessagesEnum.LOGIN_FAILED);
					}
				} else {
					response.addMessages(messages);
					response.addMessage(MessagesEnum.LOGIN_FAILED);
				}
			}
		} catch (Exception e) {
			response.addMessage(MessagesEnum.SYSTEM_ERROR);
		}

		return response;
	}

	/**
	 * Consume a {@link SaltRequest} in order to attempt to retreive the user's
	 * Salt from the database for authentication.
	 * 
	 * @param {@link SaltRequest} to consume
	 * @return {@link SaltResponse} containing information pertaining to the
	 *         {@link SaltRequest} attempt
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/salt")
	public SaltResponse postGetSalt(SaltRequest request) {
		UserAccount saltAccount = new UserAccount();
		SaltResponse response = new SaltResponse();
		try {
			if (request != null) {
				// TODO GetSalt Here
				UserAccount account = request.getAccount();
				SaltValidator sv = new SaltValidator(account);
				Message[] messages = sv.getReasons();

				if (sv.isValid()) {
					String query = "SELECT salt FROM Users WHERE username=?";
					ResultSet rs = ASServer.sql.query(query,
							new String[] { account.getUsername() });

					while (rs.next()) {
						saltAccount
								.setSalt(Base64.decode(rs.getString("salt")));
						response.setAccount(saltAccount);
						response.setSuccess(true);
					}
					rs.close();

					/*
					 * If the useraccount was not found, generate a random salt.
					 * This is to prevent this call from being used to identify
					 * valid user accounts.
					 */
					if (!response.isSuccess()) {
						saltAccount.setSalt(Cryptographer.generateSalt(32));
						response.setAccount(saltAccount);
						response.setSuccess(true);
					}

				} else {
					response.addMessages(messages);
				}
			}
		} catch (Exception e) {
			response.addMessage(MessagesEnum.SYSTEM_ERROR);
		}

		return response;
	}
}
