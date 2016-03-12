package com.blandon.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.blandon.test.bean.User;
import com.blandon.test.service.TestService;

@Controller
@RequestMapping(value="/user")
public class TestController {
	
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

		return model;
		
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String handle2(HttpServletRequest request, HttpServletResponse response){
		User user = new User();
		String name = request.getParameter("name");
		user.setName(name);
		user.setAge(20);
		
		testService.saveUser(user);
		
		request.setAttribute("savedUser", user);
		
		return "saveSuccess";
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET, value="test")
	public ModelAndView  handle3(HttpServletRequest request, HttpServletResponse  response){
		ModelAndView model = new ModelAndView();
		model.setViewName("displayUser");
		return model;
	}
	
	

}
