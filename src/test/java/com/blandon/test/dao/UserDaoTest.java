package com.blandon.test.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/app-context-config-test.xml" })
@ActiveProfiles({ "dev", "test" })
public class UserDaoTest {
	
	@Autowired
	UserDao userDao;
	
	@Test
	public void testSaveAuthn() {
		
		int row = userDao.saveAuthn();
		
		Assert.assertEquals(row, 1);
		
	}
	
}
