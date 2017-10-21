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

import helperClasses.HTML_Tags;
import helperClasses.Numbers;
import helperClasses.Validations;

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
		boolean misslName = false;
		boolean missEmployeeNum = false;
		boolean missEmailEmpty = false;
		boolean missEmailFormat = false;
		boolean missHiredYear = false;
		boolean missJobPosition = false;
		PrintWriter pw = response.getWriter();
		
		response.setContentType("text/html");
		
		if(Validations.isMissingOrEmpty(inputfName)) {
			validated = false;
			missfName = true;
		}
		else {
			
		}
		
		if(Validations.isMissingOrEmpty(inputlName)) {
			validated = false;
			misslName = true;
		}
		else {
			
		}
		
		if(Validations.isMissingOrEmpty(inputEmployeeNum)) {
			validated = false;
			missEmployeeNum = true;
		}
		else {
			if(Numbers.tryParseInt(inputEmployeeNum) == true){
				employeeNum = Integer.parseInt(inputEmployeeNum);
			}
		}
		
		if(Validations.isMissingOrEmpty(inputEmail)) {
			validated = false;
			missEmailEmpty = true;
			
		}
		else if(Validations.isEmail(inputEmail) == false) {
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
		
		
		if(validated) {
			/*try {
				
			}
			catch(IOException){
			}
			*/
			//open connection
			//https://www.tutorialspoint.com/servlets/servlets-database-access.html
			java.sql.Date sqlDate = new java.sql.Date(dateHired);
			String query = "INSERT INTO EMPLOYEE_T(ID,EmployeeFirst,EmployeeLast,EmployeeEmail,DateHired,EmployeePosition) VALUES(?,?,?,?,?,?)";
			/*PreparedStatement dbStatement = dbConnect.prepareStatement(query);
		    dbStatement.setInt(1,employeeNum);
			dbStatement.setString(2, inputfName);
			dbStatement.setString(3, inputlName);
			dbStatement.setString(4,inputEmail);
			dbStatement.setDate(5,sqlDate);
			dbStatement.setString(6,inputJobPosition);
			dbStatement.executeQuery(query);
			*/
			pw.print(HTML_Tags.headWithTitle("Employee") + "<h1> Successfully added " + inputfName + " " + inputlName + "</h1>");
			
		}
		else {
			pw.println(HTML_Tags.headWithTitle("Employee"));
			pw.println("<body bgcolor=\"A0ED97\">");
			pw.println("<h1>Employee</h1>");
			pw.println("<form method = \"post\" action = \"http://localhost:8080/COMP3095_Ass1/Employee\">");
			if(missfName == true) {
				//not supported in html 5; link to css
				pw.println("First Name: <input type = \"text\" name = \"fName\"/> <font size=\"3\" color=\"red\">*</font> <br>");
			}
			else {
			pw.println("First Name: <input type = \"text\" name = \"fName\" value = \"" + inputfName + "\"/><br>");
			}
			if(misslName == true) {
				//not supported in html 5; link to css
				pw.println("Last Name: <input type = \"text\" name = \"lName\"/> <font size=\"3\" color=\"red\">*</font> <br><br>");
			}
			else {
			pw.println("Last Name: <input type = \"text\" name = \"lName\" value = \"" + inputlName + "\"/><br><br>");
			}
			//check if integer parse
			if(missEmployeeNum == true) {
				//not supported in html 5; link to css
				pw.println("Employee#: <input type = \"text\" name = \"employeeNum\"/> <font size=\"3\" color=\"red\">*</font> <br><br>");
			}
			else {
			pw.println("Employee#: <input type = \"text\" name = \"employeeNum\" value = \"" + inputEmployeeNum + "\"/><br><br>");
			}
			if(missEmailEmpty == true) {
				//not supported in html 5; link to css
				pw.println("Email: <input type = \"text\" name = \"email\"/> <font size=\"3\" color=\"red\">*</font> <br>");
			}
			else if(missEmailFormat == true) {
				//not supported in html 5; link to css
				pw.println("Email: <input type = \"text\" name = \"email\" value = \"" + inputEmail + "\"/> <font size=\"3\" color=\"red\">That is an invalid email (e.g someone@address.com)</font> <br>");
			}
			else {
			pw.println("Email: <input type = \"text\" name = \"email\" value = \"" + inputEmail + "\"/><br>");	
			}
			if(missHiredYear == true) {
				//not supported in html 5; link to css
				pw.println("<font size=\"3\" color=\"red\">Choose the year you were hired below</font>");
			}
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
			pw.println("/select><br><br>");
			if(missJobPosition == true) {
				//not supported in html 5; link to css
				pw.println("<font size=\"3\" color=\"red\">Choose the job position below</font>");
			}
			pw.println("<select name = \"jobPosition\"");
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
	}

}
