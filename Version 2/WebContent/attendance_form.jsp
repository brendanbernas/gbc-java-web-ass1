<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="utility.ServletUtilities"%>
<%@ page import='java.time.LocalDate' %> 
    
<%
boolean firstLoad = true;
String department = request.getParameter("dName");
boolean noDepartment = false;
String date = request.getParameter("date");
String error = "";

if(request.getAttribute("firstLoad")!=null){
	firstLoad = (boolean)request.getAttribute("firstLoad");
}
if(request.getAttribute("noDepartment")!=null){
	noDepartment = (boolean) request.getAttribute("noDepartment");
}

if(noDepartment){
	
	error = "<div class=\"alert alert-warning\"><p>";
	
	if(noDepartment)
		error += "Department cannot be empty ";
	
    error += "</p></div>";
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attendance Entry</title>
	<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>

	
	<h1 class="text-center">Attendance Entry</h1>
	quick filter
	<form action='AttendanceServlet'>
		<select id="departName" name="dName">
	            <% out.print(ServletUtilities.departmentDropDown(department));  %>
	    </select> 
	    
		<div class="form-group">
	            <button class="btn btn-default" value="true" name="search" type="submit">Search</button>
	    </div>
	    
		<div class="well text-center">  
						 
				<b>Date</b><br>	
				<input name="date" type="date" value="<%=firstLoad ? LocalDate.now():date%>" max="<%= LocalDate.now()%>">
				MM/dd/yyyy

				<table class="table table-bordered table-striped">
				  <thead>
				    <tr>
				      <th>ID</th>
				      <th>First Name</th>
				      <th>Last Name</th>
				      <th>Position</th>
				      <th>Email</th>
				      <th>Attended?</th>
				    </tr>
				  </thead>
				  <tbody>
					<%= firstLoad ? " " : request.getAttribute("employeeList") %>
				  </tbody>
				</table>
					
				<input name='submit' type="submit">
			<%= error %>
			
		</div>
	</form>
</body>
</html>