<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="from-group row" style="margin-top: 10px;margin-bottom: 10px;">
		<label for="templateId">Report Template: </label>
		<%--disable template will disable this HTML select element if it is set to "disabled" --%>
		<select required name="templateId" id="templateId" class="form-control" ${disableTemplate}>
			<option disabled selected value="">Report Template</option>
			<%
			//getting HashMap of ReportTemplates (only id and name)
			//displaying it as dropdown list
			java.util.HashMap<Integer, String> templateMap = new utility.database.ReportTemplateDAO().getTemplateNamesAndIdsByDepartment(Integer.parseInt(request.getParameter("depId")));
			for(java.util.Map.Entry<Integer, String> entry: templateMap.entrySet()){
				//if the templateId param matches the key, make the HTML option selected
				String selected = "";
				if(request.getParameter("templateId")!= null && request.getParameter("templateId").equals(entry.getKey().toString())){
					selected = "selected";
				}
				out.println("<option "+selected+" value='"+entry.getKey()+"'>"+entry.getValue()+"</option>");
			}
			if(templateMap.isEmpty())
				out.println("<option disabled value=''>No templates exist for department</option>");
			%>
		</select>
		
		<label for="employeeOrGroup">
			<c:choose>
				<c:when test="${param.reportType == 'group'}">
					Group
				</c:when>
				<c:when test="${param.reportType == 'employee'}">
					Employee
				</c:when>
			</c:choose>
		</label>
		<%--disable template will disable this HTML select element if it is set to "disabled" --%>
		<select required name="employeeOrGroup" id="employeeOrGroup" class="form-control" ${disableTemplate}>
			<option disabled selected value="">
				<c:choose>
					<c:when test="${param.reportType == 'group'}">
						Group
					</c:when>
					<c:when test="${param.reportType == 'employee'}">
						Employee
					</c:when>
				</c:choose>
				Selection
			</option>
			<%
			//getting HashMap of ReportTemplates (only id and name)
			//displaying it as dropdown list
			java.util.HashMap<Integer, String> map = null;
			if(request.getParameter("reportType") != null && request.getParameter("reportType").equals("group"))
				map = new utility.database.GroupDAO().getGroupNamesAndIdsByDepartment(Integer.parseInt(request.getParameter("depId")));
			else if(request.getParameter("reportType") != null && request.getParameter("reportType").equals("employee"))
				map = new utility.database.EmployeeDAO().getEmployeeNamesAndIdsByDepartment(Integer.parseInt(request.getParameter("depId")));
			for(java.util.Map.Entry<Integer, String> entry: map.entrySet()){
				//if the templateId param matches the key, make the HTML option selected
				String selected = "";
				if(request.getParameter("employeeOrGroup")!= null && request.getParameter("employeeOrGroup").equals(entry.getKey().toString())){
					selected = "selected";
				}
				out.println("<option "+selected+" value='"+entry.getKey()+"'>"+entry.getValue()+"</option>");
			}
			if(map.isEmpty())
				out.println("<option disabled value=''>No employee/group exist for department</option>");
			%>
		</select>
		
		<%-- if forms are disabled, set params as hidden form elements (so they will still be sent)--%>
		<c:if test="${not empty disableTemplate}">
       		<input type="hidden" name="templateId" value="${param.templateId}"/>
       		<input type="hidden" name="employeeOrGroup" value="${param.employeeOrGroup}"/>
       	</c:if>
	</div>
<c:if test="${empty disableTemplate}">
	<div class="form-group row" style="margin-top: 10px;margin-bottom: 10px;">
		<input class="btn btn-default" type="submit" value="Continue">
		<a class="btn btn-default" href="">Cancel</a>
	</div>
</c:if>