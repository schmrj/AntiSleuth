/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.server.init;

import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.common.mssql.MSSQL;
import com.antisleuthsecurity.server.ASServer;
import com.antisleuthsecurity.server.PropsEnum;


public class ASystem {
	
	public MSSQL initDB(){
		ASLog.info("DATABASE CONFIG INIT");
		ASLog.info("\tHOST:     " + DBConstants.ADDRESS);
		ASLog.info("\tPORT:     " + DBConstants.PORT);
		ASLog.info("\tUsername: " + DBConstants.USERNAME);
		ASLog.info("\tDatabase: " + DBConstants.DATABASE);
		
		MSSQL sql = new MSSQL(DBConstants.ADDRESS,
							  DBConstants.PORT,
							  DBConstants.USERNAME,
							  DBConstants.PASSWORD,
							  DBConstants.DATABASE);
		return sql;
	}
	
	public static class DBConstants{
		public static String USERNAME = ASServer.props.getProperty(PropsEnum.DB_USERNAME);
		public static String PASSWORD = ASServer.props.getProperty(PropsEnum.DB_PASSWORD);
		public static String ADDRESS = ASServer.props.getProperty(PropsEnum.DB_HOST);
		public static String PORT = ASServer.props.getProperty(PropsEnum.DB_PORT);
		public static String DATABASE = ASServer.props.getProperty(PropsEnum.DB_NAME);
		public static String MAX_RETRY = ASServer.props.getProperty(PropsEnum.DB_MAX_RETRY);
		public static String RETRY_DELAY = ASServer.props.getProperty(PropsEnum.DB_RETRY_DELAY);
	}
}
