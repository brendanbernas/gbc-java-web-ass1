<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="container" style="margin-top: 10px;margin-bottom: 10px;">
		<div class="form-group">
	       	<label for="department">Department: </label>
	       	<select required class="form-control" name="depId" id="department" ${disableDepartment}>
	       		<option value="" selected disabled>Select Department</option>
	       		<jsp:useBean id="departmentList" class="utility.database.DepartmentDAO"/>
	       		<c:forEach items="${departmentList.allDepartments}" var="department">
	       			<option <c:if test="${paramValues.depId[0] == department.id}">selected</c:if> value="${department.id}">${department.name}</option>
	       		</c:forEach>
	       	</select>
	    </div>
       	<div class="form-group">
	       	<label for="reportType">Report Type: </label>
	       	<select required class="form-control" name="reportType" id="reportType" ${disableDepartment}>
				<option value="" selected disabled>Select Group Type</option>
				<option <c:if test="${paramValues.reportType[0] == 'group'}">selected</c:if> value="group">Group</option>
				<option <c:if test="${paramValues.reportType[0] == 'employee'}">selected</c:if> value="employee">Employee</option>
	       	</select>
       	</div>
       	
       	<c:if test="${not empty disableDepartment}">
       		<input type="hidden" name="depId" value="${param.depId}"/>
       		<input type="hidden" name="reportType" value="${param.reportType}"/>
       	</c:if>
	</div>
<c:if test="${empty disableDepartment}">
	<div class="form-group row" style="margin-top: 10px;margin-bottom: 10px;">
		<input class="form-control btn btn-default" type="submit" value="Continue">
		<a class="form-control btn btn-default" href="">Cancel</a>
	</div>
</c:if>