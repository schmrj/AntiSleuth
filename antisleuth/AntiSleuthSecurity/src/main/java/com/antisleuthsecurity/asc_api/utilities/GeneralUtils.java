package com.antisleuthsecurity.asc_api.utilities;

import org.codehaus.jackson.map.ObjectMapper;

public class GeneralUtils {
	ObjectMapper mapper = new ObjectMapper();

	public String createJsonString(Object toParse) throws Exception {
		try{
			return mapper.writeValueAsString(toParse);
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	public Object createObjectFromJson(String value, Class<?> classType) throws Exception {
		try{
			return mapper.readValue(value, classType);
		}catch(Exception e){
			ASLog.error("Could not parse JSON Object", e);
			throw new Exception(e);
		}
	}
	
	public static boolean isEmptyString(String value) {
		return "".equals(value) ? true : false;
	}
}
