package com.blandon.test;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blandon.test.bean.User;

@RestController //tells Spring to render the resulting string directly back to the caller.
@EnableAutoConfiguration //This annotation tells Spring Boot to “guess” how you will want to configure Spring, based on the jar dependencies that you have added. Since spring-boot-starter-web added Tomcat and Spring MVC, the auto-configuration will assume that you are developing a web application and setup Spring accordingly.
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
