package com.blandon.test.Thread;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallableTask implements Callable<String> {
	
	private static final Logger logger = LoggerFactory.getLogger(CallableTask.class);
	
	private boolean throwException;
	
	public CallableTask(boolean throwException){
		this.throwException = throwException;
	}

	@Override
	public String call() throws Exception {
		try{
			logger.debug("Callable task is running.");
			if(throwException){
				throw new Exception("throw exception to test if it can be catched");
			}
			Thread.sleep(7000);
			logger.debug("Callable task Completes successfully.");
			return "Completed successfully";
		}finally{
			logger.debug("Callable task finally statement is reached!!");
		}
	}

}
