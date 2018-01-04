/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Albert Nguyen
 *Student Number: 100865315
 *Date: Jan 04, 2017
 *Description: This servlet is used to inspect department
 */
package servlet.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.ServletUtilities;
import utility.database.DatabaseHelper;
import utility.database.DepartmentDAO;

/**
 * Servlet implementation class DepartmentViewServlet
 */
@WebServlet("/DepartmentViewServlet")
public class DepartmentViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepartmentViewServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		request.setAttribute("departmentList", ServletUtilities.tableHTMLDepartmentList(DepartmentDAO.getDepartmentList()));
	
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("DepartmentView");
		requestDispatcher.include(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
