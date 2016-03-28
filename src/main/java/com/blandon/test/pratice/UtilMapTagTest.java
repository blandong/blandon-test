package com.blandon.test.pratice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blandon.test.bean.UtilBean;

public class UtilMapTagTest {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilMapTagTest.class);
	
	public static void main(String[] args) throws Exception{
		
		//load context
		
		ApplicationContext beans = loadContext();
		
		UtilBean utilBean = (UtilBean)beans.getBean("utilBean");
		
		System.out.println("value of constant1 is: "+utilBean.getConstant1());
		
		System.out.println("value of constant2 is: "+utilBean.getConstant2());
		
		System.out.println("value of testEnum is: "+ utilBean.getTestEnum());
		
		UtilBean utilBean2 = (UtilBean)beans.getBean("utilBean2");
		
		System.out.println("value of name1 is: "+utilBean2.getName1());
		
		System.out.println("value of name2 is: "+utilBean2.getName2());
		
	}
	
	
	
	public static ApplicationContext loadContext() throws Exception{
		
		ApplicationContext appContext = null;
		
		try{
			appContext = new ClassPathXmlApplicationContext("context-core.xml");
			
			logger.info("loaded context successfully.");
			
		}catch(BeansException be){
			logger.error("Failed to load context, please check.", be.getCause());
			throw new Exception("Error occurs during beans initilization.");
		}
		
		return appContext;
	}
}
