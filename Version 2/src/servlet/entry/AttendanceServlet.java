package servlet.entry;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.EmployeeValidations;
import utility.ServletUtilities;
import utility.database.DatabaseHelper;
import utility.employees.Employee;
import utility.database.AttendanceDAO;

/**
 * Servlet implementation class AttendanceServlet
 */
@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AttendanceServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int departmentID = -1;
		if(EmployeeValidations.tryParseInt(request.getParameter("dName")))
			departmentID = Integer.parseInt(request.getParameter("dName"));
		
		if(request.getParameter("submit") != null && departmentID>-1){
			String [] attended = request.getParameterValues("checkAttendance");
			List<Employee> employeeCheck = DatabaseHelper.getEmployeeListByDepartment(departmentID);
			
			Date date = null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = formatter.parse(request.getParameter("date"));
		
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			int attendanceID = (int) AttendanceDAO.insertAttendance(date);
			AttendanceDAO.employeeAttendnaceCheck(attendanceID, employeeCheck, attended);
			request.setAttribute("attendanceID",Integer.toString(attendanceID));
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("attendance_success.jsp");
			requestDispatcher.include(request, response);
			
		}else if (request.getParameter("submit") != null){
			if(departmentID==-1)
				request.setAttribute("noDepartment", true);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("attendance_form.jsp");
			requestDispatcher.include(request, response);
			return;
			
		}
		else{
		
		request.setAttribute("firstLoad", false);
		request.setAttribute("employeeList", ServletUtilities.tableHTMLEmployeeCheckList(DatabaseHelper.getEmployeeListByDepartment(departmentID)));	
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("attendance_form.jsp");
		requestDispatcher.include(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
