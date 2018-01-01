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
Description: HTML for employee insertion
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<link href="css/bootstrap.css" rel="stylesheet">
<title>Employee Form</title>
</head>
<body>
	<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
	
	<h1 class="text-center">Employee Entry</h1>
	
	<div class = "well">
		<form action = "EmployeesProcess" method = "POST">
			<div class = "form-group">
				<label for="firstname">First Name:</label>
				<input id="firstname" name="firstname" type = "text" value="${param.firstname}">
			</div>
			<div class = "form-group">
				<label for="lastname">Last Name:</label>
				<input id="lastname" name="lastname" type = "text" value="${param.lastname}">
			</div>
			<div class = "form-group">
				<label for="employeenum">Employee#</label>
				<input id="employeenum" name="employeenum" type = "text" value="${param.employeenum}">
			</div>
			<div class = "form-group">
				<label for="email">Email:</label>
				<input id="email" name="email" type = "text" value="${param.email}">
			</div>
			<div class = "form-group">
				<label for="hiredyear">Hired Year:</label>
				<c:if test = "${requestScope.yearhired != null}">
					<% int inputYear = Integer.parseInt(request.getParameter("hiredyear"));%>
					<% out.println(ServletUtilities.generateHtmlForYear(inputYear,50)); %>
				</c:if>
				<c:if test = "${requestScope.yearhired == null}">
					<% out.println(ServletUtilities.generateHtmlForYear(0,50)); %>
				</c:if>
			</div>
			<div class = "form-group">
				<label for="jobposition">Job Position:</label>
				<c:if test = "${requestScope.jobposition != null}">
					<% out.println(ServletUtilities.generateHtmlForPositions(request.getParameter("jobposition"))); %>
				</c:if>
				<c:if test = "${requestScope.jobposition == null}">
					<% out.println(ServletUtilities.generateHtmlForPositions("empty")); %>
				</c:if>
			</div>
			<div class = "form-group">
				<input class="btn btn-default" type="submit">
			</div>
		</form>
	</div>
	<%-- JSLT if statement, checks if error message is sent, if it is it will display --%>
	<c:if test = "${requestScope.error != null}">
         <div class="alert alert-warning">
            <p> ${error} </p>
        </div>
    </c:if>
</body>
</html>