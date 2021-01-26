package com.blandon.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.test.util.ReflectionTestUtils;

import com.blandon.test.bean.User;

public class Test {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		//Get the printName method and set its accessible to true.
		Method method = User.class.getDeclaredMethod("printName", String.class);
		method.setAccessible(true);
		
		//invoke the printName(String anything) to print passed parameter - myReflectionTest
		User user = new User();
		method.invoke(user, "myReflectionTest");
		
		//set name with reflection since name setter method does not exist, then call getName method.
		ReflectionTestUtils.setField(user, "name", "myName");
		System.out.println(user.getName());
		
		
	}
}
