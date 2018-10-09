package com.blandon.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class })
@ImportResource({"application-context-config.xml"})
public class Application {
	   public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }
}
