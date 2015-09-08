package com.antisleuthsecurity.server.rest.crypto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bouncycastle.util.encoders.Base64;

import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.crypto.ASKey;
import com.antisleuthsecurity.asc_api.rest.requests.AddKeyRequest;
import com.antisleuthsecurity.asc_api.rest.requests.GetKeyRequest;
import com.antisleuthsecurity.asc_api.rest.responses.AddKeyResponse;
import com.antisleuthsecurity.asc_api.rest.responses.GetKeyResponse;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.server.ASServer;
import com.antisleuthsecurity.server.PropsEnum;
import com.antisleuthsecurity.server.rest.AsRestApi;
import com.antisleuthsecurity.server.rest.validation.AddKeyValidator;
import com.antisleuthsecurity.server.rest.validation.KeyRequestValidator;

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
		AddKeyValidator akv = new AddKeyValidator(request);
		KeyManagerUtil kmu = new KeyManagerUtil();
		AddKeyResponse response = new AddKeyResponse();

		// Try to get the session, but don't create a new one if it doesn't
		// exist!
		HttpSession session = this.servletRequest.getSession(false);

		if (session != null) {
			// TODO Validate the addition of a new key
			UserAccount account = (UserAccount) session
					.getAttribute(PropsEnum.USER_ACCOUNT.getProperty());

			if (account != null && akv.isValid()) {
				byte[] key = request.getKey();

				try {
					boolean aliasExists = kmu.doesAliasExist(
							account.getUserId(), request.getAlias(),
							ASServer.sql);

					if (!aliasExists) {
						String query = "INSERT INTO PublicKeys (userId, key_alias, key_content, key_instance) VALUES (? , ?, ?, ?)";
						String b64Key = new String(Base64.encode(request
								.getKey()), "UTF-8");

						String[] params = { account.getUserId() + "",
								request.getAlias(), b64Key,
								request.getKeyInstance() };
						ASServer.sql.execute(query, params);

						response.setSuccess(true);
					} else {
						response.addMessage(MessagesEnum.KEY_ALIAS_EXISTS);
					}
				} catch (Exception e) {
					response.addMessage(MessagesEnum.DATABASE_ERROR);
					ASLog.error(MessagesEnum.DATABASE_ERROR.getMessage(), e);
				}
			} else {
				response.addMessages(akv.getReasons());
			}
		}
		return response;
	}

	/**
	 * Method used to retreive a key by Alias and User Id
	 * 
	 * @param {@link GetKeyRequest request} 
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getKey")
	public GetKeyResponse getKey(GetKeyRequest request) {
		GetKeyResponse response = new GetKeyResponse();
		KeyRequestValidator krv = new KeyRequestValidator(request);

		if (krv.isValid()) {
			ArrayList<ASKey> keyList = new ArrayList<ASKey>();
			String query = "SELECT * FROM PublicKeys WHERE userId=? AND key_alias=?";
			String[] params = { request.getUserId() + "", request.getAlias() };

			ResultSet rs = null;

			try {
				rs = ASServer.sql.query(query, params);

				while (rs.next()) {
					String alias = rs.getString("key_alias");
					String keyString = rs.getString("key_content");
					String keyInstance = rs.getString("key_instance");

					ASKey key = new ASKey();
					key.setAlias(alias);
					key.setInstance(keyInstance);
					byte[] keyContent = Base64.decode(keyString.getBytes());
					key.setKey(keyContent);
					keyList.add(key);
				}
				response.setKeys(keyList.toArray(new ASKey[keyList.size()]));
				response.setSuccess(true);
			} catch (SQLException sqle) {
				ASLog.error("Database error", sqle);
				response.addMessage(MessagesEnum.DATABASE_ERROR);
			} finally {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}
		} else {
			response.addMessages(krv.getReasons());
		}

		response.addMessage(MessagesEnum.METHOD_NOT_IMPLEMENTED);
		return response;
	}
}
