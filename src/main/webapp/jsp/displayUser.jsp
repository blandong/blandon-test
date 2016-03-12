<%@ page import="com.blandon.test.*"%>

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%
	boolean b1 = true;

	boolean b2 = true;

	request.setAttribute("b1", b1);
	request.setAttribute("b2", b2);
	

	boolean falseTest = false;
	
%>

<html>

	<h1>Welcome, New Commers!!</h1>
	
	<span>Returned name is:</span> <h2>${returnedUser.name }</h2>

	<span>New created user name is: </span><h3>${newUser.name }</h3>
	
	<span>Name from request: ${name}</span><br/>
	
	<span>another Name from request: ${param.name}</span><br/>
	
	
	<c:if test="${b1 && b2}">
		<%out.println(falseTest); %>
		<%out.println("hi"); %>
	</c:if>
	

</html>




