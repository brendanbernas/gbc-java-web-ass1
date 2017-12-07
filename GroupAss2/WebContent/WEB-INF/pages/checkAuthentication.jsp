<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- if the session object user is empty, forward request to the login --%>
<c:if test="${empty sessionScope.user}">
	<%-- request.setAttribute("errorMessage", "Unauthorized Access: You must be logged in to view that page"); --%>
	<c:set var="errorMessage" scope="request" value="Unauthorized Access: You must be logged in to view that page"/>
	<jsp:forward page="/Login" /> 
</c:if>