<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
String attendanceID = "";
if(request.getAttribute("attendanceID") !=null)
	attendanceID = (String) request.getAttribute("attendanceID"); 

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attendance Success!</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
	
	<h1 class="text-center">Success!</h1>
	
	<div class="well text-center">
				<h2>Department has been inserted</h2>
				Attendance ID: <%= attendanceID%>
				<br>Date <%=request.getParameter("date") %>
				<br>Department Name: <%= request.getParameter("dName") %>
			
				</div>
</body>
</html>