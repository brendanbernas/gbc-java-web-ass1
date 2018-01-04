/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Piotr Grabowski
 *Student Number: 100730728
 *Date: Dec 30,2017
 *Description: This servlet is the controller for the view employee page
 */
package servlet.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import utility.ServletUtilities;
import utility.database.DatabaseHelper;

@WebServlet("/EmployeeViewProcess")
public class EmployeeViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmployeeViewServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String departmentID = request.getParameter("department");
		int departmentId = 0;
		if(departmentID != null) {
			departmentId = Integer.parseInt(departmentID);
		}
		request.setAttribute("employeelist",ServletUtilities.tableHTMLEmployeeList(DatabaseHelper.getEmployeeList(departmentId)));
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("EmployeeView");
		requestDispatcher.include(request,response);
		
	}

}
