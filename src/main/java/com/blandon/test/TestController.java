package com.blandon.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.blandon.test.bean.User;
import com.blandon.test.service.TestService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/user")
public class TestController {
	
	private static final Logger logger =LoggerFactory.getLogger(TestController.class);
	
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
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView  handlePost(HttpServletRequest request, HttpServletResponse  response){
		
		//This retrieves the json formatted parameters from the ajax request.
		String formDataAsJsonString = request.getParameter("formData");
		
		//Get name from ajax request
		String userName = request.getParameter("userName");
		
		if(userName != null){
			throw new RuntimeException("Exception occurred");
		}
		
		User user =  testService.findByName("test");
		
		User newUser = new User("newCreatedUser");
		
		ModelAndView model = new ModelAndView();
		model.setViewName("displayUser");
		
		model.addObject("returnedUser", user);
		
		request.setAttribute("newUser", newUser);
		
		return model;
		
	}
	
	@RequestMapping(value="/populateUser", method = RequestMethod.POST)
	public @ResponseBody String populateUser(){
		
		User user =new User("Blandon", 20);
		
		JsonObjectBuilder userBuilder = Json.createObjectBuilder();
		userBuilder.add("name", user.getName());
		userBuilder.add("age", user.getAge());
		String userJson = userBuilder.build().toString();
		logger.debug("user: {}\n", userJson);
		
		return userJson;
	}
	
	@RequestMapping(value="/getUser", method = RequestMethod.GET)
	public @ResponseBody String getUser(){
		
		User user =new User("Blandon", 20);
		
		JsonObjectBuilder userBuilder = Json.createObjectBuilder();
		userBuilder.add("name", user.getName());
		userBuilder.add("age", user.getAge());
		String userJson = userBuilder.build().toString();
		logger.debug("user: {}\n", userJson);
		
		return userJson;
	}

	@RequestMapping(value="/postUser", method = RequestMethod.POST)
	public @ResponseBody String postUser(HttpServletRequest request){
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		User user =new User(name,Integer.valueOf(age));
		
		JsonObjectBuilder userBuilder = Json.createObjectBuilder();
		userBuilder.add("name", user.getName());
		userBuilder.add("age", user.getAge());
		String userJson = userBuilder.build().toString();
		logger.debug("user: {}\n", userJson);
		
		return userJson;
	}
	
	@RequestMapping(value="/postUser2", method = RequestMethod.POST)
	public @ResponseBody String postUser2(@RequestParam String name, @RequestParam int age){
		
		User user =new User(name,Integer.valueOf(age));
		
		JsonObjectBuilder userBuilder = Json.createObjectBuilder();
		userBuilder.add("name", user.getName());
		userBuilder.add("age", user.getAge());
		String userJson = userBuilder.build().toString();
		logger.debug("user: {}\n", userJson);
		
		return userJson;
	}
	
	@RequestMapping(value="/postUser3", method = RequestMethod.POST)
	public @ResponseBody String postUser3(HttpServletRequest request, HttpServletResponse  response) throws IOException{
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		User user =new User(name,Integer.valueOf(age));
		
		 response.setContentType("json");
         PrintWriter out = response.getWriter();
         JSONObject jsonObject = JSONObject.fromObject(user);
         logger.debug("user: {}\n", jsonObject.toString());
         out.print(jsonObject.toString());
         out.flush();
         out.close();
		
		
		return null;
	}
}
