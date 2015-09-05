package com.antisleuthsecurity.server.rest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

public class AsRestApi extends HttpServlet{
	
	@Context protected HttpServletRequest servletRequest;
	@Context protected HttpServletResponse servletResponse;
	
	protected HttpSession getSession(){
		return this.servletRequest.getSession(false);
	}
}
