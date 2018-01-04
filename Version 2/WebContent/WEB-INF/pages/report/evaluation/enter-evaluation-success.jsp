<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Successfully Entered Report</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="reports" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
<h1 class="text-center">Successfully Entered Report</h1>
<div class="alert alert-success text-center lead">
	<fmt:formatDate value="${insertedReport.date.time}" var="formattedDate" type="date" pattern="yyyy-MM-dd" />
	<p>Successfully entered report "${insertedReport.name}" for the date: ${formattedDate}</p>
	<a href="ReportEnter" class="btn btn-default" style="margin-top: 15px;">Enter another report</a>
	<a href="ReportView" class="btn btn-default" style="margin-top: 15px;">View reports</a>
</div>
</body>
</html>