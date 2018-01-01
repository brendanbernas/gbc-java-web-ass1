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
import utility.employees.Employee;
import utility.ServletUtilities;

/**
 * Written By: Piotr Grabowski 100730728
 */
@WebServlet("/EmployeesProcess")
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
			request.setAttribute("yearhired",yearHired);
		}
		
		if(!ServletUtilities.checkParameterExists(inputJobPosition)){
			validated = false;
			missJobPosition = true;
			inputJobPosition = "";
			errorMessage += "Must enter a job position<br>";
		}
		else {
			request.setAttribute("jobposition",inputJobPosition);
			
		}
		java.sql.Date inputSqlDate = new java.sql.Date(yearHired - 1900, 1 ,1);
		Employee employee = new Employee(employeeNum,inputfName,inputlName,inputEmail,inputSqlDate,inputJobPosition);
		System.out.println(employee.getfName() + employee.getlName());
		request.setAttribute("employee",employee);
		request.setAttribute("error",errorMessage);
		
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
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("EmployeeSuccess");
					requestDispatcher.include(request, response);
				}
					
				else
				{
					//insert not successful, in most cases SQLException will be thrown called instead
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("EmployeeSuccess");
					requestDispatcher.include(request, response);
				}
					
			} catch (SQLException e) {
				
			} catch (Exception e) {
				//if error is not SQLException, print error to console
				e.printStackTrace();
			}
			
			

			
		}
		else {
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("EmployeeForm");
			requestDispatcher.include(request, response);
		}
	}

	

}