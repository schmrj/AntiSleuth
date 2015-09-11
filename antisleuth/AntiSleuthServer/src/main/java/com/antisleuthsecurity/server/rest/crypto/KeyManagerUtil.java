/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.server.rest.crypto;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.antisleuthsecurity.common.mssql.MSSQL;

public class KeyManagerUtil {
	public boolean doesAliasExist(Integer userId, String alias, MSSQL sql) throws SQLException{
		boolean exists = false;
		
		String query = "SELECT * FROM PublicKeys WHERE userId=? AND key_alias=?";
		String[] params = { userId + "", alias };
		
		ResultSet rs = sql.query(query, params);
		
		while(rs.next()){
			exists = true;
		}
		
		rs.close();
		
		return exists;
	}
}
