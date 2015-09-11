/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.exceptions;

public class AscException extends Exception{
	public AscException(){
		super();
	}
	
	public AscException(String message){
		super(message);
	}
	
	public AscException(Throwable throwable){
		super(throwable);
	}
	
	public AscException(String message, Throwable throwable){
		super(message, throwable);
	}
}
