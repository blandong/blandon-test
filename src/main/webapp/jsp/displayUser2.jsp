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

	<h1>Welcome, User2!!</h1>
	
	Returned name is: <h2>${returnedUser.name }</h2>

	New created user name is: <h3>${newUser.name }</h3>
	
	<c:if test="${b1 && b2}">
		<%out.println(falseTest); %>
		<%out.println("hi"); %>
	</c:if>
	

</html>




