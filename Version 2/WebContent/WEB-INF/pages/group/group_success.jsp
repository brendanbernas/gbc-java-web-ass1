<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- making sure the use is authenticated --%>

<%
/*Project: Pegasus Internal Web Application  
*Assignment: #1
*Author: Brendan Bernas, Albert Nguyen
*Student Number: 101012650, 100865315
*Date: Oct 19,2017
*Description: HTML for group insertion success
*/ 
%>
<%
	String groupName = "not found!";
	long groupID = -1;
	String departSelect = "";
	boolean inspect = false;
	if(request.getAttribute("inspect") != null){
		inspect = true;
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= inspect ? "Group Inspect" : "Group Entry Success!" %></title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
	

	
	<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
	
	<%= inspect ? "" : "<h1 class='text-center'>Success!</h1>" %>
			<div class="well text-center"> 
			
				<h2><%= inspect ? "Group Inspection" : "Group has been inserted" %></h2>
				<h4>ID - [<%= request.getAttribute("groupID")%>] / Group Name - <%= request.getAttribute("groupName")%></h4>
				<h4>ID - [<%= request.getAttribute("departID")%>] / Department Name - <%= request.getAttribute("departmentName")%></h4>
				
				<p>The following members are </p>   
					 
				<table class="table table-bordered table-striped">
				  <thead>
				    <tr>
				      <th>ID</th>
				      <th>First Name</th>
				      <th>Last Name</th>
				      <th>Department</th>
				      <th>Position</th>
				      <th>Email</th>
				    </tr>
				  </thead>
				  <tbody>
					<%= request.getAttribute("groupList")%>
				  </tbody>
				</table>
		  	</div>        
		
</body>
</html>