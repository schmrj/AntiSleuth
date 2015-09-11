package com.antisleuthsecurity.server.rest.messaging;

import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bouncycastle.util.encoders.Base64;
import org.codehaus.jackson.map.ObjectMapper;

import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.requests.SendMessageRequest;
import com.antisleuthsecurity.asc_api.rest.responses.SendMessageResponse;
import com.antisleuthsecurity.server.ASServer;
import com.antisleuthsecurity.server.PropsEnum;
import com.antisleuthsecurity.server.rest.AsRestApi;
import com.antisleuthsecurity.server.rest.auth.AuthenticationUtil;
import com.antisleuthsecurity.server.rest.validation.SendMessageValidator;

@Path("/messaging")
public class MessageService extends AsRestApi {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/send")
	public SendMessageResponse sendMessage(SendMessageRequest request) {
		SendMessageResponse response = new SendMessageResponse();
		HttpSession session = this.servletRequest.getSession(false);
		if (session != null) {
			if (session.getAttribute(PropsEnum.USER_ACCOUNT.getProperty()) == null) {
				response.addMessage(MessagesEnum.NOT_AUTHENTICATED);
			} else {
				SendMessageValidator smv = new SendMessageValidator(request);

				if (smv.isValid()) {
					TreeMap<String, byte[]> keys = request.getKeys();
					TreeMap<String, byte[]> msgs = request.getMessages();
					TreeMap<String, Object> options = request.getOptions();

					Iterator<String> keySet = keys.keySet().iterator();
					Iterator<String> optionSet = options.keySet().iterator();

					String keyCipher = request.getKeyCipherInstance();
					String msgCipher = request.getMessageCipherInstance();
					UserAccount from = request.getFrom();

					while (keySet.hasNext()) {
						String keyName = keySet.next();
						byte[] key = Base64.encode(keys.get(keyName));
						byte[] msg = Base64.encode(msgs.get(keyName));
						try {
							String option = new ObjectMapper()
									.writeValueAsString(options);
							Integer to = new AuthenticationUtil().findUserId(
									keyName, ASServer.sql);

							String query = "INSERT INTO Messages ([to], [from], message, [key], keyCipherInstance, msgCipherInstance, options) VALUES (?, ?, ?, ?, ?, ?, ?)";
							String[] params = { to + "",
									request.getFrom().getUserId() + "",
									new String(msg), new String(key),
									keyCipher, msgCipher, option };
							boolean pass = ASServer.sql.execute(query, params);

							response.setSuccess(true);
						} catch (Exception e) {
							response.addMessage(MessagesEnum.DATABASE_ERROR);
						}
					}
				} else {
					response.addMessages(smv.getReasons());
				}
			}
		}

		response.addMessage(MessagesEnum.METHOD_NOT_IMPLEMENTED);
		return response;
	}
}
