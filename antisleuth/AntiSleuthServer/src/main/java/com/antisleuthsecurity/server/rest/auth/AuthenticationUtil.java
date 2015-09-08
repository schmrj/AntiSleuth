package com.antisleuthsecurity.server.rest.auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import com.antisleuthsecurity.asc_api.rest.UserAccount;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.common.mssql.MSSQL;
import com.antisleuthsecurity.server.ASServer;
import com.antisleuthsecurity.server.PropsEnum;

public class AuthenticationUtil {

	public boolean addLoginAttempt(UserAccount account, boolean success,
			MSSQL sql) throws SQLException {
		return addLoginAttempt(account.getUserId(), success, sql);
	}

	public boolean addLoginAttempt(int account, boolean success, MSSQL sql)
			throws SQLException {
		String query = "INSERT INTO LoginAttempt (userId, success, timestamp) VALUES (?, ?, ?)";
		String pass = success ? "1" : "0";

		String[] params = { account + "", pass,
				Calendar.getInstance().getTime().getTime() + "" };
		boolean insert = sql.execute(query, params);

		return insert;
	}

	public Integer findUserId(String username, MSSQL sql) throws SQLException {
		Integer userid = null;

		String query = "SELECT id FROM Users WHERE username=?";
		ResultSet rs = sql.query(query, new String[] { username });

		while (rs.next()) {
			userid = rs.getInt("id");
		}

		rs.close();
		return userid;
	}

	public boolean isAccountLocked(Integer userId, MSSQL sql) throws SQLException {
		boolean isLocked = false;
		int minutesForLock = Integer.parseInt(ASServer.props
				.getProperty(PropsEnum.LOGIN_LOCK_TIME));
		long miliElapsed = (minutesForLock * 60) * 1000;
		int failedAttemptMax = Integer.parseInt(ASServer.props
				.getProperty(PropsEnum.LOGIN_MAX_FAILED));

		long currentTime = (Calendar.getInstance().getTimeInMillis() - miliElapsed);

		String query = "SELECT Count(*) as FailedAttempt FROM LoginAttempt WHERE userid = ? AND timestamp > ? AND success = 0";
		String[] params = { userId + "", currentTime + "" };

		ResultSet rs = null;
		try {
			rs = sql.query(query, params);

			while (rs.next()) {
				int attempts = rs.getInt("FailedAttempt");

				if (attempts > failedAttemptMax) {
					ASLog.warn("UserId: " + userId + " has a locked account, " + attempts + " failed attempts");
					isLocked = true;
				}
			}
		} catch (Exception e) {
			
		} finally {
			if (rs != null)
				rs.close();
		}

		return isLocked;
	}
}
