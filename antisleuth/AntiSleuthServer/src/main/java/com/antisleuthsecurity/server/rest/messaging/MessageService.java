package com.antisleuthsecurity.server.rest.messaging;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bouncycastle.util.encoders.Base64;
import org.codehaus.jackson.map.ObjectMapper;

import com.antisleuthsecurity.asc_api.common.error.MessagesEnum;
import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.rest.crypto.MessageParts;
import com.antisleuthsecurity.asc_api.rest.requests.SendMessageRequest;
import com.antisleuthsecurity.asc_api.rest.responses.GetMessageResponse;
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
					MessageParts msgParts = request.getMsgParts();

					TreeMap<String, byte[]> keys = msgParts.getKeys();
					byte[] msg = Base64.encode(msgParts.getMessage());
					TreeMap<String, Object> options = msgParts.getOptions();

					Iterator<String> keySet = keys.keySet().iterator();
					Iterator<String> optionSet = options.keySet().iterator();

					String keyCipher = msgParts.getKeyCipherInstance();
					String msgCipher = msgParts.getMessageCipherInstance();
					UserAccount from = msgParts.getFrom();

					while (keySet.hasNext()) {
						String keyName = keySet.next();
						byte[] key = Base64.encode(keys.get(keyName));
						try {
							String option = new ObjectMapper()
									.writeValueAsString(options);
							Integer to = new AuthenticationUtil().findUserId(
									keyName, ASServer.sql);

							String query = "INSERT INTO Messages ([to], [from], message, [key], keyCipherInstance, msgCipherInstance, options) VALUES (?, ?, ?, ?, ?, ?, ?)";
							String[] params = { to + "",
									msgParts.getFrom().getUserId() + "",
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

		return response;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/receive")
	public GetMessageResponse getMessages() {
		GetMessageResponse response = new GetMessageResponse();
		HttpSession session = this.servletRequest.getSession(false);

		if (session != null) {
			UserAccount myAccount = (UserAccount) session
					.getAttribute(PropsEnum.USER_ACCOUNT.getProperty());

			if (myAccount != null) {
				String query = "SELECT * FROM Messages WHERE [to] = ?";
				String[] params = { myAccount.getUserId() + "" };
				ResultSet rs = null;

				try {
					rs = ASServer.sql.query(query, params);

					while (rs.next()) {
						Integer msgId = rs.getInt("id");
						byte[] msg = Base64.decode(rs.getBytes("message"));
						byte[] key = Base64.decode(rs.getBytes("key"));
						String options = rs.getString("options");
						String keyCipherInstance = rs
								.getString("keyCipherInstance");
						String msgCipherInstance = rs
								.getString("msgCipherInstance");

						MessageParts parts = new MessageParts();
						parts.setMessageId(msgId);
						parts.setKeyCipherInstance(keyCipherInstance);
						parts.setMessageCipherInstance(msgCipherInstance);
						parts.addKey(myAccount.getUsername(), key);
						parts.addMessage(msg);
						parts.setOptions(new ObjectMapper().readValue(options, TreeMap.class));
						UserAccount from = new AuthenticationUtil()
								.findUserById(rs.getInt("from"), ASServer.sql);
						parts.setFrom(from);
						
						response.addMsg(msgId, parts);
					}
					

					if(response.getMsgs().size() > 0){
						response.setSuccess(true);
					}else{
						response.addMessage(MessagesEnum.MESSAGE_NONE_AVAILABLE);
					}
				} catch (Exception e) {
					response.addMessage(MessagesEnum.DATABASE_ERROR);
				} finally {
					try {
						rs.close();
					} catch (Exception e2) {
						response.addMessage(MessagesEnum.DATABASE_ERROR);
					}
				}
			} else {
				response.addMessage(MessagesEnum.NOT_AUTHENTICATED);
			}
		} else {
			response.addMessage(MessagesEnum.NOT_AUTHENTICATED);
		}

		return response;
	}
}
