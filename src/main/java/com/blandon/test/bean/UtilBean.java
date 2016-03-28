package com.blandon.test.bean;

import com.blandon.test.TestEnum;

public class UtilBean {
	private String constant1;
	
	private int constant2;
	
	//inject enum
	private TestEnum testEnum;
	
	private String name1;
	
	private String name2;

	public String getConstant1() {
		return constant1;
	}

	public void setConstant1(String constant1) {
		this.constant1 = constant1;
	}

	public int getConstant2() {
		return constant2;
	}

	public void setConstant2(int constant2) {
		this.constant2 = constant2;
	}

	public TestEnum getTestEnum() {
		return testEnum;
	}

	public void setTestEnum(TestEnum testEnum) {
		this.testEnum = testEnum;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}
	
	
}
