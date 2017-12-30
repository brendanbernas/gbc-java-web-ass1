<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%-- making sure the use is authenticated --%>
<jsp:include page="/WEB-INF/pages/include/checkAuthentication.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Report Template</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%-- getting navigation bar --%>
<c:set var="reports" value="active" scope="request"/>
<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>
<h1 class="text-center">Create Report Template</h1>
<form action="ReportTemplateEntry" method="post">
	<!-- errors if exist -->
	<c:if test="${not empty errSummaryList}">
		<div class="alert alert-danger">
			<ul>
			<c:forEach items="${errSummaryList}" var="message">
		  	<li>${message}</li>
		  	</c:forEach>
		  	</ul>
		</div>
	</c:if>
	
	<!-- details section -->
	<div class="well text-center">
		<p class="text-left">1. Details:</p>
        <div class="form-group row">
            <span style="<c:if test="${errrName}">color:red;</c:if>">	
            	<label for="name">Report Template: </label> <input id="name" type="text" name="rName" value ="${paramValues.rName[0]}">
            </span>
            <%--get current date --%>
        	<jsp:useBean id="currDate" class="java.util.Date"/>
            <label for="date">Date</label> <input id="date" type="text" name="location" value ="<fmt:formatDate value="${currDate}" pattern="dd/MM/yyyy"/>" disabled>
        </div>
        <div class="form-group row">
        	<%--load all departments --%>
        	<jsp:useBean id="departmentList" class="utility.database.DepartmentDAO"/>
        	<span style="<c:if test="${errdepId}">color:red;</c:if>">
	        	<label for="department">Department: </label>
	        	<select name="depId" id="department">
	        		<option selected disabled>Select Department</option>
	        		<c:forEach items="${departmentList.allDepartments}" var="department">
	        			<option <c:if test="${paramValues.depId[0] == department.id}">selected</c:if> value="${department.id}">${department.name}</option>
	        		</c:forEach>
	        	</select>
        	</span>
        </div>
	</div>
	<!-- section 1 -->
	<div class="well text-center">
		<span style="<c:if test="${errs1name}">color:red;</c:if>">
			<p class="text-left">2. Section I: <input type="text" name="s1name" value="${paramValues.s1name[0]}"></p>
		</span>
		<div class="form-group row">
            <span style="<c:if test="${errs1crit1}">color:red;</c:if>">
            	<label for="s1crit1">Criteria 1: </label> <input id="s1crit1" type="text" name="s1crit1" value ="${paramValues.s1crit1[0]}">
            </span>
            <span style="<c:if test="${errs1crit1max}">color:red;</c:if>">
	            Maximum: <select name="s1crit1max">
	            	<option selected disabled>&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${paramValues.s1crit1max[0] == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
		<div class="form-group row">
            <span style="<c:if test="${errs1crit2}">color:red;</c:if>">
            	<label for="s1crit2">Criteria 2: </label> <input id="s1crit2" type="text" name="s1crit2" value ="${paramValues.s1crit2[0]}">
            </span>
            <span style="<c:if test="${errs1crit2max}">color:red;</c:if>">
	            Maximum: <select name="s1crit2max">
	            	<option selected value="0">&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${param.s1crit2max == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
		<div class="form-group row">
            <span style="<c:if test="${errs1crit3}">color:red;</c:if>">
            	<label for="s1crit3">Criteria 3: </label> <input id="s1crit3" type="text" name="s1crit3" value ="${paramValues.s1crit3[0]}">
            </span>
            <span style="<c:if test="${errs1crit3max}">color:red;</c:if>">
	            Maximum: <select name="s1crit3max">
	            	<option selected value="0">&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${paramValues.s1crit3max[0] == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
        <div class="form-group row">
            <span style="<c:if test="${errs1crit4}">color:red;</c:if>">
            	<label for="s1crit4">Criteria 4: </label> <input id="s1crit4" type="text" name="s1crit4" value ="${paramValues.s1crit4[0]}">
            </span>
            <span style="<c:if test="${errs1crit4max}">color:red;</c:if>">
	            Maximum: <select name="s1crit4max">
	            	<option selected value="0">&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${paramValues.s1crit4max[0] == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
        <div class="form-group row">
            <span style="<c:if test="${errs1crit5}">color:red;</c:if>">
            	<label for="s1crit5">Criteria 5: </label> <input id="s1crit5" type="text" name="s1crit5" value ="${paramValues.s1crit5[0]}">
            </span>
            <span style="<c:if test="${errs1crit5max}">color:red;</c:if>">
	            Maximum: <select name="s1crit5max">
	            	<option selected value="0">&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${paramValues.s1crit5max[0] == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
	</div>
	
	<!-- section 2 -->
	<div class="well text-center">
		<span style="<c:if test="${errs2name}">color:red;</c:if>">
			<p class="text-left">3. Section II: <input type="text" name="s2name" value="${paramValues.s2name[0]}"></p>
		</span>
		<div class="form-group row">
            <span style="<c:if test="${errs2crit1}">color:red;</c:if>">
            	<label for="s2crit1">Criteria 1: </label> <input id="s2crit1" type="text" name="s2crit1" value ="${paramValues.s2crit1[0]}">
            </span>
            <span style="<c:if test="${errs2crit1max}">color:red;</c:if>">
	            Maximum: <select name="s2crit1max">
	            	<option selected disabled>&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${paramValues.s2crit1max[0] == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
		<div class="form-group row">
            <span style="<c:if test="${errs2crit2}">color:red;</c:if>">
            	<label for="s2crit2">Criteria 2: </label> <input id="s2crit2" type="text" name="s2crit2" value ="${paramValues.s2crit2[0]}">
            </span>
            <span style="<c:if test="${errs2crit2max}">color:red;</c:if>">
	            Maximum: <select name="s2crit2max">
	            	<option selected value="0">&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${paramValues.s2crit2max[0] == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
		<div class="form-group row">
            <span style="<c:if test="${errs2crit3}">color:red;</c:if>">
            	<label for="s2crit3">Criteria 3: </label> <input id="s2crit3" type="text" name="s2crit3" value ="${paramValues.s2crit3[0]}">
            </span>
            <span style="<c:if test="${errs2crit3max}">color:red;</c:if>">
	            Maximum: <select name="s2crit3max">
	            	<option selected value="0">&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${paramValues.s2crit3max[0] == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
	</div>
	
	<!-- section 3 -->
	<div class="well text-center">
		<span style="<c:if test="${errs3name}">color:red;</c:if>">
			<p class="text-left">4. Section III: <input type="text" name="s3name" value="${paramValues.s3name[0]}"></p>
		</span>
		<div class="form-group row">
            <span style="<c:if test="${errs3crit1}">color:red;</c:if>">
            	<label for="s3crit1">Criteria 1: </label> <input id="s3crit1" type="text" name="s3crit1" value ="${paramValues.s3crit1[0]}">
            </span>
            <span style="<c:if test="${errs3crit1max}">color:red;</c:if>">
	            Maximum: <select name="s3crit1max">
	            	<option selected disabled>&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${paramValues.s3crit1max[0] == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
		<div class="form-group row">
            <span style="<c:if test="${errs3crit2}">color:red;</c:if>">
            	<label for="s3crit2">Criteria 2: </label> <input id="s3crit2" type="text" name="s3crit2" value ="${paramValues.s3crit2[0]}">
            </span>
            <span style="<c:if test="${errs3crit2max}">color:red;</c:if>">
	            Maximum: <select name="s3crit2max">
	            	<option selected value="0">&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${paramValues.s3crit2max[0] == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
		<div class="form-group row">
            <span style="<c:if test="${errs3crit3}">color:red;</c:if>">
            	<label for="s3crit3">Criteria 3: </label> <input id="s3crit3" type="text" name="s3crit3" value ="${paramValues.s3crit3[0]}">
            </span>
            <span style="<c:if test="${errs3crit3max}">color:red;</c:if>">
	            Maximum: <select name="s3crit3max">
	            	<option selected value="0">&mdash;</option>
	            	<c:forEach begin="1" end="5" step="1" varStatus="loop">
	            		<option <c:if test="${paramValues.s3crit3max[0] == loop.count}">selected</c:if> value="${loop.count}">${loop.count}</option>
	            	</c:forEach>
	            </select>
            </span>
        </div>
	</div>
	
	<div class="form-group text-center">
		<input class="btn btn-default" type="submit">
		<a class="btn btn-default" href="?firstLoad=true">Cancel</a>
	</div>
</form>

</body>
</html>