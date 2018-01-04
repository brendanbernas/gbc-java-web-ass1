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
<form action="" method="get">
	<div class="well text-center">
		<p class="text-left">1. Details:</p>
        <div class="form-group row">
            <label for="name">Report Template: </label> <input id="name" type="text" name="rName" value ="">
            <%--get current date --%>
        	<jsp:useBean id="currDate" class="java.util.Date"/>
            <label for="date">Date</label> <input id="date" type="text" name="location" value ="<fmt:formatDate value="${currDate}" pattern="dd/MM/yyyy"/>" disabled>
        </div>
        <div class="form-group row">
        	<%--load all departments --%>
        	<jsp:useBean id="departmentList" class="utility.database.DepartmentDAO"/>
        	<label for="department">Department: </label>
        	<select name="depId" id="department">
        		<option selected disabled>Select Department</option>
        		<c:forEach items="${departmentList.allDepartments}" var="department">
        			<option value="${department.id}">${department.name}</option>
        		</c:forEach>
        	</select>
        </div>
	</div>
	<!-- section 1 -->
	<div class="well text-center">
		<p class="text-left">2. Section I: <input type="text" name="s1name"></p>
		<div class="form-group row">
            <label for="s1crit1">Criteria 1: </label> <input id="s1crit1" type="text" name="s1crit1" value ="">
            Maximum: <select name="s1crit1max">
            	<option selected disabled>&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>
        </div>
        <div class="form-group row">
            <label for="s1crit2">Criteria 2: </label> <input id="s1crit2" type="text" name="s1crit2" value ="">
            Maximum: <select name="s1crit2max">
            	<option selected>&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>
        </div>
        <div class="form-group row">
            <label for="s1crit3">Criteria 3: </label> <input id="s1crit3" type="text" name="s1crit3" value ="">
            Maximum: <select name="s1crit3max">
            	<option selected>&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>
        </div>
        <div class="form-group row">
            <label for="s1crit4">Criteria 4: </label> <input id="s1crit4" type="text" name="s1crit4" value ="">
            Maximum: <select name="s1crit4max">
            	<option selected>&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>
        </div>
        <div class="form-group row">
            <label for="s1crit5">Criteria 5: </label> <input id="s1crit5" type="text" name="s1crit5" value ="">
            Maximum: <select name="s1crit5max">
            	<option selected >&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>   
        </div>
	</div>
	
	<!-- section 2 -->
	<div class="well text-center">
		<p class="text-left">2. Section II: <input type="text" name="s2name"></p>
		<div class="form-group row">
            <label for="s2crit1">Criteria 1: </label> <input id="s2crit1" type="text" name="s2crit1" value ="">
            Maximum: <select name="s2crit1max">
            	<option selected disabled>&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>
        </div>
        <div class="form-group row">
            <label for="s2crit2">Criteria 2: </label> <input id="s2crit2" type="text" name="s2crit2" value ="">
            Maximum: <select name="s2crit2max">
            	<option selected >&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>
        </div>
        <div class="form-group row">
            <label for="s2crit3">Criteria 3: </label> <input id="s2crit3" type="text" name="s2crit3" value ="">
            Maximum: <select name="s2crit3max">
            	<option selected >&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>
        </div>
	</div>
	
	<!-- section 3 -->
	<div class="well text-center">
		<p class="text-left">2. Section III: <input type="text" name="s3name"></p>
		<div class="form-group row">
            <label for="s3crit1">Criteria 1: </label> <input id="s3crit1" type="text" name="s3crit1" value ="">
            Maximum: <select name="s3crit1max">
            	<option selected disabled>&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>
        </div>
        <div class="form-group row">
            <label for="s3crit2">Criteria 2: </label> <input id="s3crit2" type="text" name="s3crit2" value ="">
            Maximum: <select name="s3crit2max">
            	<option selected >&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>
        </div>
        <div class="form-group row">
            <label for="s3crit3">Criteria 3: </label> <input id="s3crit3" type="text" name="s3crit3" value ="">
            Maximum: <select name="s3crit3max">
            	<option selected >&mdash;</option>
            	<option value="5">5</option>
            	<option value="4">4</option>
            	<option value="3">3</option>
            	<option value="2">2</option>
            	<option value="1">1</option>
            </select>
        </div>
	</div>
	
	<div class="form-group text-center">
		<input class="btn btn-default" type="submit">
		<a class="btn btn-default" href="?firstLoad=true">Cancel</a>
	</div>
</form>

</body>
</html>