/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Albert Nguyen
 *Student Number: 100865315
 *Date: Oct 18, 2017
 *Description: This servlet is used to validate and insert group member into a group
 */
package servlet.entry;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import utility.ServletUtilities;
import utility.database.DatabaseHelper;
import utility.departments.Department;


@WebServlet("/GroupProcess")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public GroupServlet() {
        super();
  
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		int a = 5;
		int b = a;
		
		String groupName = "";
		String department = "";
		String gMember1 = "";
		String gMember2 = "";
		String gMember3 = "";
		String gMember4 = "";
		String gMember5 = "";
		String gMember6 = "";
		
		
		try{
			groupName = request.getParameter("gName");
			department = request.getParameter("dName");
			gMember1 = request.getParameter("gMember1");
			gMember2 = request.getParameter("gMember2");
			gMember3 = request.getParameter("gMember3");
			gMember4 = request.getParameter("gMember4");
			gMember5 = request.getParameter("gMember5");
			gMember6 = request.getParameter("gMember6");
			
		}catch(NullPointerException e)
		{
			//In case a user tries to play around the url.
			response.sendRedirect("/COMP3095_PEGASUS/GroupForm");
			return;
		
		}
		//Put group members into a list
		List<String> group = ServletUtilities.populateGroupMembers(gMember1, gMember2, gMember3, gMember4, gMember5, gMember6);
		
		//Check validation and send error if failed
		if(!ServletUtilities.checkParameterExists(groupName)
			|| !ServletUtilities.checkParameterExists(department)
			|| group.size() <2
			|| ServletUtilities.checkMembersDuplicate(group)
			|| DatabaseHelper.hasGroupName(groupName)
			){
			String error = "<div class=\"alert alert-warning\"><p>";
			error += "The following errors below are";
			
			if(!ServletUtilities.checkParameterExists(groupName)){
				request.setAttribute("checkGroupName", true);
				error += "<br>Group Name cannot be empty";
			}
				
			if(!ServletUtilities.checkParameterExists(department)){
				request.setAttribute("checkDepartment", true);
				error += "<br>Department insertion failed";
			}
				
			if(group.size() > 1 && ServletUtilities.checkMembersDuplicate(group)){
				request.setAttribute("checkDuplication", true);
				error +="<br>No duplication of same member are allowed";
			}
				
			if(group.size() < 2){
				request.setAttribute("checkMember", true);
				error +="<br>Group must have two or more members";
			}
			
			if(DatabaseHelper.hasGroupName(groupName)){
				request.setAttribute("checkGroupName", true);
				error +="<br>Group name already taken!";
			}
			error += "</p></div>";
			request.setAttribute("error", error);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("GroupForm");
			requestDispatcher.include(request, response);
		}
		else{
			
			//Insert group into group table
			long gID = DatabaseHelper.insertGroup(groupName, department);
			
			//if group was not inserted
			if(gID ==-1) {
				throw new RuntimeException("AN ERROR HAS OCCURED IN THE DATABASE \n Group Insertion failed");
				
			}else{
			//Insert group member into group member table.	
			DatabaseHelper.insertGroupMember(gID,group);
			Department selectedDepartment = ServletUtilities.getDepartment(Integer.parseInt(department));
		
			request.setAttribute("groupID", gID);
			request.setAttribute("groupName", groupName);
			request.setAttribute("groupList", ServletUtilities.tableHTMLEmployeeList(DatabaseHelper.getGroupMembersList((int)(gID))));
			request.setAttribute("departID", selectedDepartment.getId());
			request.setAttribute("departmentName", selectedDepartment.getName());
			
			
			
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("GroupSuccess");
			requestDispatcher.include(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}
		
	

}
