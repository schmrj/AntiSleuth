/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antisleuthsecurity.asc_api.utilities;

import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Bobby
 */
public class ASLog {
    private static Logger logger = LogManager.getLogger("AntiSleuth");
    
    public ASLog(String logName){
    	init();
    	logger = LogManager.getLogger("AntiSleuth " + logName);
    }
    
    public static enum LEVELS{
    	TRACE, DEBUG, INFO, WARN, ERROR, FATAL
    };
    
    private static void init(){
        try{
            Properties props = new Properties();
            props.load(ASLog.class.getResourceAsStream("/log4j.properties"));
            PropertyConfigurator.configure(props);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void traceTSMaterialBelow(){
    	logger.trace("**** WARNING SENSITIVE MATERIAL BELOW ****");
    }
    
    public static void traceTSMaterialAbove(){
    	logger.trace("**** WARNING SENSITIVE MATERIAL Above ****");
    }
    
    public static void debug(String msg){
        logger.debug(msg); 
    }
    
    public static void debug(String msg, Throwable t){
        logger.debug(msg, t);
    }
    
    public static void info(String msg){
    	logger.info(msg);
    }
    
    public static void info(String msg, Throwable t){
    	logger.info(msg, t);
    }
    
    public static void warn(String msg){
    	logger.warn(msg);
    }
    
    public static void warn(String msg, Throwable t){
    	logger.warn(msg, t);
    }
    
    public static void error(String msg){
    	logger.error(msg);
    }
    
    public static void error(String msg, Throwable t){
    	logger.error(msg, t);
    }
    
    public static void fatal(String msg){
    	logger.fatal(msg);
    }
    
    public static void fatal(String msg, Throwable t){
    	logger.fatal(msg, t);
    }
    
    public static void trace(String msg, Throwable t){
    	logger.trace(msg, t);
    }
    
    public static void trace(String msg){
    	logger.trace(msg);
    }
    
    public static void log(LEVELS level, String msg, Throwable t){
    	switch(level){
    		case TRACE:
    			trace(msg, t);
    			break;
    		case DEBUG:
    			debug(msg, t);
    			break;
    		case INFO:
    			info(msg, t);
    			break;
    		case WARN:
    			warn(msg, t);
    			break;
    		case ERROR:
    			error(msg, t);
    			break;
    		case FATAL:
    			fatal(msg, t);
    			break;
			default:
				info(msg, t);
    	}
    }
    
    public static void log(LEVELS level, String msg){
    	switch(level){
    		case TRACE:
    			trace(msg);
    			break;
    		case DEBUG:
    			debug(msg);
    			break;
    		case INFO:
    			info(msg);
    			break;
    		case WARN:
    			warn(msg);
    			break;
    		case ERROR:
    			error(msg);
    			break;
    		case FATAL:
    			fatal(msg);
    			break;
			default:
				info(msg);
    	}
    }
}
