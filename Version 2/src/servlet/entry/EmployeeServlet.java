/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Piotr Grabowski
 *Student Number: 100730728
 *Date: Oct 19,2017
 *Description: This servlet is used to display the employee entry form, validate the submitted parameters from the client, and store the client's entry into the database
 */
package servlet.entry;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.EmployeeValidations;
import utility.database.DatabaseAccess;
import utility.ServletUtilities;

/**
 * Written By: Piotr Grabowski 100730728
 */
@WebServlet("/EmployeesController")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmployeeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		//get all parameters
		String inputfName = request.getParameter("firstname");
		String inputlName = request.getParameter("lastname");
		String inputEmployeeNum = request.getParameter("employeenum");
		String inputEmail = request.getParameter("email");
		String inputHiredYear = request.getParameter("hiredyear");
		String inputJobPosition = request.getParameter("jobposition");
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
		String redirectAddress;
		String errorMessage = "";
		
		//checking if parameters exist
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
		
		if(!ServletUtilities.checkParameterExists(inputHiredYear)) {
			validated = false;
			missHiredYear = true;
			inputHiredYear = "";
			errorMessage += "Must enter a hire year<br>";
		}
		else {
			yearHired = Integer.parseInt(inputHiredYear);
		}
		
		if(!ServletUtilities.checkParameterExists(inputJobPosition)){
			validated = false;
			missJobPosition = true;
			inputJobPosition = "";
			errorMessage += "Must enter a job position<br>";
		}
		else {
			
		}
		request.setAttribute("error",errorMessage);
		
		//redirectAddress = "/WEB-INF/employee/employee_form.jsp";
		//RequestDispatcher dispatcher = request.getRequestDispatcher(redirectAddress);
		//dispatcher.forward(request,response);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("EmployeeForm");
		requestDispatcher.include(request, response);
		
		//if all the input is validated input it into the database
		/*
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
					pw.print("<h1> Successfully added " + inputfName + " " + inputlName + "</h1>" +
						"</body>");
					pw.print("<a class=\"btn-default btn-lg btn-primary\" href=\"EmployeesEntry?firstLoad=true\">Back to Employee Entry</a>\r\n");
				}
					
				else
				{
					//insert not successful, in most cases SQLException will be thrown called instead
					pw.print("<h1> Could not insert " + inputfName + " " + inputlName + "</h1>" +
							"</body>");
					pw.print("<a class=\"btn-default btn-lg btn-primary\" href=\"EmployeesEntry?firstLoad=true\">Back to Employee Entry</a>\r\n");
				}
					
			} catch (SQLException e) {
				//insert not successful, it will output the reason why on page
				pw.print("<h1> Could not insert</h1>" +
						"<p class='well'>" + e.getMessage() + "</p>" +
						"</body>");
				pw.print("<a class=\"btn-default btn-lg btn-primary\" href=\"EmployeesEntry?firstLoad=true\">Back to Employee Entry</a>\r\n");
				e.printStackTrace();
			} catch (Exception e) {
				//if error is not SQLException, print error to console
				e.printStackTrace();
			}
			
			

			
		}
		else {
			//if input is NOT valid, this code executes
			pw.println("<h1 class=\"text-center\">Employee Entry</h1>");

			if(!errorMessage.equals(""))
				errorMessage = 
						"<div class=\"alert alert-warning\">\r\n" + 
						"	<p>"+errorMessage+"</p>\r\n" + 
						"</div>";
			
			if(firstLoad)
				errorMessage = ""; //overwrite error message to "" if firstLoad == true
			
			//printing form to page
			//gets previous inputs and recreates them on this form
			//ServletUtilities.getHtmlInputError() will return a red html "*" on the form if the param evaluates true
			//if firstLoad == true, getHtmlInputEror() will return ""
			pw.println("<div class=\"well\">\r\n" + 
					"    <form method = \"post\" action = \"EmployeesEntry\">\r\n" + 
					"        <div class=\"form-group\">\r\n" + 
					"            First Name: <input value=\"" +inputfName+ "\" type = \"text\" name = \"fName\"/>" + ServletUtilities.getHtmlInputError((missfName || fNameHasNumbersOrS) && !firstLoad) +
					"        </div>\r\n" + 
					"        <div class=\"form-group\">\r\n" + 
					"            Last Name: <input value=\""+inputlName+"\" type = \"text\" name = \"lName\"/>"+  ServletUtilities.getHtmlInputError((misslName || lNameHasNumbersOrS) && !firstLoad) +
					"        </div>\r\n" + 
					"        <div class=\"form-group\">\r\n" + 
					"            Employee#: <input value=\""+inputEmployeeNum+"\" type = \"text\" name = \"employeeNum\"/>" + ServletUtilities.getHtmlInputError((missEmployeeNum || employeeNumHasChar) && !firstLoad) +
					"        </div>\r\n" + 
					"        <div class=\"form-group\">\r\n" + 
					"            Email: <input value=\""+inputEmail+"\" type = \"text\" name = \"email\"/>" + ServletUtilities.getHtmlInputError((missEmailEmpty|| missEmailFormat) && !firstLoad) +
					"        </div>\r\n" + 
					"        <div class=\"form-group\">\r\n" + 
					"            Hired year:\r\n" + 
								//generate options for 50 years
								ServletUtilities.generateHtmlForYear(yearHired, 50) +
					"            \r\n" + ServletUtilities.getHtmlInputError(missHiredYear && !firstLoad) +
					"        </div>\r\n" + 
					"        <div class=\"form-group\">Job position:\r\n" + 
								//generates options for positions in ServletUtilities class
								//can also create a string array and add them as a parameter overloading method
					            ServletUtilities.generateHtmlForPositions(inputJobPosition) +
					"			 \r\n" + ServletUtilities.getHtmlInputError(missJobPosition && !firstLoad) +
					"		 </div>\r\n" +
					"        <div class=\"form-group\">\r\n" + 
					"            <input class=\"btn btn-default\" type = \"submit\" value = \"Submit\" />&nbsp;" +
					" 			 <a class=\"btn btn-default\" href=\"?firstLoad=true\">Cancel</a>\r\n" + 
					"        </div>\r\n" + 
					"    </form>\r\n" + 
					errorMessage +
					"</div>");
			pw.println("</body>");
		}
		*/
	}

	

}