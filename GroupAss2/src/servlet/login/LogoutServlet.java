/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Brendan Bernas
 *Student Number: 101012650
 *Date: Oct 19,2017
 *Description: This servlet is used to log out the client, this also invalidates their session and redirects them back to the login page
 *			   It will also remove their remember-me cookie if it is set and remove it from the database
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

import utility.ServletUtilities;
import utility.database.DatabaseHelper;


@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LogoutServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get the session
		HttpSession session = request.getSession();
		//destroy the session
		session.invalidate();
		
		//get remember me cookie if exists
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
		if(cookieRemember != null)
		{
			//if remember me cookie exists
			//remove logged in user from database
			DatabaseHelper.removeLoggedInUser(cookieRemember.getValue());
			//delete cookie
			cookieRemember.setMaxAge(0);
			response.addCookie(cookieRemember);
		}
		//send user back to login
		//using this method in case cookie is set
		ServletUtilities.sendRedirect(response, "Login");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
