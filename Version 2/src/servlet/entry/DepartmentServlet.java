/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Albert
 *Student Number:
 *Date: Oct 18, 2017
 *Description: This servlet is used to display the departments entry form, validate the submitted parameters from the client, and store the client's entry into the database
 */
package servlet.entry;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.io.PrintWriter;
import utility.ServletUtilities;
import utility.database.*;


@WebServlet("/DepartmentProcess")
public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DepartmentServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.setContentType("text/html");
		
		/*
			Insert session login
		*/
		
		
		String departName = request.getParameter("dName");
		String location = request.getParameter("location");
		
		//if input are valid insert department and redirect to DepartmentSuccess.jsp
		if(ServletUtilities.checkParameterExists(departName) && ServletUtilities.checkParameterExists(location)){
			
			Long departID = DatabaseHelper.insertDepartment(departName, location);
			request.setAttribute("departID", departID);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("DepartmentSuccess");
			requestDispatcher.include(request, response);
			
		}else{
			//Check Department name
			if(!ServletUtilities.checkParameterExists(departName)){
				request.setAttribute("nameError", true);
			}
			//Check Department location
			if(!ServletUtilities.checkParameterExists(location)){
				request.setAttribute("locationError", true);
			}
			
			//redirect previous page
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("DepartmentForm");
			requestDispatcher.include(request, response);
			
			
		}
		
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

	

}
