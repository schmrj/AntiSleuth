package com.antisleuthsecurity.server;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.sun.jersey.api.core.ResourceConfig;

public class Properties {
	ServletContext context = null;
	ResourceConfig config = null;
	
	ServletConfig servletConfig = null;

	public Properties(ServletContext context, ServletConfig config){
		this.context = context;
		this.servletConfig = config;
	}
	
	public Properties(ServletContext context, ResourceConfig config) {
		this.context = context;
		this.config = config;
	}
	
	public String getProperty(String property){
		
		if(config != null)
			return (String) this.config.getProperty(property);
		else
			return (String) this.servletConfig.getInitParameter(property);
	}
	
	public URL getResource(String resource) throws MalformedURLException{
		return this.context.getResource(this.getProperty(resource));
	}
	
	public InputStream getResourceAsStream(String filename){
		return this.context.getResourceAsStream(filename);
	}
}
