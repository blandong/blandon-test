package com.blandon.test.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.blandon.test.bean.User;

@Repository
public class UserDao {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public User findByName(String name){
		User user = new User(name);
		return user;
	}
	
	public User saveUser(User user){
		logger.debug(user.getName()+" is saved.");
		return user;
	}
}
