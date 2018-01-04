<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>

	<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
	
			<div class="well text-center"> 
			
				<h2>Department View</h2>

				
				<p>List of Departments below </p>   
					 
				<table class="table table-bordered table-striped">
				  <thead>
				    <tr>
				      <th>ID</th>
				      <th>Department Name</th>
				      <th>Location</th>
				
				    </tr>
				  </thead>
				  <tbody>
					<%= request.getAttribute("departmentList")%>
				  </tbody>
				</table>
		  	</div>        
		
</body>
</html>