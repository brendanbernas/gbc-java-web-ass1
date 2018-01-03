/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Piotr Grabowski
 *Student Number: 100730728
 *Date: Dec 30,2017
 *Description: This servlet is the controller for the view employee page
 */
package servlet.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import utility.ServletUtilities;
import utility.database.DatabaseHelper;

@WebServlet("/EmployeeViewServlet")
public class EmployeeViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmployeeViewServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//database lookup all the employees using filter
		//save all the employees as a list
		//set the employees as a attribute 
		//forward to jsp page
		
		//ServletUtilities.tableHTMLEmployeeList(employees);
		String departmentID = request.getParameter("departmentid");
		request.setAttribute("employeelist",DatabaseHelper.getEmployeeList(departmentID));
	}

}
