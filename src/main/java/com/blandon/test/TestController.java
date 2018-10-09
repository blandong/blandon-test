package com.blandon.test;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blandon.test.domain.UserPassword;



@RestController //tells Spring to render the resulting string directly back to the caller.
//@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class }) //This annotation tells Spring Boot to “guess” how you will want to configure Spring, based on the jar dependencies that you have added. Since spring-boot-starter-web added Tomcat and Spring MVC, the auto-configuration will assume that you are developing a web application and setup Spring accordingly.
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping(value="/sync", method=RequestMethod.POST)
	 public String fetchUserXml(@RequestHeader(name = "authorization", required = true) String authorization,
			 @RequestBody @Valid UserPassword userPassword ) throws IOException, URISyntaxException {
		logger.debug("Writing userPassword: {} ", userPassword);
		writeUserPassword(userPassword);
		logger.debug("userPassword is written to file for user: {} ", userPassword.getGblCovUserId());
		return null;
	}
	
	
	public void writeUserPassword(UserPassword userPassword) throws IOException, URISyntaxException {
		String passwordRecord = userPassword.toString()+"\n";
		URL url = TestController.class.getClassLoader().getResource("userPasswords.txt");
		logger.debug("url: "+url.toString());
		Files.write(Paths.get(url.toURI()), passwordRecord.getBytes(),  StandardOpenOption.APPEND);
	}

}
