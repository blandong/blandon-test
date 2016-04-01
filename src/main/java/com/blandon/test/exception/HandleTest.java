package com.blandon.test.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleTest {
	
	private static final Logger logger = LoggerFactory.getLogger(HandleTest.class);
	
	//tip: throw early, catch late
	
	public static void main(String[] args) {
		
		HandleTest ht = new HandleTest();
		
		//swallowed all exceptions
	//	ht.swallow();
		
		//original exception stack trace is lost
		//ht.test4(0);
		
		//original stack track is returned as well
		//ht.test4(1);
		
		//duplicates messages output, not need 
		ht.test5();
	}
	
	
	//If you think the client code cannot process the exception this method throws, just convert the checked exception to unchecked exception.
	private void test(){
		try{
			throw new ExampleException("example example");
		}catch(ExampleException e){
			throw new RuntimeException("runtime error, covert to unchecked exception so client doesn't need to deal with exception, just propagate.");
		}
	}
	
	
	//never swallow  exception 
	private String swallow(){
		//it totally swallows the exception, losing the cause of error forever.
		int x= 0;
		try{
			int i = 1;
			int j = 0;
			 x = i/j;
			//code throws exception
		}catch(Exception e ){
			//this statement swallows exceptions completely.
			return null;
		}
		
		return String.valueOf(x);
	}
	
	
	
	//Declare the specific checked exceptions that your method can throw

	private void unSpecificExceptionMethod() throws Exception { //Incorrect way
	}
	
	
	
	
	// Do not catch the Exception class rather catch specific sub classes, it will swallow all checked exception and unchecked exception as well.
	
	private void test3(){
		int x= 0;
		try{
			int i = 1;
			int j = 0;
			 x = i/j;
			 
		} catch (Exception e) {
			//LOGGER.error("method has failed", e);
		}
	}
	
	
	
	//Always correctly wrap the exceptions in custom exceptions so that stack trace is not lost
	private void test4(int i){
		try{
			if(i == 0){
				throw new ExampleException("example example");
			}else{
				throw new  CustomException2("custom example");
			}
		}catch (ExampleException e) {
			   throw new RuntimeException("Some information: " + e.getMessage());  //Incorrect way
		}catch(CustomException2 e){
			throw new RuntimeException("New information: " , e);  //correct way
		}
	}
	
	
	
	//Either log the exception or throw it but never do the both
	//As in this example code, logging and throwing will result in multiple log messages in log files, for a single problem in the code, and makes life hell for the engineer who is trying to dig through the logs.
	
	private void test5()  {
		try{
			throw new  ExampleException("example message");
		}catch (ExampleException e) {
			   logger.error("Some information", e);
			   throw new RuntimeException("runtime error", e);
			}
	}
	
	
	
	//Don’t use printStackTrace() statement or similar methods
	
	
	
	
	
	
	//Use finally blocks instead of catch blocks if you are not going to handle exception
	private void test6(){
		try {
			  //someMethod();  //Method 2
			} finally {
			 // cleanUp();    //do cleanup here
			}
	}
	
	
	
	
	//Always include all information about an exception in single log message
	/** 
	 * 	Using a multi-line log message with multiple calls to LOGGER.debug() may look fine in your test case, but when it shows up in the log file of an app server with 400 threads running in parallel, all dumping information to the same log file, your two log messages may end up spaced out 1000 lines apart in the log file, even though they occur on subsequent lines in your code.
	 * */
	private void test7(){
		try{
			throw new  ExampleException("");
		}catch(ExampleException e){
			logger.debug("Using cache sector A");
			logger.debug("Using retry sector B");
		}
	}
	
	
	
}
