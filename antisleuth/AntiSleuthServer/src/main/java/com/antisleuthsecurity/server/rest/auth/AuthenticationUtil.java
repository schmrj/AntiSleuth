package com.antisleuthsecurity.server.rest.auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.common.mssql.MSSQL;

public class AuthenticationUtil {
	
	public boolean addLoginAttempt(UserAccount account, boolean success,
			MSSQL sql) throws SQLException {
		return addLoginAttempt(account.getUserId(), success, sql);
	}
	
	public boolean addLoginAttempt(int account, boolean success,
			MSSQL sql) throws SQLException {
		String query = "INSERT INTO LoginAttempt (userId, success, timestamp) VALUES (?, ?, ?)";
		String pass = success ? "1" : "0";
		
		String[] params = { account + "", pass, Calendar.getInstance().getTime().getTime() + "" };
		boolean insert = sql.execute(query, params);
		
		return insert;
	}
	
	public Integer findUserId(String username, MSSQL sql) throws SQLException{
		Integer userid = null;
		
		String query = "SELECT id FROM Users WHERE username=?";
		ResultSet rs = sql.query(query, new String[] { username });
		
		while(rs.next()){
			userid = rs.getInt("id");
		}
		
		rs.close();
		return userid;
	}
}
