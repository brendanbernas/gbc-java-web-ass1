<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Report</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="reports" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
<h1 class="text-center">View Report</h1>
<div class="well text-center">
	<c:choose>
		<%-- both params missing, load empty form --%>
		<c:when test="${(empty param.depId) && (empty param.templateId)}">
			<jsp:include page="/WEB-INF/pages/report/view/empty-select-form.jsp" />
		</c:when>
		
		<%-- templateId missing, display template dropdown --%>
		<c:when test="${(not empty param.depId) && (empty param.templateId)}">
			<jsp:include page="/WEB-INF/pages/report/view/select-template-form.jsp" />
		</c:when>
		
		<%-- only depId missing, autofill departmentId from templateId --%>
		<c:when test="${(empty param.depId) && (not empty param.templateId)}">
			<jsp:include page="/WEB-INF/pages/report/view/select-report-form.jsp" />
		</c:when>
		
		<%-- both are filled display report dropdown --%>
		<c:when test="${(not empty param.depId) && (not empty param.templateId)}">
			<jsp:include page="/WEB-INF/pages/report/view/select-report-form.jsp" />
		</c:when>
	</c:choose>
</div>
<c:if test="${not empty param.reportId}">
	<jsp:include page="/WEB-INF/pages/report/view/view-or-edit-report-form.jsp" />
</c:if>
</body>
</html>