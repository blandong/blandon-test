package com.blandon.test.view;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class UserJsonConverterViewResolver implements ViewResolver, Ordered{
	
	private UserJsonView view;
	
	private int order = Integer.MAX_VALUE;  // default: same as non-Ordered
	
	public UserJsonConverterViewResolver(UserJsonView view){
		this.view= view;
	}

	public View resolveViewName(String viewName, Locale locale) throws Exception {
		return view;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	

}
