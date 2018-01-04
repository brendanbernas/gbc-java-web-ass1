/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Albert Nguyen
 *Student Number: 100865315
 *Date: Jan 04, 2017
 *Description: This servlet is used to inspect Group
 */
package servlet.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Department;
import domain.Group;
import utility.EmployeeValidations;
import utility.ServletUtilities;
import utility.database.DatabaseHelper;
import utility.database.GroupDAO;

/**
 * Servlet implementation class GroupViewServlet
 */
@WebServlet("/GroupViewServlet")
public class GroupViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GroupViewServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if(request.getParameter("inspect") != null){
			int groupID = Integer.parseInt(request.getParameter("inspect"));
			Group selectedGroup = ServletUtilities.getGroup(groupID);
			Department selectedDepartment = ServletUtilities.getDepartment(selectedGroup.getDepartmentID());
			
			request.setAttribute("groupID", selectedGroup.getId());
			request.setAttribute("groupName", selectedGroup.getName());
			request.setAttribute("groupList", ServletUtilities.tableHTMLEmployeeList(DatabaseHelper.getGroupMembersList((int)(selectedGroup.getId()))));
			request.setAttribute("departID", selectedDepartment.getId());
			request.setAttribute("departmentName", selectedDepartment.getName());
			request.setAttribute("inspect", true);
					
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("GroupSuccess");
			requestDispatcher.include(request, response);
			return;
		}
		int departmentID = -1;
		if(EmployeeValidations.tryParseInt(request.getParameter("dName")))
			departmentID = Integer.parseInt(request.getParameter("dName"));
		
		request.setAttribute("groupList", ServletUtilities.tableHTMLGroupList(GroupDAO.getGroupsListByDepartment(departmentID)));		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("GroupView");
		requestDispatcher.include(request, response);
		
		
		
		
		
		
		
		
		
		
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
