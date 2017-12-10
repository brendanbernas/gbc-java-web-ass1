package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import utility.*;
import utility.database.*;

@WebServlet("/GroupServlet")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public GroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		PrintWriter out = response.getWriter();
		
		out.println(ServletUtilities.headWithTitle("Group Entry"));
		out.println("<h1>Group Entry</h1>");
		out.println(ServletUtilities.bodyWithContent(defaultHTML()));
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String defaultHTML() {
		
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
				"        \r\n" + 
				"\r\n" + 
				"    </form>\r\n" + 
				"</div>";
		
	}
}
