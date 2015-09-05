package com.antisleuthsecurity;

import com.antisleuthsecurity.server.ASServer;

public class ServerConstants {
	public static final String version = "0.0.1";
	
	public static class DBProperties{
		public static final String USERNAME = "mssql.user";
		public static final String PASSWORD = "mssql.pass";
		public static final String ADDRESS = "mssql.host";
		public static final String PORT = "mssql.port";
		public static final String DATABASE = "mssql.database";
		public static final String MAX_RETRY = "mssql.max_retry";
		public static final String RETRY_DELAY = "mssql.retry_delay";
	}
	
	public static class SessionConstants{
		public static final String ACCOUNT = "account";
		public static final String SESSIONID_TAG = "JSESSIONID";
	}
	
	public static class KeyMgmtConstants{
		public static final String KEY_STRENGTH = ASServer.props.getProperty("session.keyStrength");
		public static final String KEY_LIFETIME_MINUTES = ASServer.props.getProperty("session.lifeTimeMin");
		public static final String EXPIRED_KEY_BUFFER = ASServer.props.getProperty("session.numKeysInMem");
	}
}
