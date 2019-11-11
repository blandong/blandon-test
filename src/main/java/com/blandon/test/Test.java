package com.blandon.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");  
		TargetTest targetTest = (TargetTest)context.getBean("targetTest");
		targetTest.aopTest("*****aop test*****");
		context.close();
	}
	
}
