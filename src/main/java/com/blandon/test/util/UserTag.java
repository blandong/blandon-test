package com.blandon.test.util;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


public class UserTag extends TagSupport{
	private static final long serialVersionUID = -1785761817715491721L;
	private String property;
	
	@Override
	public int doStartTag() throws JspException {
	     JspWriter out = pageContext.getOut();
	     
	     try {
	    	if("error".equalsIgnoreCase(property)){
	    		out.println("<strong><span style=\"color: red;\">");
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
	     return(EVAL_BODY_INCLUDE); 
	}


	@Override
	public int doEndTag() throws JspException {
		 try {
			  JspWriter out = pageContext.getOut();
			  if("error".equalsIgnoreCase(property)){
				  out.print("</span></strong>");
			  }
		   
		  } catch(IOException ie) {
			  throw new JspException(ie.getMessage());

		  }
		  return(EVAL_PAGE); // Continue with rest of JSP page
	}


	public void setProperty(String property) {
		this.property = property;
	}
	
	
	
	
}
