package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.ServletUtilities;
import utility.database.DatabaseHelper;


@WebServlet("/GroupProcessing")
public class GroupEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public GroupEntry() {
        super();
  
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		String gName = request.getParameter("gName");
		String departID = request.getParameter("dName");
		String gM1 = request.getParameter("gMember1");
		String gM2 = request.getParameter("gMember2");
		String gM3 = request.getParameter("gMember3");
		String gM4 = request.getParameter("gMember4");
		String gM5 = request.getParameter("gMember5");
		String gM6 = request.getParameter("gMember6");
		String error ="";
		
		//put group member into a list
		List<String> groupList = ServletUtilities.populateGroupMembers(gM1, gM2, gM3, gM4, gM5, gM6);
	
		if(!ServletUtilities.checkParameterExists(gName)|| !ServletUtilities.checkParameterExists(departID) ||ServletUtilities.checkMembersDuplicate(groupList)) {
			if(!ServletUtilities.checkParameterExists(gName)) 
				error +="Group Name is empty\n";
			if(ServletUtilities.checkMembersDuplicate(groupList))
				error +="Duplicate members are not allowed\n";
			if(!ServletUtilities.checkParameterExists(departID))
				error +="No department was selected, Please populate your department on the department page";

				out.println(ServletUtilities.headWithTitle("Group Entry"));
				out.println("<h1>Group Entry</h1>");
				out.println(ServletUtilities.bodyWithContent(getInvalidHTML(error)));
		}else
		{
			
			long gID = DatabaseHelper.insertGroup(gName, departID);
			
			//if group id was not found
			if(gID ==-1) {
				out.println("AN ERROR HAS OCCURED");
				
			}else
			{
				DatabaseHelper.insertGroupMember(gID,groupList);
			
			out.println(ServletUtilities.headWithTitle("Group Entry"));
			out.println("<h1>Group Entry</h1>");
			out.println(ServletUtilities.bodyWithContent(getSuccessGroup(gName)));
			
			}
			
			
			
			
		}
	
		
		
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	private String getInvalidHTML(String error) {
		
		return "<div class=\"well text-center\">\r\n" + 
				"    <form action=\"GroupProcessing\">\r\n" + 
				"       \r\n" + 
				"        <div class=\"form-group\">\r\n" + 
				"            <label for=\"groupName\">Group Name: </label> <input id=\"groupName\" type=\"text\" name=\"gName\">\r\n" + 
				"        </div>\r\n" + 
				"        \r\n" + 
				"         <div class=\"form-group\">\r\n" + 
				"            <label for=\"departName\">Department:</label> \r\n" + 
				"            <select id=\"departName\"name=\"dName\">\r\n" + 
				"            	" + ServletUtilities.departmentDropDown()+
				"            </select> \r\n" + 
				"        </div>\r\n" + 
				"       \r\n" + 
				"      \r\n" + 
				"        \r\n" + 
				"        <div class=\"form-group\">\r\n" + 
				"            <label for=\"groupMember1\">GroupMember01</label> \r\n" + 
				"            <select id=\"groupMember1\"name=\"gMember1\">\r\n" + 
				"            	\r\n" + ServletUtilities.employeeDropDown()+
				"            </select> \r\n" + 
				"            \r\n" + 
				"             <label for=\"groupMember2\">GroupMember02</label> \r\n" + 
				"            <select id=\"groupMember2\"name=\"gMember2\">\r\n" + 
				"            	\r\n" + ServletUtilities.employeeDropDown()+
				"            </select> \r\n" + 
				"            \r\n" + 
				"             <label for=\"groupMember1\">GroupMember03</label> \r\n" + 
				"            <select id=\"groupMember3\"name=\"gMember3\">\r\n" + 
				"            	 <option value='none'>None</option>\r\n" + ServletUtilities.employeeDropDown()+
				"            </select> \r\n" + 
				"            \r\n" + 
				"        </div>\r\n" + 
				"        \r\n" + 
				"        <div class=\"form-group\">\r\n" + 
				"            <label for=\"groupMember4\">GroupMember04</label> \r\n" + 
				"            <select id=\"groupMember4\"name=\"gMember4\">\r\n" + 
				"            	 <option value='none'>None</option>\r\n" + ServletUtilities.employeeDropDown()+
				"            </select> \r\n" + 
				"            \r\n" + 
				"             <label for=\"groupMember5\">GroupMember05</label> \r\n" + 
				"            <select id=\"groupMember5\"name=\"gMember5\">\r\n" + 
				"            	 <option value='none'>None</option>\r\n" + ServletUtilities.employeeDropDown()+
				"            </select> \r\n" + 
				"            \r\n" + 
				"             <label for=\"groupMember6\">GroupMember06</label> \r\n" + 
				"            <select id=\"groupMember6\"name=\"gMember6\">\r\n" + 
				"            	 <option value='none'>None</option>\r\n" + ServletUtilities.employeeDropDown()+
				"            </select> \r\n" + 
				"            \r\n" + 
				"        </div>\r\n" + 
				"        \r\n" + 
				"        <div class=\"form-group\">\r\n" + 
				"            <input class=\"btn btn-default\" type=\"submit\">\r\n" + 
				"            <input class=\"btn btn-default\" type=\"reset\" value=\"Cancel\">\r\n" + 
				"        </div>\r\n" +
				"        <div class=\"alert alert-warning\">\r\n" + 
				"            <p>" + error + "</p>\r\n" + 
				"        </div>\r\n"+
				"        \r\n" + 
				"\r\n" + 
				"    </form>\r\n" + 
				"</div>";
		
	}
	private String getSuccessGroup(String gName) {
		
		return "<div class=\"well text-center\"> "
				+ "<h2>Department has been inserted</h2>"
				+ "Group Name:"+ gName +
				"</div>";
	}

}
