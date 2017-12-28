package servlet.entry;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.ServletUtilities;


@WebServlet("/ReportTemplateEntry")
public class ReportTemplateEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReportTemplateEntry() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//first check for authorization
		HttpSession session = request.getSession();
		//if "user" attribute does not exist forward to login page with error message showing
		if(!ServletUtilities.doesSessionAttributeExist(session, "user"))
		{
			ServletUtilities.forwardToLoginWithErrorMessage(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
