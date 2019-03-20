package com.blandon.test;

import com.blandon.test.bean.BuilderUser;
import com.blandon.test.bean.BuilderUserWithMandatoryFields;

public class Test {
	public static void main(String[] args) {
		
		BuilderUser bu = new BuilderUser.Builder().name("name").age(1).build();
		BuilderUserWithMandatoryFields buwf = new BuilderUserWithMandatoryFields.Builder().name("name").age(1).build();
	}
}
