package com.blandon.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.blandon.test.bean.User;
import com.blandon.test.service.TestService;

@Controller
@RequestMapping(value="/user")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestService testService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView  handle(HttpServletRequest request, HttpServletResponse  response){
		
		String name = request.getParameter("name");
		
		User user =  testService.findByName(name);
		
		User newUser = new User("newCreatedUser");
		
		ModelAndView model = new ModelAndView();
		model.setViewName("displayUser");
		
		model.addObject("returnedUser", user);
		
		request.setAttribute("newUser", newUser);
		
		response.setCharacterEncoding("UTF-8");

		return model;
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	//@PathVariable String name,
	
	public @ResponseBody String saveUser( HttpServletRequest request, HttpServletResponse  response){
		
		String name = request.getParameter("name");
		
		logger.debug("The incoming user name is {}", name);
		
		User user = new User(name);
		
		User returnedUser = testService.saveUser(user);
		
		return "new user saved";
	}

}
