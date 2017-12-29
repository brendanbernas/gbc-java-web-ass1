<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Landing</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="employees" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
<div class="container">
    <div class="jumbotron">
        <h1>Employee Landing</h1>
        <p>Click a button to go to its page</p>
    </div>
    <a class="btn-default btn-lg btn-primary" href="EmployeeForm">Employee Entry</a>
</div>
</body>
