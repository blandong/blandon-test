package com.blandon.test;

import com.blandon.test.bean.User;
import com.thoughtworks.xstream.XStream;

public class Test {
	public static void main(String[] args) {
		userToXml();
		xmlToUser();
	}
	
	public static void userToXml() {
		XStream xstream = new XStream();
		User user = new User("Blandon", 30);
		//This is an optional step. Without it XStream would work fine, but the XML element names would contain the fully qualified name of each class (including package) which would bulk up the XML a bit
		xstream.alias("user", User.class); 
		String xml = xstream.toXML(user);
		System.out.println("user in xml:\n"+xml);
	}
	
	public static void xmlToUser() {
		XStream xstream = new XStream();
		User user = new User("Blandon", 30);
		//This is an optional step. Without it XStream would work fine, but the XML element names would contain the fully qualified name of each class (including package) which would bulk up the XML a bit
		xstream.alias("user", User.class); 
		String xml = xstream.toXML(user);
		
		User newUser = (User)xstream.fromXML(xml);
		System.out.println(String.format("new user nam:%s, new user age: %d}", newUser.getName(), newUser.getAge()));
		
	}
}
