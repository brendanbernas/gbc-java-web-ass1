<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form method="post" class="form-inline">
	<div class="container">
		<div class="form-group">
			<%-- select template --%>
			<jsp:useBean id="templateDAO" class="utility.database.ReportTemplateDAO"/>
			<label for="templateId">Report Template</label>
			<select class="form-control" id="templateId" name="templateId" onchange="this.form.submit();">
				<option selected disabled>Select Template</option>
				<% 
				try{
					int depId = Integer.parseInt(request.getParameter("depId"));
					//for each templateMap display a dropdown option
					int count = 0;
					for(java.util.Map.Entry<Integer, String> entry : new utility.database.ReportTemplateDAO().getTemplateNamesAndIdsByDepartment(depId).entrySet()){
						out.println("<option value='"+entry.getKey()+"'>"+entry.getValue()+"</option>");
						count++;
					}
					if(count == 0)
						out.println("<option disabled>No templates exist for department</option>");
				}catch(NumberFormatException e){
					//depId is not a number
					e.printStackTrace();
				}
				%>
			</select>
			
			<%-- show selected department id --%>
			<jsp:useBean id="departmentDAO" class="utility.database.DepartmentDAO"/>
			<label for="departmentId">Department</label>
			<select disabled class="form-control" id="depId" name="templateId" onchange="this.form.submit();">
				<c:forEach items="${departmentDAO.allDepartments}" var="department" >
					<%-- show selected department--%>
					<c:set var="idAsString" value="${department.id}"/>
					<c:if test="${param.depId == idAsString}">
						<option selected value="${param.depId}">${department.name}</option>
						<c:set var="hiddenDepId" value="${department.id}"/>
					</c:if>
				</c:forEach>
			</select>
			<input type="hidden" name="depId" value="${hiddenDepId}"/>
		</div>
		<a class="btn btn-default" href="">Cancel</a>
	</div>
</form>