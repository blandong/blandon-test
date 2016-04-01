package com.blandon.test.exception;

public class ExampleException extends  Exception{
	
	private String message;
	
	public ExampleException(String msg){
		this.message=msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
