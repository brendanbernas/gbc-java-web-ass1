package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helperClasses.EmployeeValidations;
import utility.database.DatabaseAccess;

/**
 * Written By: Piotr Grabowski 100730728
 */
@WebServlet("/Employee")
public class Employee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * This servlet get the input parameters from the Employee's form and save an Employee to the Database
     */
    public Employee() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputfName = request.getParameter("fName");
		String inputlName = request.getParameter("lName");
		String inputEmployeeNum = request.getParameter("employeeNum");
		String inputEmail = request.getParameter("email");
		String inputHiredYear = request.getParameter("hiredDate");
		String inputJobPosition = request.getParameter("jobPosition");
		int employeeNum;
		long dateHired = 2017;
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
		PrintWriter pw = response.getWriter();
		
		response.setContentType("text/html");
		
		if(EmployeeValidations.isMissingOrEmpty(inputfName)) {
			validated = false;
			missfName = true;
		}
		else if(EmployeeValidations.isAlphabetic(inputfName) == false) {
			validated = false;
			fNameHasNumbersOrS = true;
		}
		else {
			
		}
		
		if(EmployeeValidations.isMissingOrEmpty(inputlName)) {
			validated = false;
			misslName = true;
		}
		else if(EmployeeValidations.hasNumbers(inputlName) == true) {
			validated = false;
			lNameHasNumbersOrS = true;
		}
		else {
			
		}
		
		if(EmployeeValidations.isMissingOrEmpty(inputEmployeeNum)) {
			validated = false;
			missEmployeeNum = true;
		}
		else {
			if(EmployeeValidations.tryParseInt(inputEmployeeNum) == true){
				employeeNum = Integer.parseInt(inputEmployeeNum);
			}
			else {
				validated = false;
				employeeNumHasChar = true;
			}
		}
		
		if(EmployeeValidations.isMissingOrEmpty(inputEmail)) {
			validated = false;
			missEmailEmpty = true;
			
		}
		else if(EmployeeValidations.isEmail(inputEmail) == false) {
			validated = false;
			missEmailFormat = true;	
		}
		else {
		
		}
		
		if(inputHiredYear == null || inputHiredYear.trim().equals("")) {
			validated = false;
			missHiredYear = true;
		}
		else {
			dateHired = Long.parseLong(inputHiredYear);
		}
		
		if(inputJobPosition == null || inputJobPosition.trim().equals("")){
			validated = false;
			missJobPosition = true;
		}
		else {
			
		}
		
		if(validated) {
			
			java.sql.Date sqlDate = new java.sql.Date(dateHired);
			String query = "INSERT INTO EMPLOYEE_T(EmployeeID, EmployeeFirst,EmployeeLast,EmployeeEmail,DateHired,EmployeePosition) VALUES(null,?,?,?,?,?)";
			PreparedStatement dbStatement;
			try {
				dbStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
				dbStatement.setString(1, inputfName);
				dbStatement.setString(2, inputlName);
				dbStatement.setString(3,inputEmail);
				dbStatement.setDate(4,sqlDate);
				dbStatement.setString(5,inputJobPosition);
				dbStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pw.print(headWithTitle("Employee") + "<h1> Successfully added " + inputfName + " " + inputlName + "</h1>");
			
		}
		else {
			pw.println(headWithTitle("Employee"));
			pw.println("<body bgcolor=\"A0ED97\">");
			pw.println("<h1>Employee Entry</h1>");
			pw.println("<form method = \"post\" action = \"http://localhost:8080/COMP3095_Ass1/Employee\">");
			if(missfName == true) {
				pw.println("First Name: <input type = \"text\" name = \"fName\"/> <font size=\"3\" color=\"red\">*</font> <br>");
			}
			else if(fNameHasNumbersOrS == true) {
				pw.println("First Name: <input type = \"text\" name = \"fName\" value = \"" + inputfName + "\"/> <font size=\"3\" color=\"red\">*</font>" +
						"<font size=\"3\" color=\"red\">Names cannot have numbers or special characters</font> <br><br>");
			}
			else {
			pw.println("First Name: <input type = \"text\" name = \"fName\" value = \"" + inputfName + "\"/><br>");
			}
			if(misslName == true) {
				pw.println("Last Name: <input type = \"text\" name = \"lName\"/> <font size=\"3\" color=\"red\">*</font> <br><br>");
			}
			else if(lNameHasNumbersOrS == true) {
				pw.println("Last Name: <input type = \"text\" name = \"lName\" value = \"" + inputlName + "\"/> <font size=\"3\" color=\"red\">*</font>" +
				"<font size=\"3\" color=\"red\">Names cannot have numbers or special characters</font> <br><br>");
			}
			else {
			pw.println("Last Name: <input type = \"text\" name = \"lName\" value = \"" + inputlName + "\"/><br><br>");
			}
			if(missEmployeeNum == true) {
				pw.println("Employee#: <input type = \"text\" name = \"employeeNum\"/> <font size=\"3\" color=\"red\">*</font> <br><br>");
			}
			else if(employeeNumHasChar == true){
				pw.println("Employee#: <input type = \"text\" name = \"employeeNum\" value = \"" + inputEmployeeNum + "\"/> <font size=\"3\" color=\"red\">*</font>" +
						"<font size=\"3\" color=\"red\">Employees can only contain whole numbers</font> <br><br>");
			}
			else {
			pw.println("Employee#: <input type = \"text\" name = \"employeeNum\" value = \"" + inputEmployeeNum + "\"/><br><br>");
			}
			if(missEmailEmpty == true) {
				pw.println("Email: <input type = \"text\" name = \"email\"/> <font size=\"3\" color=\"red\">*</font> <br>");
			}
			else if(missEmailFormat == true) {
				pw.println("Email: <input type = \"text\" name = \"email\" value = \"" + inputEmail + "\"/> <font size=\"3\" color=\"red\">That is an invalid email (e.g someone@address.com)</font> <br>");
			}
			else {
			pw.println("Email: <input type = \"text\" name = \"email\" value = \"" + inputEmail + "\"/><br>");	
			}
			if(missHiredYear == true) {
				pw.println("<font size=\"3\" color=\"red\">Choose the year you were hired below</font>");
				pw.println("<select name = \"hiredDate\">");
				pw.println("<option selected disabled>Hired Year</option>");
				pw.println("<option value=\"2017\">2017</option>");
				pw.println("<option value=\"2016\">2016</option>");
				pw.println("<option value=\"2015\">2015</option>");
				pw.println("<option value=\"2014\">2014</option>");
				pw.println("<option value=\"2013\">2013</option>");
				pw.println("<option value=\"2012\">2012</option>");
				pw.println("<option value=\"2011\">2011</option>");
				pw.println("<option value=\"2010\">2010</option>");
				pw.println("<option value=\"2009\">2009</option>");
				pw.println("</select><br><br>");
			}
			else {
			pw.println("<select name = \"hiredDate\">");
			pw.println("<option disabled>Hired Year</option>");
			if(inputHiredYear.equals("2017")) {
				pw.println("<option selected value=\"2017\">2017</option>");
			}
			else {
			pw.println("<option value=\"2017\">2017</option>");
			}
			if(inputHiredYear.equals("2016")) {
				pw.println("<option selected value=\"2016\">2016</option>");
			}
			else {
			pw.println("<option value=\"2016\">2016</option>");
			}
			if(inputHiredYear.equals("2015")) {
				pw.println("<option selected value=\"2015\">2015</option>");
			}
			else {
			pw.println("<option value=\"2015\">2015</option>");
			}
			if(inputHiredYear.equals("2014")) {
				pw.println("<option selected value=\"2014\">2014</option>");
			}
			else {
			pw.println("<option value=\"2014\">2014</option>");
			}
			if(inputHiredYear.equals("2013")) {
				pw.println("<option selected value=\"2013\">2013</option>");
			}
			else {
			pw.println("<option value=\"2013\">2013</option>");
			}
			if(inputHiredYear.equals("2012")) {
				pw.println("<option selected value=\"2012\">2012</option>");
			}
			else {
			pw.println("<option value=\"2012\">2012</option>");
			}
			if(inputHiredYear.equals("2011")) {
				pw.println("<option selected value=\"2011\">2011</option>");
			}
			else {
			pw.println("<option value=\"2011\">2011</option>");
			}
			if(inputHiredYear.equals("2010")) {
				pw.println("<option selected value=\"2010\">2010</option>");
			}
			else {
			pw.println("<option value=\"2010\">2010</option>");
			}
			if(inputHiredYear.equals("2009")) {
				pw.println("<option selected value=\"2009\">2009</option>");
			}
			else {
			pw.println("<option value=\"2009\">2009</option>");
			}
			pw.println("</select><br><br>");
			}
			if(missJobPosition == true) {
				pw.println("<font size=\"3\" color=\"red\">Choose the job position below</font>");
				pw.println("<select name = \"jobPosition\">");
				pw.println("<option selected disabled>Job Position</option>");
				pw.println("<option value=\"Manager\">Manager</option>");
				pw.println("<option value=\"Receptionist\">Receptionist</option>");
				pw.println("<option value=\"Supervisor\">Supervisor</option>");
				pw.println("</select><br><br>");
				pw.println("<input type = \"submit\" value = \"Submit\" />&nbsp;<input type = \"submit\" value = \"Cancel\"/>");
				pw.println("</form>");
				pw.println("</body>");
				pw.println("</html>");
			}
			else {
				pw.println("<select name = \"jobPosition\">");
				pw.println("<option disabled>Job Position</option>");
				if(inputJobPosition.equals("Manager")) {
					pw.println("<option selected value=\"Manager\">Manager</option>");
				}
				else {
					pw.println("<option value=\"Manager\">Manager</option>");
				}
				if(inputJobPosition.equals("Receptionist")) {
					pw.println("<option selected value=\"Receptionist\">Receptionist</option>");
				}
				else {
					pw.println("<option value=\"Receptionist\">Receptionist</option>");
				}
				if(inputJobPosition.equals("Supervisor")) {
					pw.println("<option selected value=\"Supervisor\">Supervisor</option>");
				}
				else {
				pw.println("<option value=\"Supervisor\">Supervisor</option>");
				}
				pw.println("</select><br><br>");
				pw.println("<input type = \"submit\" value = \"Submit\" />&nbsp;<input type = \"submit\" value = \"Cancel\"/>");
				pw.println("</form>");
				pw.println("</body>");
				pw.println("</html>");	
			}
		}
	}
	
	/*
	 * headWithTitle method takes a string as a parameter and returns a string that contains a html header and title tag
	 */
	private String headWithTitle(String title)
	  {
	    return 
	    
	      "<!DOCTYPE html>\n<html> \n<head><title>" + title + "</title></head>\n";
	  }

}
