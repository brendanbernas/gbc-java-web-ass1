<%@page import="domain.ReportTemplateSectionCriteria"%>
<%@page import="domain.ReportCriteriaEvaluation"%>
<%@page import="domain.Report"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<div class="well text-center container">
<form id="reportForm" class="form-inline" method="post">
<%
try{
	int reportId = Integer.parseInt(request.getParameter("reportId"));

	request.setAttribute("selectedReport", new utility.database.ReportDAO().getFullReport(reportId));
} catch (NumberFormatException e) {
	e.printStackTrace();
}
%>
	<input type="hidden" name="reportId" value="${selectedReport.id}">
	<input type="hidden" name="templateId" value="${selectedReport.template.id}">
	<input type="hidden" name="depId" value="${selectedReport.template.departmentId }" >
	<c:if test="${not empty insertFailure }">
	<div class="alert alert-danger">${insertFailure}</div>
	</c:if>
	<c:if test="${not empty insertSuccess }">
	<div class="alert alert-success">${insertSuccess}</div>
	</c:if>
	<div class="panel panel-default">
		<div class="panel-heading text-left">Details</div>
		<div class="panel-body">
			<table class="table table-bordered">
				<tr>
					<td class="text-right">Report Template</td>
					<td class="text-left"><strong>${selectedReport.template.name}</strong></td>
				</tr>
				<tr>
					<td class="text-right">Report Title</td>
					<td class="text-left"><strong>${selectedReport.name}</strong></td>
				</tr>
				<tr>
					<td class="text-right">Report Date</td>
					<td class="text-left"><strong><fmt:formatDate pattern = "yyyy-MM-dd"  value = "${selectedReport.date.time}" /></strong></td>
				</tr>
				<tr>
					<td class="text-right">Department</td>
					<td class="text-left"><strong>${selDepName}</strong></td>
				</tr>
			</table>
		</div>
	</div>
<c:forEach items="${selectedReport.sectionEvaluationList}" var="sectionEval" >
	<div class="panel panel-default">
		<div class="panel-heading text-left">Section ${sectionEval.sectionTemplate.section}: ${sectionEval.sectionTemplate.name}</div>
		<div class="panel-body">
			<c:forEach items="${sectionEval.sectionTemplate.sectionCriteria}" var="critTemplate">
			<%-- count the total of max evaluations --%>
			<c:set var="evalTotal" value="${evalTotal + critTemplate.maxEvaluation}"/>
			<div class="col-md-6">
				<div class="col-md-6 text-right">
					<p class="text-muted" style="height: 20px;padding-top: 6px;padding-bottom: 6px;">${critTemplate.name}</p>
				</div>
				<div class="col-md-6">
					<select onchange="calculateTotal()" disabled class="form-control criteria disablable" name="evalSec${sectionEval.sectionTemplate.section}Crit${critTemplate.number}">
						<c:forEach var="i" begin="0" end="${critTemplate.maxEvaluation}" step="1">
						<option 
						<%
						Report report = (Report)request.getAttribute("selectedReport");
						java.util.List<ReportCriteriaEvaluation> critEvals = report.getCriteriaEvaluationList();
						Integer i = ((Integer)(pageContext.findAttribute("i")));
						ReportTemplateSectionCriteria currCritTemplate = ((ReportTemplateSectionCriteria) (pageContext.findAttribute("critTemplate")));
						for(ReportCriteriaEvaluation eval : critEvals){
							//if evaluation object's template is equal to current template and grade, show selected
							if(eval.getCriteriaTemplate().equals(currCritTemplate) &&  eval.getGrade() == i){
								out.print("selected");
							}
						}
						%>				
						 value="${i}" > ${i}</option>
						</c:forEach>
					</select>
					<span class="text-muted">/${critTemplate.maxEvaluation}</span>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<textarea disabled required name="commentSec${sectionEval.sectionTemplate.section}" class="form-control disablable" maxlength="255" cols="50" rows="5" id="comment" style="min-width: 100%">${sectionEval.comment}</textarea>
				</div>
			</div>
			</c:forEach>
			
		</div>
	</div>
</c:forEach>
<div class="row" style="margin-top: 10px;margin-bottom: 10px;">
	<p> Total: <span class="btn btn-default btn-lg disabled" id="totalEval">0</span> / ${evalTotal}</p>
</div>
<div class="row" style="margin-top: 10px;margin-bottom: 10px;">
	<input id="btnEdit" type="button" class="btn btn-default" onclick="enableForm()" value="Edit">
	<input id="btnSubmit" type="submit" class="btn btn-default" value="Save Any Changes" style="display: none;">
	<a id="btnCancel" class="btn btn-default" href="" style="display: none;">Cancel</a>
</div>

<%-- calculate the total from the evaluations and display it --%>
<script>
function calculateTotal(){
	var crits = document.getElementsByClassName("criteria");
	var total = 0;
	for(var i = 0; i < crits.length; i++){
		if(!crits[i].value == "")
			total += parseInt(crits[i].value);
	}
	document.getElementById("totalEval").textContent = total;
}
calculateTotal();

function enableForm(){
	var elements = document.getElementsByClassName("disablable");
	for(var i = 0; i < elements.length; i++){
		elements[i].disabled = false;
	}
	
	document.getElementById("btnEdit").style.display = "none";
	document.getElementById("btnSubmit").style.display = "inline";
	document.getElementById("btnCancel").style.display = "inline";
	document.getElementById("reportForm").action = "ReportEvaluationUpdate";
}
</script>

</div>
</form>