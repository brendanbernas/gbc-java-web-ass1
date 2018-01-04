<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<form method="post" class="form-inline" action="ReportView">
	<div class="container">
		<div class="form-group">
			<%-- show selected template --%>
			<jsp:useBean id="templateDAO" class="utility.database.ReportTemplateDAO"/>
			<label for="templateId">Report Template</label>
			<select disabled class="form-control" id="templateId" name="templateId" onchange="this.form.submit();">
				<% 
				try{
					int templateId = Integer.parseInt(request.getParameter("templateId"));
					//for each templateMap display a dropdown option
					for(java.util.Map.Entry<Integer, String> entry : new utility.database.ReportTemplateDAO().getAllTemplateNamesAndIds().entrySet()){
						if(templateId == entry.getKey())
							out.println("<option selected value='"+entry.getKey()+"'>"+entry.getValue()+"</option>");
					}
				}catch(NumberFormatException e){
					//templateId is not a number
					e.printStackTrace();
				}
				%>
			</select>
			<input type="hidden" name="templateId" value="${param.templateId}"/>
			
			<%-- show selected department id --%>

			<c:choose>
				<%-- if depId is empty must generate it from the selected template --%>
				<c:when test="${empty param.depId}">
					<label for="departmentId">Department</label>
					<select disabled class="form-control" id="depId" name="templateId" onchange="this.form.submit();">
						<%
						try{
							int templateId = Integer.parseInt(request.getParameter("templateId"));
							domain.Department dep = new utility.database.DepartmentDAO().getDepartmentByTemplateId(templateId);
							if(dep != null){
								out.println("<option selected value='"+dep.getId()+"'>"+dep.getName()+"</option>");
								//set this attribute for use in EL line 66
								request.setAttribute("hiddenDepId", dep.getId());
							}
						}catch(NumberFormatException e){
							//templateId is not a number
							e.printStackTrace();
						}
						%>
					</select>
				</c:when>
				
				<%-- otherwise depId was not empty, show the selected department--%>
				<c:otherwise>
					<jsp:useBean id="departmentDAO" class="utility.database.DepartmentDAO"/>
					<label for="departmentId">Department</label>
					<select disabled class="form-control" id="depId" name="templateId" onchange="this.form.submit();">
						<c:forEach items="${departmentDAO.allDepartments}" var="department" >
							<%-- show selected department--%>
							<c:set var="idAsString" value="${department.id}"/>
							<c:if test="${param.depId == idAsString}">
								<option selected value="${param.depId}">${department.name}</option>
								<c:set var="selDepName" value="${department.name}" scope="request"/>
								<c:set var="hiddenDepId" value="${department.id}"/>
							</c:if>
						</c:forEach>
					</select>
				</c:otherwise>
			</c:choose>
			<input type="hidden" name="depId" value="${hiddenDepId}"/>
		</div>
		
		<%-- generate list of reports --%>
		<label for="reportId">Report</label>
		<select class="form-control" id="reportId" name="reportId" onchange="this.form.submit();">
			<option selected disabled value="">Select report</option>
		<%
		try{
			int reportId;
			//if reportId param exists, set it to what was sent, otherwise it is -1
			try{
				reportId = Integer.parseInt(request.getParameter("reportId"));
			}catch(NumberFormatException e){
				reportId = -1;
			}
			int templateId = Integer.parseInt(request.getParameter("templateId"));
			boolean hasReports = false;
			for(domain.Report report : new utility.database.ReportDAO().getReportHeadersByTemplateId(templateId)){
				hasReports = true;
				//formatting date
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
				String strDate = df.format(report.getDate().getTime());
				String selected = "";
				//if the reportId param exists, select that report in the dropdown
				if(reportId != -1 && reportId == report.getId())
					selected = "selected";
				out.println("<option "+selected+" value='"+report.getId()+"'>"+strDate + " - " + report.getName() +"</option>");		
			}
			if(!hasReports){
				out.println("<option disabled>No reports found for the selected template</option>");		
			}
		}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
		%>
		</select>
		
		<a class="btn btn-default" href="">Cancel</a>
	</div>
</form>