package com.antisleuthsecurity.asc_api.common.network;

import java.util.Iterator;
import java.util.TreeMap;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.antisleuthsecurity.asc_api.utilities.ASLog;
import com.antisleuthsecurity.asc_api.utilities.GeneralUtils;

public class ASMessage {
	boolean success = false;
	TreeMap<Integer, String> errors = new TreeMap<Integer, String>();
	TreeMap<String, Object> content = new TreeMap<String, Object>();
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public TreeMap<Integer, String> getErrors() {
		return errors;
	}
	public void setErrors(TreeMap<Integer, String> errors) {
		this.errors = errors;
	}
	public TreeMap<String, Object> getContent() {
		return content;
	}
	public void setContent(TreeMap<String, Object> content) {
		this.content = content;
	}
	
	public void addError(Integer errorNum, String errorMessage){
		this.errors.put(errorNum, errorMessage);
	}
	
	public void addContent(String key, Object value){
		this.content.put(key, value);
	}
	
	@JsonIgnore
	public Object getContent(String key){
		return this.content.get(key);
	}
	
	@JsonIgnore
	public Iterator<String> getContentKeys(){
		return this.content.keySet().iterator();
	}
	
	@JsonIgnore
	public String toString(){
		try{
			return new GeneralUtils().createJsonString(this);
		}catch(Exception e){
			ASLog.error("Could not get values", e);
			return null;
		}
	}
}
