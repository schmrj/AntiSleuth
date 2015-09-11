/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.common.mssql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bob Schmidinger
 */
public class MSSQL {
    // TODO Move to Config File/GUI
    String HOST = "localhost";
    String PORT = "1433";
    String username = "sysadm";
    String password = "sysadm";
    String Database = "CryptoServ";
    
    String conn_url = null;
        
    Connection con = null;
    ResultSet rs = null;
        
    /*public MSSQL(){
        this.prep();
    }*/
    public MSSQL(String address, String port, String username, String password, String db){
        this.HOST = address; this.PORT = port; this.username = username; this.password = password;
        this.Database = db;
        this.prep();
    }
    
    public MSSQL(){	
    	this.prep();
    }
    
    public void prep(){
        this.conn_url = "jdbc:sqlserver://" + HOST + ":" + PORT +
            ";DatabaseName=" + Database +
            ";user=" + username + ";" + 
            ";Password=" + password;
    }
    
    /**
     * Connect to MS SQL Server
     * @throws SQLException
     */
    public void connect() throws SQLException{
        try{
            this.init();
        }catch(Exception e){
            SQLException sqle = new SQLException(e);
            throw sqle;
        }
    }
    
    private void init() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(conn_url);
    }
    
    /**
     * Query MS SQL Server
     * @param query
     * @return
     * @throws SQLException
     */
    public ResultSet query(String query, String[] params) throws SQLException{
        PreparedStatement state = con.prepareStatement(query);
        state = prepareStatement(state, params);
        this.rs = state.executeQuery();
        return this.rs;
    }
    
    public boolean execute(String query, String[] params) throws SQLException{
        PreparedStatement state = con.prepareStatement(query);
        state = prepareStatement(state, params);
        return state.execute();
    }
    
    private static PreparedStatement prepareStatement(PreparedStatement statement, String[] params) throws SQLException{
    	for(int i = 0; i < params.length; i++)
    		statement.setString(i+1, params[i]);
    	return statement;
    }
    
    /**
     *
     * @return
     * @throws SQLException
     */
    public ResultSet next() throws SQLException{
        if(rs.next()){
            return rs;
        }else
            return null;
    }
    
    /**
     * Close the connection to MS SQL Server
     * @throws SQLException
     */
    public void close() throws SQLException{
        this.con.close();
    }
    
    public static boolean executeQuery(MSSQL sql, String query, String[] params) throws SQLException{
		sql.connect();
		sql.execute(query, params);
		sql.close();
		return true;
	}

	public static ResultSet runQuery(MSSQL sql, String query, String[] params) throws SQLException{
		sql.connect();
		ResultSet rs = sql.query(query, params);
		return rs;
	}

	public String getHOST() {
		return HOST;
	}

	public String getPORT() {
		return PORT;
	}

	public String getUsername() {
		return username;
	}

	public String getDatabase() {
		return Database;
	}
}
