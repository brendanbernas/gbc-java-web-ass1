<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>

<%
/*Project: Pegasus Internal Web Application  
*Assignment: #1
*Author: Brendan Bernas, Albert Nguyen
*Student Number: 101012650, 100865315
*Date: Oct 19,2017
*Description: HTML for department insertion success
*/ 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Deparment Success!</title>
	<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>

	<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
	
	<h1 class="text-center">Success!</h1>
	
	<div class="well text-center">
				<h2>Department has been inserted</h2>
				Department ID: <%= request.getAttribute("departID")%>
				<br>Department Name: <%= request.getParameter("dName") %>
				<br> Department Location: <%= request.getParameter("location") %>
				</div>
</body>
</html>