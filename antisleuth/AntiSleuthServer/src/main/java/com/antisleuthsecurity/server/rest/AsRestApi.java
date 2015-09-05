package com.antisleuthsecurity.server.rest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.bouncycastle.util.encoders.UrlBase64;

import com.antisleuthsecurity.asc_api.common.network.ASMessage;
import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.asc_api.utilities.GeneralUtils;

public class AsRestApi extends HttpServlet{
	
	@Context protected HttpServletRequest servletRequest;
	@Context protected HttpServletResponse servletResponse;
		
	protected String jsonEncodeMessage(ASMessage message){
		try{
			if(message != null){
				return new String(UrlBase64.encode(new GeneralUtils().createJsonString(message).getBytes()));
			}
		}catch(Exception e){
			ASLog.error("Could not encode the message", e);
		}
		return null;
	}
	
	protected HttpSession getSession(){
		return this.servletRequest.getSession(false);
	}
}
