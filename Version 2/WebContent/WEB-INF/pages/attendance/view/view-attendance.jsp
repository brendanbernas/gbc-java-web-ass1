<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- making sure the user is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Attendance</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="attendance" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
ok
</body>
</html>