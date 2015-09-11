/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.server;

public enum PropsEnum {
	
	// SESSION PARAMETERS
	USER_ACCOUNT ("session.useraccount"),
	
	// Authentication Parameters
	LOGIN_MAX_FAILED ("login.max_failed"),
	LOGIN_LOCK_TIME ("login.account_lock_time"),
	
	// Database Init Parameters
	DB_HOST ("mssql.host"),
	DB_PORT ("mssql.port"),
	DB_NAME ("mssql.database"),
	DB_MAX_RETRY ("mssql.max_retry"),
	DB_RETRY_DELAY ("mssql.retry_delay"),
	DB_USERNAME ("mssql.user"),
	DB_PASSWORD ("mssql.pass");

	private String property = null;
	
	private PropsEnum(String property){
		this.property = property;
	}

	public String getProperty() {
		return property;
	}
	
	@Override
	public String toString(){
		return property;
	}
}
