package com.blandon.test.view;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;

import com.blandon.test.bean.User;

public class UserJsonConverter {
	
	public JsonStructure marshal(User user){
		 JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		 jsonBuilder.add("userName", user.getName());
		 jsonBuilder.add("age", user.getAge());
		 
		 JsonStructure json = jsonBuilder.build();
		 
		 return json;
	}
	
}
