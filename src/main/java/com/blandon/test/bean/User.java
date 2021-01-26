package com.blandon.test.bean;

public class User {
	private String name;
	private int age;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	private String printName(String anything) {
		System.out.println(anything);
		return anything;
	}
	
}
