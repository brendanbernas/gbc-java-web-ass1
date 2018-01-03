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
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="employees" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
<div class="container">
    <div class="jumbotron">
        <h1>Employee Listing</h1>
        <div>
        	<p>Quick filter</p>
        	<%ServletUtilities.departmentDropDown(request.getParameter("department")); %>
        </div>
        <div class = "form-group">
			<input class="btn btn-default" type="submit">
		</div>
    </div>
    <a class="btn-default btn-lg btn-primary" href="EmployeeForm">Employee Entry</a>
</div>
<div style="overflow-y:auto;">
	<%request.getAttribute("employeelist"); %>
  <table>
  <tr>
      <th>Last Name</th>
      <th>First Name</th>
      <th>Employee#</th>
      <th>Year Date</th>
      <th>Email</th>
      <th>Position</th>
    </tr>
    <tr>
      <td>Bernas</td>
      <td>Brendan</td>
      <td>100730728</td>
      <td>09/09/2012</td>
      <td>b.bernas@georgebrown.ca</td>
      <td>General</td>
    </tr>
  </table>
</div>
</body>
</body>
</html>