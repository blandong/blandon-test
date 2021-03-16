package com.blandon.test.dao;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(PowerMockRunner.class)
public class TestWithPockMock {
	
	@Test
	@PrepareForTest({NamedParameterJdbcTemplate.class, UserDao.class})// required for PowerMockito.whenNew
	public void test1() throws Exception {
		Method addOrUpdateAuthenticationLogMethod = UserDao.class.getDeclaredMethod("addOrUpdateAuthenticationLog", String.class);
		addOrUpdateAuthenticationLogMethod.setAccessible(true);
		
		//get private field from instance
		//Field f = obj.getClass().getDeclaredField("stuffIWant"); //NoSuchFieldException
		//f.setAccessible(true);
		//Hashtable iWantThis = (Hashtable) f.get(obj); //IllegalAccessException
		
		
		UserDao userDao = new UserDao();
		// When spying, you take an existing object and "replace" only some methods.
		userDao = PowerMockito.spy(userDao);
		JdbcTemplate jdbcTemplate = PowerMockito.mock(JdbcTemplate.class);
		PowerMockito.doReturn(jdbcTemplate).when(userDao).getJdbcTemplate();
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = PowerMockito.mock(NamedParameterJdbcTemplate.class);
		PowerMockito.whenNew(NamedParameterJdbcTemplate.class).withAnyArguments().thenReturn(namedParameterJdbcTemplate);
		
		
		PowerMockito.when(namedParameterJdbcTemplate.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class))).thenReturn(1);
		
		String dummySQL = "\"dummySqlQuery\"";
		
		//example to set private field without setter method.
		ReflectionTestUtils.setField(userDao, "sql", dummySQL);
		
		int updatedRow = (int) addOrUpdateAuthenticationLogMethod.invoke(userDao, dummySQL);
		
		System.out.println("######: "+updatedRow);
		
		Assert.assertTrue(updatedRow == 1);
		
		
	}

}
