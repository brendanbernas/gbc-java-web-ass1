<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- making sure the use is authenticated --%>


<%
/*Project: Pegasus Internal Web Application  
*Assignment: #1
*Author: Brendan Bernas, Albert Nguyen
*Student Number: 101012650, 100865315
*Date: Oct 19,2017
*Description: HTML for group insertion
*/ 
%>
     
	<%@ page import="utility.ServletUtilities"%>
	
	<%
		String groupName = "";
		String department = request.getParameter("dName");
		String gMember1 = request.getParameter("gMember1");
		String gMember2 = request.getParameter("gMember2");
		String gMember3 = request.getParameter("gMember3");
		String gMember4 = request.getParameter("gMember4");
		String gMember5 = request.getParameter("gMember5");
		String gMember6 = request.getParameter("gMember6");
		boolean checkMember = false;
		boolean checkDuplication = false;
		boolean checkDepartment = false;
		boolean checkGroupName = false;
		String error="";
		
		//Check parameter for previous page
		if(request.getParameter("gName") != null)
			 groupName = request.getParameter("gName");
		
		if(request.getAttribute("checkMember") != null){
			checkMember = (boolean) request.getAttribute("checkMember");
		}
		
		if(request.getAttribute("checkDuplication") != null){
			checkDuplication = (boolean) request.getAttribute("checkDuplication");
		}
		
		if(request.getAttribute("checkDepartment") != null){
			checkDepartment = (boolean) request.getAttribute("checkDepartment");
		}
		
		if(request.getAttribute("checkGroupName") != null){
			checkGroupName = (boolean) request.getAttribute("checkGroupName");
		}
		if(request.getAttribute("error") != null){
			error = (String) request.getAttribute("error");
		}
		//

	%>
	
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Group Entry</title>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>

	<jsp:include page="/WEB-INF/pages/include/navigationBar.jsp"/>

	<h1 class="text-center">Group Entry</h1>
	
	<div class="well text-center">
	    <form method='post' action="GroupProcess">
	       
	        <div class="form-group">
	            <label for="groupName">Group Name: </label> 
	            <input id="groupName" type="text" name="gName" value=<%= groupName %>> 
	            <% if(checkGroupName){out.print("<font size=\"3\" color=\"red\">*</font>");} %>
	        </div>
	        
	         <div class="form-group">
	            <label for="departName">Department:</label> 
	            <select id="departName" name="dName">

	            	<% out.print(ServletUtilities.departmentDropDown(department));  %>
	            	
	            </select> 
	            <% if(checkDepartment){out.print("<font size=\"3\" color=\"red\">*</font>");} %>
	        </div> 
	       
	      
	        <% if(checkMember || checkDuplication){out.print("<font size=\"3\" color=\"red\">(Check all members)&#8595;</font> <br><br>");} %>
	        
	        <div class="form-group">
	            <label for="groupMember1">GroupMember01</label> 
	            <select id="groupMember1" name="gMember1">
	            	
					<% out.print(ServletUtilities.employeeDropDown(gMember1));  %>
					
	            </select> 
	            
	            <label for="groupMember2">GroupMember02</label> 
	            <select id="groupMember2" name="gMember2">
	            	
					<% out.print(ServletUtilities.employeeDropDown(gMember2));  %>
					
	            </select> 
	            
	            <label for="groupMember1">GroupMember03</label> 
	            <select id="groupMember3" name="gMember3">
	            
	            	 <% out.print(ServletUtilities.employeeDropDown(gMember3));  %>
	            	 
	            </select> 
	            
	        </div>
	        
	        <div class="form-group">
	            <label for="groupMember4">GroupMember04</label> 
	            <select id="groupMember4" name="gMember4">
	            
	            	<% out.print(ServletUtilities.employeeDropDown(gMember4));  %>
	            	
	            </select> 
	            
	             <label for="groupMember5">GroupMember05</label> 
	            <select id="groupMember5" name="gMember5">
	            
	            	 <% out.print(ServletUtilities.employeeDropDown(gMember5));  %>
	            	 
	            </select> 
	            
	             <label for="groupMember6">GroupMember06</label> 
	            <select id="groupMember6" name="gMember6">
	            
	            	 <% out.print(ServletUtilities.employeeDropDown(gMember6));  %>
	            	 
	            </select> 
	            
	        </div>
	        
	        <div class="form-group">
				 <input type="hidden" name="checkSubmit" value="true">
	            <input class="btn btn-default" value="Submit" name="submit" type="submit">
	            <input class="btn btn-default" type="reset" value="Cancel">
	        </div>
	        
	        <%= error %>
	
	    </form>
	</div>
</body>
</html>