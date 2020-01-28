package com.blandon.test;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.blandon.test.bean.User;
import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.LazyArgs;

public class Test {
	
	private static final FluentLogger logger = FluentLogger.forEnclosingClass();
	
	public static void main(String[] args) {
		 	logger.atWarning().log("test warning log");

	        logger.atInfo().log("test info log");

	        logger.at(Level.SEVERE)
	                .atMostEvery(50, TimeUnit.SECONDS)
	                .log("SEVERE", LazyArgs.lazy(() -> doSomeCostly()));
	        
	        //logger.atWarning().withCause().log("warning");
	        
	        User user=new User();
	        logger.atInfo().log("Info logs : %s", user);

	    }




	public static int doSomeCostly() {
	    return 0;
	}
	
}
