package com.antisleuthsecurity.server;

import javax.servlet.ServletException;

import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.common.mssql.MSSQL;
import com.antisleuthsecurity.server.init.ASystem;
import com.antisleuthsecurity.server.init.ASystem.DBConstants;
import com.antisleuthsecurity.server.threads.KeyManagementThread;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class ASServer extends ServletContainer {

	// 4 Feb 2015
	private static final long serialVersionUID = 04022015L;
	public static KeyManagementThread keyThread = null;
	public static Properties props = null;
	public static MSSQL sql = null;

	private ASystem system = new ASystem();
	
	public void init() throws ServletException {
		super.init();
		ASLog.info("Starting AntiSleuth Security Server");
		ASLog.info("Initializing the server properties");
		this.props = new Properties(this.getServletContext(),
				this.getServletConfig());
		
		try{
			this.keyThread = new KeyManagementThread();
			ASLog.info("Starting Key Management Thread");
			this.keyThread.start();
			synchronized (this.keyThread) {
				this.keyThread.wait();
			}
		}catch(Exception e){
			ASLog.fatal("Could not generate required keys", e);
			System.exit(1);
		}

		ASLog.info("Setting up database connection");
		this.sql = system.initDB();
		connectDB();
	}

	private void connectDB() {
		int failureCount = 0;
		int maxFails = Integer.parseInt(DBConstants.MAX_RETRY);
		int sleepTime = Integer.parseInt(DBConstants.RETRY_DELAY);

		try {
			ASLog.debug("Attempting to connect to database: " + DBConstants.DATABASE);
			this.sql.connect();
			ASLog.debug("Connected to database: " + DBConstants.DATABASE);
		} catch (Exception e) {
			ASLog.warn("Could not connect to the database", e);

			try {
				if (failureCount < maxFails) {
					failureCount++;
					Thread.sleep(sleepTime);
				} else {
					ASLog.fatal(
							"Could not establish database connection after "
									+ maxFails + "retries", e);
					System.exit(1);
				}
			} catch (Exception e2) {
				ASLog.warn("Could not sleep for database reconnection attempt",
						e2);
			}
		}
	}
}
