package com.blandon.test;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blandon.test.bean.User;
import com.blandon.test.service.TestService;
import com.thoughtworks.xstream.XStream;

@RestController //tells Spring to render the resulting string directly back to the caller.
//@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class }) //This annotation tells Spring Boot to “guess” how you will want to configure Spring, based on the jar dependencies that you have added. Since spring-boot-starter-web added Tomcat and Spring MVC, the auto-configuration will assume that you are developing a web application and setup Spring accordingly.
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@Value("${user.age}") //inject value from application.properties file
	private int userAge;
	
	@RequestMapping("/")
	public String getUserJson(){
		User user = new User("Blandon", 20);
		
		JsonObjectBuilder userBuilder = Json.createObjectBuilder();
		userBuilder.add("name", user.getName());
		userBuilder.add("age", user.getAge());
		
		return userBuilder.build().toString();
	}
	
	@RequestMapping(value="/userXml/{username}/{age}", method=RequestMethod.GET)
	 public String fetchUserXml(@PathVariable String username, @PathVariable int age) {
		User user = this.testService.findByName("Bai");
		user.setAge(age);
		XStream xstream = new XStream();
		xstream.alias("user", User.class);
		String xml = xstream.toXML(user);
		return xml;
	}
	
	@RequestMapping(value="/userXml/{username}", method=RequestMethod.GET)
	 public String fetchUserWithAge(@PathVariable String username) {
		User user = this.testService.findByName("Bai");
		user.setAge(userAge);
		XStream xstream = new XStream();
		xstream.alias("user", User.class);
		String xml = xstream.toXML(user);
		return xml;
	}
	

	/**
	 * @param testService the testService to set
	 */
	public void setTestService(TestService testService) {
		this.testService = testService;
	}

}
