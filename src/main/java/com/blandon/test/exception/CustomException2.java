package com.blandon.test.exception;

public class CustomException2 extends Exception{
	private String msg;
	
	public CustomException2(String msg){
		this.msg = msg;
	}
}
