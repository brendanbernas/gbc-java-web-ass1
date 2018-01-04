<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- making sure the user is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Attendance Landing</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="attendance" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
<div class="container">
    <div class="jumbotron">
        <h1>Attendance Landing</h1>
        <p>Click a button to go to its page</p>
    </div>
    <a class="btn-default btn-lg btn-primary" href="AttendanceForm">Attendance Entry</a>
    <a class="btn-default btn-lg btn-primary" href="AttendanceView">View Attendance</a>
</div>

</body>
