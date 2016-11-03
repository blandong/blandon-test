package com.blandon.test.Thread;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<String> {

	@Override
	public String call() throws Exception {
		System.out.println("Callable task completed.");
		Thread.sleep(2000);
		return "Completed successfully";
	}

}
