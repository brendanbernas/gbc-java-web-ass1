<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Enter Report</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="reports" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
<h1 class="text-center">Enter Report</h1>
<div class="well text-center">
	<form method="post" class="form-inline">
	<%-- if department id or report type are not disable the select department jsp page --%>
	<c:if test="${(not empty param.depId) && (not empty param.reportType)}">
		<c:set var="disableDepartment" value="disabled" scope="request"/>
	</c:if>
	<jsp:include page="/WEB-INF/pages/report/select-department-form.jsp" />
	
	<%-- if disableDepartment is set include other select-template form --%>
	<c:if test="${not empty disableDepartment}">
		<%-- if the templateId and employee/group is chosen disable the form --%>
		<c:if test="${(not empty param.templateId) && (not empty param.employeeOrGroup)}">
			<c:set var="disableTemplate" value="disabled" scope="request"/>
		</c:if>
		<jsp:include page="/WEB-INF/pages/report/select-template-form.jsp" />
	</c:if>
	
	<%-- if disableTemplate is set, include the fields necessary to enter report--%>
	<c:if test="${not empty disableTemplate}">
		<jsp:include page="/WEB-INF/pages/report/new-report-form.jsp" />
	</c:if>
	
	</form>
	
</div>
</body>
</html>