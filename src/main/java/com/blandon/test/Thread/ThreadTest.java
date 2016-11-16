package com.blandon.test.Thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ThreadTest.class);
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//executeTask();
		//submitTask();
		//submitCallableTask();
		//simpleTimeoutTask();
		timeoutTask(true);
	}
	
	
	public static void executeTask(){
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		RunnableTask runnableTask = new RunnableTask();
		
		executorService.execute(runnableTask);
		
		logger.debug("Done the executing.");
		
		executorService.shutdown();
		
	}
	
	/*
	 * The submit(Runnable) method also takes a Runnable implementation, but returns a Future object. 
	 * This Future object can be used to check if the Runnable as finished executing.
	 * */
	public static void submitTask() throws InterruptedException, ExecutionException{
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		RunnableTask runnableTask = new RunnableTask();
		
		//This Future should have a null object as there is no return result for runnable task execution.
		@SuppressWarnings("unchecked")
		Future<Object> future = (Future<Object>)executorService.submit(runnableTask);
		
		Object result = future.get();
		
		if(result == null){
			logger.debug("Done the execution successfully.");
		}
		
		executorService.shutdown();
		
	}

	
	public static void submitCallableTask() throws InterruptedException, ExecutionException{
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		CallableTask callableTask = new CallableTask(false);
		
		Future<String> future = executorService.submit(callableTask);
		
		//The future object should contain the callableTask execution result.
		String result = (String)future.get();
		
		logger.debug("Result of executing callable task is: {}", result);
		
		executorService.shutdown();
		
	}
	
	
	public static void simpleTimeoutTask() throws InterruptedException, ExecutionException{
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		CallableTask callableTask = new CallableTask(true);
		Future<String> future = null;
		
		try {
			future = executorService.submit(callableTask);
			future.get(50, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			logger.debug("Timeout exception occurred.", e);
			future.cancel(true); //The executor service will stop executing current task
			//executorService.shutdown(); //The executor service will stop accepting new task, however it doesn't stop currently running task
			//executorService.shutdownNow(); //The executor service will stop currently running task and top accepting new task
		}catch(Exception e){
			logger.error("exception occurred during task execution.", e);
			future.cancel(true);
		}
		
		logger.debug("timeout task execution completed.");
		
		//executorService.shutdownNow();
		
	}
	
	public static void timeoutTask(boolean alltime) throws InterruptedException, ExecutionException{
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		CallableTask callableTask = new CallableTask(true);
		
		Future<String> future = null;
		int i =0;
		
		while(alltime){
			Thread.sleep(3000);
			 logger.debug(++i+" time calling callable task...");
			 future = executorService.submit(callableTask);
		try {
			future.get(50, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			logger.debug("Timeout exception occurred.", e);
			future.cancel(true); //The executor service will stop executing current task
		}catch(Exception e){
			logger.error("exception occurred during task execution.", e);
			future.cancel(true);
		}
		
		logger.debug("timeout task execution completed.");
		
	}
		executorService.shutdownNow();
		
	}
}
