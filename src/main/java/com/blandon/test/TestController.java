package com.blandon.test;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blandon.test.bean.User;

@RestController
public class TestController {
	
	@RequestMapping("/")
	public String getUserJson(){
		User user = new User("Blandon", 20);
		
		JsonObjectBuilder userBuilder = Json.createObjectBuilder();
		userBuilder.add("name", user.getName());
		userBuilder.add("age", user.getAge());
		
		return userBuilder.build().toString();
	}

}
