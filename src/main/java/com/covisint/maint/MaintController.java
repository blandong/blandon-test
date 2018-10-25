package com.covisint.maint;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MaintController {
	
	@RequestMapping(method = RequestMethod.GET, value="**")
	public void  handle14(HttpServletRequest request, HttpServletResponse  response) throws IOException{
		response.sendRedirect("http://sorry.covisint.com/generic-maintenance.html");
	}

}
