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
*Description: HTML for department insertion
*/ 
%>
<%@ page import="utility.ServletUtilities"%>
<%
	String error = "";
	boolean checkName = false;
	boolean checkLocation = false;

	//Checks if error had occur
	if(request.getAttribute("nameError") != null)
		checkName = (boolean)request.getAttribute("nameError");
	if(request.getAttribute("locationError") != null)
		checkLocation = (boolean)request.getAttribute("locationError");
	error = ServletUtilities.departmentErrors(checkName,checkLocation);
	//

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Enter Departments</title>
	<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>

	<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>

	<h1 class="text-center">Department Entry</h1>
	
	<div class="well text-center">  
					    <form action="DepartmentProcess"> 
					       <div class="form-group>" >
					            <label for="name">Department Name:</label> 
					            <input id="name" type="text" name="dName" value=<%= checkName ? request.getParameter("dName") : "" %>> <% if(checkName){out.print("<font size=\"3\" color=\"red\">*</font>");} %>
					        </div>
					     
					        <br>
					       <div class="form-group"> 
					            <label for="location">Department Location/Floor:</label> 
					            <input id="location" type="text" name="location" value=<%= checkLocation ? request.getParameter("location") : "" %> > <% if(checkLocation){out.print("<font size=\"3\" color=\"red\">*</font>");} %>
					        </div> 
					        <div class="form-group"> 
					            <input class="btn btn-default" type="submit">
					            <a class="btn btn-default">Cancel</a>  
					        </div> 
					        
					      <%= error %>
					        
							
					    </form>
					</div>";	
</body>
</html>
