package com.blandon.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blandon.test.bean.User;
import com.blandon.test.dao.UserDao;

//@Service
public class TestService {
	
	@Autowired
	private UserDao userDao;
	
	public  User findByName(String name){
		return userDao.findByName(name);
	}
}
