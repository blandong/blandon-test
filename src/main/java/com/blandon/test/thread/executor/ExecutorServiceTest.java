package com.blandon.test.thread.executor;

public class ExecutorServiceTest {
	
	/*
	 * 1. Name pool threads
	I can't emphasize this. 
	When dumping threads of a running JVM or during debugging, 
	default thread pool naming scheme is pool-N-thread-M, 
	where N stands for pool sequence number (every time you create a new thread pool,
	global N counter is incremented) and M is a thread sequence number within a pool. 
	For example pool-2-thread-3 means third thread in second pool created in the JVM lifecycle.
	See: Executors.defaultThreadFactory(). 
    Not very descriptive. JDK makes it slightly complex to properly name threads because naming strategy is hidden inside ThreadFactory. 
	Luckily Guava has a helper class for that:
	 * */
	private static void 

}
