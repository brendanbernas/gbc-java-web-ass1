/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Brendan Bernas
 *Student Number: 101012650
 *Date: Oct 19,2017
 *Description: This servlet is used to validate the submitted parameters from the client, and give access to the entry pages to the client
 */
package servlet.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

import utility.ServletUtilities;
import utility.database.DatabaseHelper;

@WebServlet("/Authentication")
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AuthenticationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get username and password and remember params
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		String remember = request.getParameter("remember");
		
		//look for remember-me cookie
		Cookie cookieRemember = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null)
		{
			for(Cookie c : cookies)
			{
				if(c.getName().equals("remember-me"))
					cookieRemember = c;
			}			
		}
		//if remember me cookie exists
		if(cookieRemember != null)
		{
			//get user from remember-cookie
			if(DatabaseHelper.authenticateLogin(cookieRemember.getValue()))
			{
				//get user id
				int userId = DatabaseHelper.getUserIdFromUUID(cookieRemember.getValue());
				//create session obj with user id
				HttpSession session = request.getSession();
				//storing string object for use in later pages
				//contains the full name of the user
				session.setAttribute("user", DatabaseHelper.getUserFullName(userId));
				response.sendRedirect("Landing");
			}
			else
			{
				//cookie has expired, must remove it
				cookieRemember.setMaxAge(0);
				response.addCookie(cookieRemember);
				//response.sendRedirect("Login?errNo=3");
				request.setAttribute("errorMessage", "Remember me has expired, you must login again");
				request.getRequestDispatcher("/Login").forward(request, response);
			}
			return;
		}
		
		if(ServletUtilities.checkParameterExists(username) && ServletUtilities.checkParameterExists(password))
		{
			//create session and redirect to home
			DatabaseHelper db = new DatabaseHelper();
			boolean userExists = db.authenticateLogin(username, password);
			if(userExists)
			{
				//user exists in the database
				HttpSession session = request.getSession();
				//storing string object for use in later pages
				//contains the full name of the user
				session.setAttribute("user", db.getUserFullName(username, password));
				if(ServletUtilities.checkParameterExists(remember) && remember.equals("on"))
				{
					//remember me is checked
					//set a cookie with unique id to last four weeks
					String uniqueId = UUID.randomUUID().toString();
					Cookie rememberCookie = new Cookie("remember-me", uniqueId);
					rememberCookie.setMaxAge(60*60*24*7);
					response.addCookie(rememberCookie);
					int userId = DatabaseHelper.getUserId(username);
					if(userId != 0)
						DatabaseHelper.insertLoggedInUser(userId, uniqueId);
				}
				//bug with Tomcat
				//solution found here: http://www.jguru.com/faq/view.jsp?EID=53251
				//if response.sendRedirect() any cookies in this servlet will not be stored in the browser
				ServletUtilities.sendRedirect(response, "Landing");
				return;
			}
		}
		//bad login
		//send user back to login page
		//response.sendRedirect("Login?errNo=2");
		request.setAttribute("errorMessage", "Error Bad Login: Username or password is incorrect");
		request.getRequestDispatcher("/Login").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
