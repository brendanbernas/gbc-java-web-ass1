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
		    	<div class="col-md-6 text-right">
		    		<p class="text-muted" style="height: 20px;padding-top: 6px;padding-bottom: 6px;">${criteria.name}</p>
		    	</div>
		    	<div class="col-md-6 text-left">
		    		<%--
		    		param name would be evalSecXCritY
	    			example: section 1 criteria 2 would be evalSec1Crit2
	    			--%>
		    		<select name="evalSec${section.section}Crit${criteria.number}" class="form-control" required>
		    			<option value="" selected disabled>Evaluation</option>
			    		<c:forEach var="i" begin="1" end="${criteria.maxEvaluation}">
			    		<option>${i}</option>
			    		</c:forEach>
		    		</select>
		    		<span class="text-muted">/${criteria.maxEvaluation}</span>
		    	</div>
	    	</c:forEach>
		    </div>
		    <div class="col-md-6">
	    		<div class="form-group">
		    		<label for="comment">Comment:</label>
		    		<%-- comment param would be commentSecX, X  being the section number --%>
		    		<textarea name="commentSec${section.section}" class="form-control" maxlength="255" cols="50" rows="5" id="comment" style="min-width: 100%"></textarea>
	    		</div>
		    </div>
		  </div>
	</div>
</div>
</c:forEach>

<div class="row" style="margin-top: 10px;margin-bottom: 10px;">
	<input class="btn btn-default" type="submit" value="Continue">
	<a class="btn btn-default" href="">Cancel</a>
</div>