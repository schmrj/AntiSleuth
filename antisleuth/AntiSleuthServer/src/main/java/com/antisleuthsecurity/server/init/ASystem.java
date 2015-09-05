package com.antisleuthsecurity.server.init;

import com.antisleuthsecurity.ServerConstants.DBProperties;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.common.mssql.MSSQL;
import com.antisleuthsecurity.server.ASServer;


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
		public static String USERNAME = ASServer.props.getProperty(DBProperties.USERNAME);
		public static String PASSWORD = ASServer.props.getProperty(DBProperties.PASSWORD);
		public static String ADDRESS = ASServer.props.getProperty(DBProperties.ADDRESS);
		public static String PORT = ASServer.props.getProperty(DBProperties.PORT);
		public static String DATABASE = ASServer.props.getProperty(DBProperties.DATABASE);
		public static String MAX_RETRY = ASServer.props.getProperty(DBProperties.MAX_RETRY);
		public static String RETRY_DELAY = ASServer.props.getProperty(DBProperties.RETRY_DELAY);
	}
}
