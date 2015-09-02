package com.antisleuthsecurity.asc_api.common.network;

public class ASMessageConstants {
	public static class LoginMessages{
		public static final String SALT = "salt";
		public static final String PUBLIC_KEY = "publicKey";
	}
	
	public static class ApiMessageParts{
		public static final String SUCCESS_MESSAGE = "successMessage";
	}
	
	public static class MessageParts{
		public static final String TO = "to";
		public static final String FROM = "from";
		public static final String SUBJECT = "subject";
		public static final String BODY = "body";
		public static final String CIPHER = "cipher";
		public static final String CIPHER_TYPE = "type";
		public static final String CIPHER_INSTANCE = "cipherInstance";
		public static final String SECURITY_KEY= "securityKey";
		public static final String MSG_KEY_CIPHER= "msgKeyCipher";
		public static final String MSG_KEY_INSTANCE= "msgKeyInstance";
		public static final String MSG_KEY_ALIAS= "msgKeyAlias";
		public static final String MESSAGES= "messages";
	}
	
	public static class CommonEncMsgParts{
		public static final String MESSAGE_CONTENT = "message";
	}
	
	public static class AesMsgParts{
		public static final String KEY = "messageKey";
		public static final String IV = "messageIV";
	}
	
	public static class AccountParts{
		public static final String USERNAME = "username";
		public static final String USER_KEYS = "keys";
	}
}
