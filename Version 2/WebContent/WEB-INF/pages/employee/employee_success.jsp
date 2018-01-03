<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import = "utility.*" %>
<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<%--
Project: Pegasus Internal Web Application  
Assignment: #2
Author: Piotr Grabowski
Student Number: 100730728
Date: Dec 28,2017
Description: HTML for successful employee insertion
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Entry</title>
</head>
<body>
	<c:if test = "${success}">
		<h1>Successfully added ${employee.fName} ${employee.lName}</h1>
		<a class=\"btn-default btn-lg btn-primary\" href= "EmployeeForm">Back to Employee Entry</a>
	</c:if>
	<c:if test = "${!success}">
		<h1>Could not insert ${employee.fName} ${employee.lName}</h1>
			<c:if test = "${sqlexception != null}">
				<p>${sqlexception}</p>	
			</c:if>
		<a class=\"btn-default btn-lg btn-primary\" href= "EmployeeForm">Back to Employee Entry</a>
	</c:if>
</body>
</html>