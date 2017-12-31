<%@page import="utility.database.ReportTemplateDAO"%>
<%@page import="domain.ReportTemplate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="from-group row" style="margin-top: 10px;margin-bottom: 10px;">
	<label for="reportTitle">Report Title: </label>
	<input class="form-control" name="reportTitle" id="reportTitle" value ="${param.reportTitle}" required>
	<%--get current date --%>
	<label for="date">Date</label> 
	<input type="date" id="date" type="text" name="date" value ="${param.date}" required>
</div>

<%
//getting template
ReportTemplate template = new ReportTemplateDAO().getFullReportTemplate(Integer.parseInt(request.getParameter("templateId")));
request.setAttribute("reportTemplate", template);
%>

<c:forEach items="${reportTemplate.templateSections}" var="section">
<div class="panel panel-default">
	<div class="panel-heading text-left">
		<strong>Section ${section.section}: ${section.name}</strong>
	</div>
	<div class="panel-body">
		<div class="row">
		    <div class="col-md-6">
	    	<c:forEach items="${section.sectionCriteria}" var="criteria">
	    		<p>${criteria.name} - ${criteria.maxEvaluation}</p>
	    	</c:forEach>
		    </div>
		    <div class="col-md-6">
	    	<span class="pull-right">
	    		<div class="form-group">
		    		<label for="comment">Comment:</label>
		    		<textarea class="form-control" rows="5" id="comment" style="width:auto;"></textarea>
	    		</div>
	    	</span>
		    </div>
		  </div>
	</div>
</div>
</c:forEach>

<div class="row" style="margin-top: 10px;margin-bottom: 10px;">
	<input class="btn btn-default" type="submit" value="Continue">
	<a class="btn btn-default" href="">Cancel</a>
</div>