<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="utility.ServletUtilities"%>
<%
/*Project: Pegasus Internal Web Application  
*Assignment: #1
*Author: Albert Nguyen
*Student Number:100865315
*Date: Jan 04,2017
*Description: HTML for attendance view
*/ 
%>
<%
String department = request.getParameter("dName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
	
	Quick Filter
	<form action='GroupViewServlet'>
	
		<select id="departName" name="dName">
	            <% out.print(ServletUtilities.departmentDropDown(department));  %>
	    </select> 
	
		<div class="form-group">
	            <button class="btn btn-default" value="true" name="search" type="submit">Search</button>
	     </div>
	
			<div class="well text-center"> 
			
				<h2>Group View</h2>
				<p>List of Groups below </p>   
					 
			
					<table class="table table-bordered table-striped">
					  <thead>
					    <tr>
					      <th>ID</th>
					      <th>Group Name</th>
					      <th>Inspect</th>
					
					    </tr>
					  </thead>
					  <tbody>
						<%= request.getAttribute("groupList")%>
					  </tbody>
					</table>
				
		  	</div>  
		  	
	</form> 
</body>
</html>