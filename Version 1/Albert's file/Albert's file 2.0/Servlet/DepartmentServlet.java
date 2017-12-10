package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import java.io.PrintWriter;
import utility.*;
import utility.database.*;


@WebServlet("/DepartmentsProcessing")
public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DepartmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		PrintWriter out = response.getWriter();
		String departName = request.getParameter("dName");
		String location = request.getParameter("location");
		String error ="";
		
		//If inputs is not null or empty
		if(ServletUtilities.checkParameterExists(departName) && ServletUtilities.checkParameterExists(location)) {		
			DatabaseHelper.insertDepartment(departName, location);
			
			out.println(ServletUtilities.headWithTitle("Enter Department"));
			out.println("<h1 class=\"text-center\">Departments</h1>");
			out.println(ServletUtilities.bodyWithContent(getSuccessDepartment(departName,location)));
			
		}
		//show errors of inputs
		else {
			if(!ServletUtilities.checkParameterExists(departName))
					error += "Department cannot be empty";
			if(!ServletUtilities.checkParameterExists(location))
					error += "<br>Location cannot be empty";
			
			out.println(ServletUtilities.headWithTitle("Enter Department"));
			out.println("<h1 class=\"text-center\">Departments</h1>");
			out.println(ServletUtilities.bodyWithContent(getDepartmentForm(departName,location, error)));
		}
			
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	private String getDepartmentForm(String deptName,String location, String error) {
		
		return "<div class=\"well text-center\">\r\n" + 
				"    <form action=\"DepartmentsProcessing\">\r\n" + 
				"        <div class=\"form-group\">\r\n" + 
				"            <label for=\"name\">Department Name:</label> <input id=\"name\" type=\"text\" name=\"dName\" value =\""+deptName+"\">\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"form-group\">\r\n" + 
				"            <label for=\"location\">Department Location/Floor:</label> <input id=\"location\" type=\"text\" name=\"location\" value =\""+deptName+"\">\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"form-group\">\r\n" + 
				"            <input class=\"btn btn-default\" type=\"submit\">\r\n" + 
				"            <input class=\"btn btn-default\" type=\"reset\" value=\"Cancel\">\r\n" + 
				"        <div class=\"alert alert-warning\">\r\n" + 
				"            <p>" + error + "</p>\r\n" + 
				"        </div>\r\n" + 
				"        </div>\r\n" + 
				"\r\n" + 
				"    </form>\r\n" + 
				"</div>";	
	}
	
	private String getSuccessDepartment(String departName,String location) {
		
		return "<div class=\"well text-center\"> "
				+ "<h2>Department has been inserted</h2>"
				+ "Department Name:"+ departName +
				"\n Department Location: "+location+
				"</div>";
	}
	

}
