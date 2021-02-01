package com.blandon.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blandon.test.dao.CrossDBDao;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	
	@Autowired
	CrossDBDao crossDBDao;
	
	
	   public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }

	@Override
	public void run(String... args) throws Exception {
		crossDBDao.crossDBCheck();
		
	}
	   
	   
}
