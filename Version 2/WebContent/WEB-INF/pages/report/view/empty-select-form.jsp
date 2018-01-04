<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form method="post" class="form-inline">
	<%-- select department id --%>
	<div class="container">
	<div class="form-group">
		<jsp:useBean id="templateDAO" class="utility.database.ReportTemplateDAO"/>
		<label for="templateId">Report Template</label>
		<select class="form-control" id="templateId" name="templateId" onchange="this.form.submit();">
			<option selected disabled>Select Template</option>
			<c:forEach items="${templateDAO.allTemplateNamesAndIds}" var="templateMap" >
			<%-- generate dropdown for template --%>
			<option value="${templateMap.key}">${templateMap.value}</option>
			</c:forEach>
		</select>
		
		<jsp:useBean id="departmentDAO" class="utility.database.DepartmentDAO"/>
		<label for="departmentId">Department</label>
		<select class="form-control" id="depId" name="depId" onchange="this.form.submit();">
			<option selected disabled>Select Department</option>
			<c:forEach items="${departmentDAO.allDepartments}" var="department" >
			<%-- generate dropdown for department--%>
			<option value="${department.id}">${department.name}</option>
			</c:forEach>
		</select>
	</div>
	</div>
</form>