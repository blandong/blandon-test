package com.blandon.test.dao;

import org.springframework.stereotype.Repository;

import com.blandon.test.bean.User;

//@Repository
public class UserDao {
	
	public User findByName(String name){
		User user = new User(name);
		return user;
	}
}
