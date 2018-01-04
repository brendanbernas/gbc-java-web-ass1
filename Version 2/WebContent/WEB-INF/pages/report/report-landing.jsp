<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reports Landing</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="reports" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
<div class="container">
    <div class="jumbotron">
        <h1>Reports Landing</h1>
        <p>Click on a button to go to its page</p>
    </div>
    <a class="btn-default btn-lg btn-primary" href="ReportCreate">Create Report Template</a>
    <a class="btn-default btn-lg btn-primary" href="ReportEnter">Enter Report</a>
    <a class="btn-default btn-lg btn-primary" href="ReportView">View/Edit Report</a>
</div>
</body>
