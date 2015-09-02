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
