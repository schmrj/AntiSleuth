/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity;

import com.antisleuthsecurity.server.ASServer;

public class ServerConstants {
	public static final String version = "0.0.1";

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
