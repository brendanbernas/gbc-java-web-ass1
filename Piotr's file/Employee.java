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
		dateHired = 2017;
		validated = true;
		missfName = false;
		fNameHasNumbersOrS = false;
		lNameHasNumbersOrS = false;
		employeeNumHasChar = false;
		misslName = false;
		missEmployeeNum = false;
		missEmailEmpty = false;
		missEmailFormat = false;
		missHiredYear = false;
		missJobPosition = false;
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
			if(missEmployeeNum == true) {
				
				java.sql.Date sqlDate = new java.sql.Date(dateHired);
				String query = "INSERT INTO EMPLOYEE_T(EmployeeFirst,EmployeeLast,EmployeeEmail,DateHired,EmployeePosition) VALUES(?,?,?,?,?)";
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
			//implement
			//else if(DatabaseAccess.employeeExists == true) {
			//	
			//}
			else {
				
				java.sql.Date sqlDate = new java.sql.Date(dateHired);
				String query = "INSERT INTO EMPLOYEE_T(EmployeeID, EmployeeFirst,EmployeeLast,EmployeeEmail,DateHired,EmployeePosition) VALUES(?,?,?,?,?,?)";
				PreparedStatement dbStatement;
				try {
					dbStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
					dbStatement.setInt(1,employeeNum)
					dbStatement.setString(2, inputfName);
					dbStatement.setString(3, inputlName);
					dbStatement.setString(4,inputEmail);
					dbStatement.setDate(5,sqlDate);
					dbStatement.setString(6,inputJobPosition);
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
			
		}
		else {
			pw.print(stickyHTMLForm(inputfName,inputlName,inputEmployeeNum,inputEmail,inputHiredYear,inputJobPosition));
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
	private String stickyHTMLForm(String inputfName,String inputlName,String inputEmployeeNum,String inputEmail,String inputHiredYear, String inputJobPosition){
		String html = "";
		html += (headWithTitle("Employee"));
		html +=("<body bgcolor=\"A0ED97\">\n");
		html +=("<h1>Employee Entry</h1>\n");
		html +=("<form method = \"post\" action = \"http://localhost:8080/COMP3095_Ass1/Employee\">\n");
		if(missfName == true) {
			html +=("First Name: <input type = \"text\" name = \"fName\"/> <font size=\"3\" color=\"red\">*</font> <br>\n");
		}
		else if(fNameHasNumbersOrS == true) {
			html +=("First Name: <input type = \"text\" name = \"fName\" value = \"" + inputfName + "\"/> <font size=\"3\" color=\"red\">*</font>\n" +
					"<font size=\"3\" color=\"red\">Names cannot have numbers or special characters</font> <br><br>\n");
		}
		else {
			html +=("First Name: <input type = \"text\" name = \"fName\" value = \"" + inputfName + "\"/><br>\n");
		}
		if(misslName == true) {
			html +=("Last Name: <input type = \"text\" name = \"lName\"/> <font size=\"3\" color=\"red\">*</font> <br><br>\n");
		}
		else if(lNameHasNumbersOrS == true) {
			html +=("Last Name: <input type = \"text\" name = \"lName\" value = \"" + inputlName + "\"/> <font size=\"3\" color=\"red\">*</font>\n" +
			"<font size=\"3\" color=\"red\">Names cannot have numbers or special characters</font> <br><br>\n");
		}
		else {
		html +=("Last Name: <input type = \"text\" name = \"lName\" value = \"" + inputlName + "\"/><br><br>\n");
		}
		if(employeeNumHasChar == true){
			html +=("Employee#: <input type = \"text\" name = \"employeeNum\" value = \"" + inputEmployeeNum + "\"/> <font size=\"3\" color=\"red\">*</font>\n" +
					"<font size=\"3\" color=\"red\">Employees can only contain whole numbers</font> <br><br>\n");
		}
		else {
			html +=("Employee#: <input type = \"text\" name = \"employeeNum\" value = \"" + inputEmployeeNum + "\"/><br><br>\n");
		}
		if(missEmailEmpty == true) {
			html +=("Email: <input type = \"text\" name = \"email\"/> <font size=\"3\" color=\"red\">*</font> <br>\n");
		}
		else if(missEmailFormat == true) {
			html +=("Email: <input type = \"text\" name = \"email\" value = \"" + inputEmail + "\"/> <font size=\"3\" color=\"red\">That is an invalid email (e.g someone@address.com)</font> <br>\n");
		}
		else {
			html +=("Email: <input type = \"text\" name = \"email\" value = \"" + inputEmail + "\"/><br>\n");	
		}
		if(missHiredYear == true) {
			html +=("<font size=\"3\" color=\"red\">Choose the year you were hired below</font>\n");
			html +=("<select name = \"hiredDate\">\n");
			html +=("<option selected disabled>Hired Year</option>\n");
			html +=("<option value=\"2017\">2017</option>\n");
			html +=("<option value=\"2016\">2016</option>\n");
			html +=("<option value=\"2015\">2015</option>\n");
			html +=("<option value=\"2014\">2014</option>\n");
			html +=("<option value=\"2013\">2013</option>\n");
			html +=("<option value=\"2012\">2012</option>\n");
			html +=("<option value=\"2011\">2011</option>\n");
			html +=("<option value=\"2010\">2010</option>\n");
			html +=("<option value=\"2009\">2009</option>\n");
			html +=("</select><br><br>\n");
		}
		else {
			html +=("<select name = \"hiredDate\">\n");
			html +=("<option disabled>Hired Year</option>\n");
		if(inputHiredYear.equals("2017")) {
			html +=("<option selected value=\"2017\">2017</option>\n");
		}
		else {
			html +=("<option value=\"2017\">2017</option>\n");
		}
		if(inputHiredYear.equals("2016")) {
			html +=("<option selected value=\"2016\">2016</option>\n");
		}
		else {
			html +=("<option value=\"2016\">2016</option>\n");
		}
		if(inputHiredYear.equals("2015")) {
			html +=("<option selected value=\"2015\">2015</option>\n");
		}
		else {
			html +=("<option value=\"2015\">2015</option>\n");
		}
		if(inputHiredYear.equals("2014")) {
			html +=("<option selected value=\"2014\">2014</option>\n");
		}
		else {
			html +=("<option value=\"2014\">2014</option>\n");
		}
		if(inputHiredYear.equals("2013")) {
			html +=("<option selected value=\"2013\">2013</option>\n");
		}
		else {
			html +=("<option value=\"2013\">2013</option>\n");
		}
		if(inputHiredYear.equals("2012")) {
			html +=("<option selected value=\"2012\">2012</option>\n");
		}
		else {
			html +=("<option value=\"2012\">2012</option>\n");
		}
		if(inputHiredYear.equals("2011")) {
			html +=("<option selected value=\"2011\">2011</option>\n");
		}
		else {
			html +=("<option value=\"2011\">2011</option>\n");
		}
		if(inputHiredYear.equals("2010")) {
			html +=("<option selected value=\"2010\">2010</option>\n");
		}
		else {
			html +=("<option value=\"2010\">2010</option>\n");
		}
		if(inputHiredYear.equals("2009")) {
			html +=("<option selected value=\"2009\">2009</option>\n");
		}
		else {
			html +=("<option value=\"2009\">2009</option>\n");
		}
		html +=("</select><br><br>\n");
		}
		if(missJobPosition == true) {
			html +=("<font size=\"3\" color=\"red\">Choose the job position below</font>\n");
			html +=("<select name = \"jobPosition\">\n");
			html +=("<option selected disabled>Job Position</option>\n");
			html +=("<option value=\"Manager\">Manager</option>\n");
			html +=("<option value=\"Receptionist\">Receptionist</option>\n");
			html +=("<option value=\"Supervisor\">Supervisor</option>\n");
			html +=("</select><br><br>\n");
			html +=("<input type = \"submit\" value = \"Submit\" />&nbsp;<input type = \"submit\" value = \"Cancel\"/>\n");
			html +=("</form>\n");
			html +=("</body>\n");
			html +=("</html>\n");
		}
		else {
			html +=("<select name = \"jobPosition\">\n");
			html +=("<option disabled>Job Position</option>\n");
			if(inputJobPosition.equals("Manager\n")) {
				html +=("<option selected value=\"Manager\">Manager</option>\n");
			}
			else {
				html +=("<option value=\"Manager\">Manager</option>\n");
			}
			if(inputJobPosition.equals("Receptionist")) {
				html +=("<option selected value=\"Receptionist\">Receptionist</option>\n");
			}
			else {
				html +=("<option value=\"Receptionist\">Receptionist</option>\n");
			}
			if(inputJobPosition.equals("Supervisor")) {
				html +=("<option selected value=\"Supervisor\">Supervisor</option>\n");
			}
			else {
				html +=("<option value=\"Supervisor\">Supervisor</option>\n");
			}
			html +=("</select><br><br>\n");
			html +=("<input type = \"submit\" value = \"Submit\" />&nbsp;<input type = \"submit\" value = \"Cancel\"/>\n");
			html +=("</form>\n");
			html +=("</body>\n");
			html +=("</html>\n");	
			
			
		}
		return html;
	}

}
