<%
/*
	Project Pegasus
*/
%>
<%@ page import = "utility.database.DatabaseAccess" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "utility.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	if(!ServletUtilities.doesSessionAttributeExist(session,"logged-in")){
		response.sendRedirect("Login?errNo=1");
		return;
	}
%>
<%!
	String inputfName;
	String inputlName;
	String inputEmployeeNum;
	String inputEmail;
	String inputHiredYear;
	String inputJobPosition;
	int employeeNum = 0;
	int yearHired = 0;
	boolean validated = true;
	boolean missfName = false;
	boolean fNameHasNumbersOrS = false;
	boolean lNameHasNumbersOrS = false;
	boolean employeeNumHasChar = false;
	boolean misslName = false;
	boolean missEmployeeNum = false;
	boolean missEmailEmpty = false;
	boolean missEmailFormat = false;
	boolean missHiredYear = false;
	boolean missJobPosition = false;
	boolean firstLoad = false;
	String errorMessage = "";
%>
<% //if(ServletUtilities.checkParameterExists("hiredDate")){
//	hiredYear = Integer.parseInt(inputHiredYear);
//}
String inputFirstLoad = request.getParameter("firstLoad");
	 inputfName = request.getParameter("fName");
	 inputlName = request.getParameter("lName");
	 inputEmployeeNum = request.getParameter("employeeNum");
	 inputEmail = request.getParameter("email");
	 inputHiredYear = request.getParameter("hiredDate");
	 inputJobPosition = request.getParameter("jobPosition");
%>    
<% 
	out.println(ServletUtilities.headWithTitle("Employee Input"));
%>
<body>
<%@ include file = "NavigationBar.jsp" %>
<%
	out.println("<h1 class=\"text-center\">Employee Entry</h1>");
%>
<%
	if(ServletUtilities.checkParameterExists(inputFirstLoad) && inputFirstLoad.equals("true"))
	{
		//first time loading page, must load empty form
		firstLoad = true;
	}
%>
<div class = "well">
	<form method = "post" action = "EmployeeEntry.jsp">
<% errorMessage = ""; %>
		<div class = "form-group">
<%	//checking if parameters exist
	//if they do not exist, the submission is invalid and add to the error message to be displayed
	if(!ServletUtilities.checkParameterExists(inputfName)) {
			validated = false;
			missfName = true;
			inputfName = "";
			errorMessage += "First name is empty<br>";
		}
	//make sure input only contains alphabetic characters
			else if(EmployeeValidations.isAlphabetic(inputfName) == false) {
				validated = false;
				fNameHasNumbersOrS = true;
				errorMessage += "First name cannot have numbers or special characters<br>";
			}
%> 
		First Name: <input value = "<%=inputfName %>" type = "text" name = "fName"> <% out.println(ServletUtilities.getHtmlInputError((missfName || fNameHasNumbersOrS) && !firstLoad)); %>
		</div>
		<div class = "form-group">
<%	
	if(!ServletUtilities.checkParameterExists(inputlName)) {
			validated = false;
			misslName = true;
			inputlName = "";
			errorMessage += "Last name is empty<br>";
		}
	//if last name has numbers, input is invalid
		else if(EmployeeValidations.hasNumbers(inputlName) == true) {
			validated = false;
			lNameHasNumbersOrS = true;
			errorMessage += "First name cannot have numbers or special characters<br>";
		}
%>
		Last Name: <input value = "<%=inputlName %>" type = "text" name = "lName"> 
		</div>
		<div class = "form-group">
<%		
		if(!ServletUtilities.checkParameterExists(inputEmployeeNum)) {
			validated = false;
			missEmployeeNum = true;
			inputEmployeeNum = "";
			errorMessage += "Employee number cannot be empty<br>";
		}
		else {
			//check if employeeNum can be parsed into an integer
			if(EmployeeValidations.tryParseInt(inputEmployeeNum) == true){
				employeeNum = Integer.parseInt(inputEmployeeNum);
			}
			//if cannot parse, input not valid
			else {
				validated = false;
				employeeNumHasChar = true;
				errorMessage += "Employee numbers can only contain whole numbers<br>";
			}
		}
%>
		Employee#: <input value = "<%=inputEmployeeNum %>" type = "text" name = "employeeNum"> 
		</div>
		<div class = "form-group">
<%
		if(!ServletUtilities.checkParameterExists(inputEmail)) {
				validated = false;
				missEmailEmpty = true;
				inputEmail = "";
				errorMessage += "Email cannot be empty<br>";
				}
		//make sure email conforms to email regex
		else if(EmployeeValidations.isEmail(inputEmail) == false) {
			validated = false;
			missEmailFormat = true;	
			errorMessage += "Email is not valid<br>";
			}
%>		
		Email: <input value = "<%=inputEmail %>" type = "text" name = "email"> 
		</div>
		<div class = "form-group">
<%
		if(!ServletUtilities.checkParameterExists(inputHiredYear)) {
			validated = false;
			missHiredYear = true;
			inputHiredYear = "";
			errorMessage += "Must enter a hire year<br>";
		}
		else {
			yearHired = Integer.parseInt(inputHiredYear);
		}

%>
		Hired Year: <% out.println(ServletUtilities.generateHtmlForYear(yearHired,50)); %>
		</div>
		<div class = "form-group">
<%
		if(!ServletUtilities.checkParameterExists(inputJobPosition)){
			validated = false;
			missJobPosition = true;
			inputJobPosition = "";
			errorMessage += "Must enter a job position<br>";
		}
		else {
	
		}
%>		
		Job Position: <% out.println(ServletUtilities.generateHtmlForPositions(inputJobPosition)); %>
		</div>
		<div class = "form-group">
		<input class = "btn btn-default" type = "submit" value = "Submit"/>
		<a class = "btn btn-default" href="?firstload=true">Cancel</a>
		</div>
	</form>
		<div class= "alert alert-warning">
		<p><%=errorMessage %></p>
		</div>
</div>
<%
		//if all the input is validated input it into the database
		if(validated) {
			//date instances start at 1900, where year 1 is 1900
			//must subtract 1900 from ex 2017 for 2017 to be inserted
			java.sql.Date sqlDate = new java.sql.Date(yearHired - 1900, 1 ,1);
			String query = "INSERT INTO EMPLOYEE_T(EmployeeID, EmployeeFirst,EmployeeLast,EmployeeEmail,DateHired,EmployeePosition) VALUES(?,?,?,?,?,?)";
			PreparedStatement dbStatement;
			try {
				//insert into database
				dbStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
				dbStatement.setInt(1, employeeNum);
				dbStatement.setString(2, inputfName);
				dbStatement.setString(3, inputlName);
				dbStatement.setString(4,inputEmail);
				dbStatement.setDate(5,sqlDate);
				dbStatement.setString(6,inputJobPosition);
				if(dbStatement.executeUpdate() == 1)
				{
					//insert successful, show this on page
					out.print("<h1> Successfully added " + inputfName + " " + inputlName + "</h1>");
					out.print("<a class=\"btn-default btn-lg btn-primary\" href=\"EmployeesEntry?firstLoad=true\">Back to Employee Entry</a>\r\n");
				}
					
				else
				{
					//insert not successful, in most cases SQLException will be thrown called instead
					out.print("<h1> Could not insert " + inputfName + " " + inputlName + "</h1>");
					out.print("<a class=\"btn-default btn-lg btn-primary\" href=\"EmployeesEntry?firstLoad=true\">Back to Employee Entry</a>\r\n");
				}
					
			} catch (SQLException e) {
				//insert not successful, it will output the reason why on page
				out.print("<h1> Could not insert</h1>" +
						"<p class='well'>" + e.getMessage() + "</p>");
				out.print("<a class=\"btn-default btn-lg btn-primary\" href=\"EmployeesEntry?firstLoad=true\">Back to Employee Entry</a>\r\n");
				e.printStackTrace();
			} catch (Exception e) {
				//if error is not SQLException, print error to console
				e.printStackTrace();
			}
			
		}

%>
</body>