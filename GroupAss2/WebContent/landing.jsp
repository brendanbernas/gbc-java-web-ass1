<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Landing</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>

<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/checkAuthentication.jsp"/>
<%-- getting navigation bar --%>
<%-- <c:set var="departments" value="active" scope="request"/> --%>
<jsp:include page="/WEB-INF/pages/navigationBar.jsp"/>

<div class="container">
    <div class="jumbotron">
        <h1>Welcome ${sessionScope.user}</h1>
        <p>Click one navigation buttons in the navbar above or one of the buttons below to go its landing page</p>
    </div>
    <a class="btn-default btn-lg btn-primary" href="Departments">Departments</a>
    <a class="btn-default btn-lg btn-primary" href="Employees">Employees</a>
    <a class="btn-default btn-lg btn-primary" href="Group">Group</a>
    <a class="btn-default btn-lg btn-primary" href="Reports">Reports</a>
    <a class="btn-default btn-lg btn-primary" href="Attendance">Attendance</a>
</div>
</body>
