/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.client.common;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import com.sun.jersey.multipart.impl.MultiPartWriter;

public class WebServiceClient {

	ASLog logger = new ASLog("WebServiceClient");
	private WebResource resource = null;
	
	public WebServiceClient(){
	    
	}
	
	public WebServiceClient(String connectionUrl){
		this.resource = this.setClient(connectionUrl, false);
	}
	
	public WebServiceClient(String connectionUrl, boolean statelessClient){
		this.resource = this.setClient(connectionUrl, statelessClient);
	}
	
	public WebResource setClient(String connectionUrl, boolean stateless){
		try{
			DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
	        if(!stateless)
				config.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);
	        config.getClasses().add(MultiPartWriter.class);
	        config.getClasses().add(JacksonJsonProvider.class);
	        Client client = ApacheHttpClient.create(config);
	        client.setFollowRedirects(true);
	        WebResource resource = client.resource(connectionUrl);
	        return resource;
		}catch(Exception e){
			logger.error("Could not connect to the URL: " + connectionUrl, e);
			return null;
		}
	}
	
	public WebResource getClient(String connectionUrl){
		return this.setClient(connectionUrl, false);
	}
	
	public WebResource getClient(String connectionUrl, boolean StatelessClient){
		return this.setClient(connectionUrl, StatelessClient);
	}
	
	public WebResource getWebResource(){
		return this.resource;
	}
}
