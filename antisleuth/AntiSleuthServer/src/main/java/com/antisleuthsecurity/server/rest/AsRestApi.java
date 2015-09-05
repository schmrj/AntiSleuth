package com.antisleuthsecurity.server.rest;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class AsRestApi extends HttpServlet{
	
	private ObjectMapper mapper = new ObjectMapper();
	@Context protected HttpServletRequest servletRequest;
	@Context protected HttpServletResponse servletResponse;
	
	protected HttpSession getSession(){
		return this.servletRequest.getSession(false);
	}
	
	protected <T> T getObject(String jsonString, Class<T> classType) throws JsonParseException, JsonMappingException, IOException{
		return classType.cast(this.mapper.readValue(jsonString, classType));
	}
}
