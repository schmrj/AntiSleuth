/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.server;

import java.sql.ResultSet;

import com.antisleuthsecurity.asc_api.utilities.ASLog;

/**
 * @author Bob Schmidinger, schmrj@comcast.net
 * 
 */
public enum ServerSettings {

	ServerId("Server.id");

	private String setting = null;

	private ServerSettings(String setting) {
		this.setting = setting;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

	public String getValue() {
		String query = "SELECT * FROM Settings WHERE setting = ?";
		String[] params = { this.setting };
		String value = null;

		ResultSet rs = null;
		try {
			rs = ASServer.sql.query(query, params);
			if (rs.next()) {
				value = rs.getString("value");
			}
		} catch (Exception e) {
			ASLog.error("Could not find setting: " + this.setting + ", "
					+ e.getMessage());
		} finally {
			try {
				rs.close();
			} catch (Exception x) {
				ASLog.error("Could not close connection", x);
			}
		}

		return value;
	}

	public void setValue(String value) {
		String currentValue = this.getValue();
		String query = null;
		String[] params = null;
		if (currentValue == null) {
			query = "INSERT INTO Settings (setting, value) VALUES (?, ?)";
			params = new String[] { this.setting, value };
		} else {
			query = "UPDATE Settings SET value = ? WHERE setting = ?";
			params = new String[] { value, this.setting };
		}

		try {
			ASServer.sql.execute(query, params);
		} catch (Exception e) {
			ASLog.error("Could not set setting: " + this.setting + ", "
					+ e.getMessage());
		}
	}

}
