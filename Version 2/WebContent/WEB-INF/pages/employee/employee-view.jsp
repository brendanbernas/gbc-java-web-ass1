<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import = "utility.*" %>
<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<%--
Project: Pegasus Internal Web Application  
Assignment: #2
Author: Piotr Grabowski
Student Number: 100730728
Date: Dec 30,2017
Description: HTML for viewing all employees 
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee View</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="employees" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>

<div class="well text-center"> 
			
	<h2>Employee Listing</h2>
	<p>Quick filter</p>
	<form method = "post" action = "EmployeeViewProcess">
		<div class="form-group"> 
		<label for="department">Department:</label>
		<select required name = "department">
		<%out.print(ServletUtilities.departmentDropDown(request.getParameter("department"))); %>
		</select>
		<input class="btn btn-default" value='Search' name='search' type="submit">
		</div>
	</form>
	<p>List of Employees below </p>
				
	<table class="table table-bordered table-striped">
		<thead>
		<tr>
			<th>Last Name</th>
			<th>First Name</th>
			<th>Year Date</th>
			<th>Email</th>
			<th>Position</th>		
		</tr>
		</thead>
		<tbody>
		<%= request.getAttribute("employeelist")%>
		</tbody>
	</table>
	</div>
<center>			    
<a class="btn-default btn-lg btn-primary" href="Landing">Home</a>
</center>
</body>
</html>