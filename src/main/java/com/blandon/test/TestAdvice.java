package com.blandon.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TestAdvice {

	//any scope and any return type of any method in classcom.blandon.test.TargetTest
	@Pointcut("execution(* com.blandon.test.TargetTest.*(..))")
	public void pointCutMethod() {
	}
	
	//any method in any class of any package
	@Pointcut("execution(* *(..))")
	public void pointCutMethod2() {
	}
	
	@Before("pointCutMethod2() ")
	public void adviceAopTest2() {
		System.out.println("method intercepted in advice of adviceAopTest2.");
	}
	
	@Before("pointCutMethod() ")
	public void adviceAopTest() {
		System.out.println("method intercepted in advice of adviceAopTest.");
	}
	
	@Before("pointCutMethod() && args(arg) ")
	public void adviceAopTest3(String test) {
		System.out.println("method intercepted in advice of adviceAopTest3.");
	}
	
	
}
