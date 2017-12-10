<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login: X Company</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<h1>Login: X Company</h1>
<body>
<div class="well">
    <form action="Authentication" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input id="username" type="text" name="user" value="">
        </div>
        <div clas="form-group">
            <label for="password">Password:</label>
            <input id="password" type="password" name="pass">
        </div>
        
 		 <div class="form-group">
        	<label for="remember">Remember me </label>
        	<input id="remember" name="remember" type="checkbox" class="form-check-input">
        	<input class="btn btn-default" type="submit">
        </div>    
	</form>
	<%-- if an error message is passed to this jsp it will display it here --%>
	<%--
		<% if(request.getAttribute("errorMessage") != null){ %>
		<div class="alert alert-warning">
	            <p>
	            request.getAttribute("errorMessage").toString()
	            </p>
	        </div>
	    <% } %>
    --%>
    <%-- JSLT if statement, checks if error message is sent, if it is it will display --%>
	<c:if test = "${requestScope.errorMessage != null}">
         <div class="alert alert-warning">
            <p> ${requestScope.errorMessage} </p>
        </div>
    </c:if>
</div>
</body>
</html>