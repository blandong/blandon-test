package com.blandon.test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.blandon.test.bean.User;
import com.blandon.test.service.TestService;

@Controller
public class TestController {
	
	@Autowired
	private TestService testService;
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping(value = "/get1", method = RequestMethod.GET)
	public ModelAndView  handle(HttpServletRequest request, HttpServletResponse  response) throws IOException{
		
		String name = request.getParameter("name");
		
		User user =  testService.findByName(name);
		
		User newUser = new User("newCreatedUser");
		
		ModelAndView model = new ModelAndView();
		model.setViewName("displayUser");
		
		model.addObject("returnedUser", user);
		
		request.setAttribute("newUser", newUser);
		
		response.sendRedirect("http://sorry.covisint.com/covisint-notice.html");

		return model;
		
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String handle2(HttpServletRequest request, HttpServletResponse response){
		
		String requestUrl = request.getRequestURL().toString();
		String requestURI = request.getRequestURI();
		String host = request.getRemoteHost();
		String queryString= request.getQueryString();
		String refererUrl = request.getHeader("referer");
		
		logger.debug("request url: {}\n request URI: {}\n host: {}\n query string: {}\n refererUrl: {}\n", requestUrl, requestURI, host, queryString, refererUrl);
		
		String name1 = request.getParameter("name1");
		
		logger.debug("name1 is: {}", name1);
		
		User user = new User();
		String name = request.getParameter("name");
		
		logger.debug("name is: {}", name);
		user.setName(name);
		user.setAge(20);
		
		testService.saveUser(user);
		
		request.setAttribute("savedUser", user);
		
		return "saveSuccess";
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET, value="/test")
	public ModelAndView  handle3(HttpServletRequest request, HttpServletResponse  response) throws IOException{
		ModelAndView model = new ModelAndView();
		model.setViewName("displayUser");
		response.sendRedirect("http://sorry.covisint.com/covisint-notice.html");
		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/")
	public void  handle14(HttpServletRequest request, HttpServletResponse  response) throws IOException{
		ModelAndView model = new ModelAndView();
		model.setViewName("displayUser");
		response.sendRedirect("http://sorry.covisint.com/generic-maintenance.html");
	}
	
	@RequestMapping(method = RequestMethod.GET, value="name")
	@ResponseBody
	public String  handle4(HttpServletRequest request, HttpServletResponse  response){
		return "Name of user";
	}

}
