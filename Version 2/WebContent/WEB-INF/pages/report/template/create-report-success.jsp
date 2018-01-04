<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Successfully Entered Report Template</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="reports" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
<h1 class="text-center">Successfully Entered Report Template</h1>
<div class="alert alert-success text-center lead">
	<p>Successfully entered report template "${insertedTemplate.name}"</p>
	<a href="ReportEnter" class="btn btn-default" style="margin-top: 15px;">Enter Report</a>
	<a href="ReportCreate" class="btn btn-default" style="margin-top: 15px;">Enter Another Template</a>
</div>
</body>
</html>