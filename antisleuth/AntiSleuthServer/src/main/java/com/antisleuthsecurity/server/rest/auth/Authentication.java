/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.server.rest.auth;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import com.antisleuthsecurity.asc_api.rest.responses.LogoutResponse;
import com.antisleuthsecurity.asc_api.rest.responses.RegistrationResponse;
import com.antisleuthsecurity.asc_api.rest.responses.SaltResponse;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.server.ASServer;
import com.antisleuthsecurity.server.PropsEnum;
import com.antisleuthsecurity.server.rest.AsRestApi;
import com.antisleuthsecurity.server.rest.validation.LoginValidator;
import com.antisleuthsecurity.server.rest.validation.NewAccountValidator;
import com.antisleuthsecurity.server.rest.validation.SaltValidator;

@Path("/auth")
public class Authentication extends AsRestApi {

	AuthenticationUtil authUtil = new AuthenticationUtil();

	public Authentication() {

	}
	
	public Authentication(HttpServletRequest request) {
		this.servletRequest = request;
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
					+ request.getAccount().getUsername() + ", ["
					+ sqle.getErrorCode() + "] " + sqle.getMessage());
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
		HttpSession session = this.servletRequest.getSession(true);
		LoginResponse response = new LoginResponse();
		try {
			if (request != null) {
				LoginValidator lv = new LoginValidator(request.getAccount());
				Message[] messages = lv.getReasons();

				if (lv.isValid()) {
					UserAccount account = request.getAccount();
					Integer userId = authUtil.findUserId(account.getUsername(),
							ASServer.sql);

					boolean accountLocked = authUtil.isAccountLocked(userId,
							ASServer.sql);

					if (!accountLocked) {
						String query = "SELECT id FROM Users WHERE username=? AND password=?";
						ResultSet rs = ASServer.sql.query(
								query,
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
							authUtil.addLoginAttempt(account, true,
									ASServer.sql);
							session.setAttribute(
									PropsEnum.USER_ACCOUNT.getProperty(),
									account);
						} else {
							response.addMessage(MessagesEnum.LOGIN_FAILED);
							authUtil.addLoginAttempt(userId, false,
									ASServer.sql);
						}
					} else {
						response.addMessage(MessagesEnum.ACCOUNT_LOCKED);
						authUtil.addLoginAttempt(userId, false, ASServer.sql);
					}
				} else {
					response.addMessages(messages);
					response.addMessage(MessagesEnum.LOGIN_FAILED);
				}
			}
		} catch (SQLException sqle) {
			ASLog.debug("Could not login user: "
					+ request.getAccount().getUsername() + ", ["
					+ sqle.getErrorCode() + "] " + sqle.getMessage());
			response.addMessage(MessagesEnum.LOGIN_FAILED);
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

	/**
	 * Use to terminate current session {@link GET}
	 * 
	 * @return {@link LogoutResponse}
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/logout")
	public LogoutResponse getLogout() {
		return logout();
	}

	/**
	 * Use to terminate current logged in session {@link POST}
	 * 
	 * @return {@link LogoutResponse}
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/logout")
	public LogoutResponse logout() {
		LogoutResponse response = new LogoutResponse();

		HttpSession session = this.servletRequest.getSession(false);

		if (session != null) {
			UserAccount account = (UserAccount) session
					.getAttribute(PropsEnum.USER_ACCOUNT.getProperty());
			if (account != null) {
				response.setSuccess(true);
				session.invalidate();
			}
		}
		
		if(!response.isSuccess())
			response.addMessage(MessagesEnum.NOT_AUTHENTICATED);

		return response;
	}
}
