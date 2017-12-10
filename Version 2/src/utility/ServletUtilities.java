/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Brendan Bernas, Albert Nguyen
 *Student Number: 101012650, 100865315
 *Date: Oct 19,2017
 *Description: Some simple timesavers. Note that most are static methods.
 */
package utility;

import javax.servlet.http.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utility.EmployeeValidations;
import utility.database.DatabaseHelper;
import utility.departments.Department;
import utility.departments.DepartmentList;
import utility.employees.Employee;
import utility.employees.EmployeeList;

/** Some simple timesavers. Note that most are static methods.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */

public class ServletUtilities {
  public static final String DOCTYPE =
    "<!DOCTYPE html>";

  //output the head of an HTML document with specified title
  public static String headWithTitle(String title) {
    return( DOCTYPE + "\r\n" + 
    		"<html>\r\n" + 
    		"<head>\r\n" + 
    		"<meta charset=\"ISO-8859-1\">\r\n" + 
    		"<title>" + title + "</title>\r\n" + 
    		"<link href=\"css/bootstrap.css\" rel=\"stylesheet\">\r\n" + 
    		"</head>");
  }
  
  //output the body of and HTML document with specified (HTML) content in a String
  public static String bodyWithContent(String content) {
	  return "<body>\n" +
			  content + "\n" +
			  "</body>\n"+
			  "</html>";
  }

  /** Read a parameter with the specified name, convert it
   *  to an int, and return it. Return the designated default
   *  value if the parameter doesn't exist or if it is an
   *  illegal integer format.
  */
  
  public static int getIntParameter(HttpServletRequest request,
                                    String paramName,
                                    int defaultValue) {
    String paramString = request.getParameter(paramName);
    int paramValue;
    try {
      paramValue = Integer.parseInt(paramString);
    } catch(NumberFormatException nfe) { // null or bad format
      paramValue = defaultValue;
    }
    return(paramValue);
  }

  public static double getDoubleParameter
                                 (HttpServletRequest request,
                                  String paramName,
                                  double defaultValue) {
    String paramString = request.getParameter(paramName);
    double paramValue;
    try {
      paramValue = Double.parseDouble(paramString);
    } catch(NumberFormatException nfe) { // null or bad format
      paramValue = defaultValue;
    }
    return(paramValue);
  }
  
  /**Checks if a parameter value entered is either null or empty
   * Caller must pass the value and not the parameter name
   */
 
  public static boolean checkParameterExists(String paramValue)
  {
	  //return false on null or empty or space-filled string
	  //otherwise return true
	  return !(paramValue == null || paramValue.trim().equals(""));
  }
  
  /**Checks if a parameter entered contains a value that is neither
   * null or empty string. Caller must pass the request object 
   */
  
  public static boolean checkParameterExists(HttpServletRequest request, String paramName)
  {
	  //get parameter value from request and pass it to single arg checkParameterExists method
	  //return its findings
	  String paramValue = request.getParameter(paramName);
	  return ServletUtilities.checkParameterExists(paramValue);
  }

  /** Replaces characters that have special HTML meanings
   *  with their corresponding HTML character entities.
   */
  
  // Note that Javadoc is not used for the more detailed
  // documentation due to the difficulty of making the
  // special chars readable in both plain text and HTML.
  //
  // Given a string, this method replaces all occurrences of
  //  '<' with '&lt;', all occurrences of '>' with
  //  '&gt;', and (to handle cases that occur inside attribute
  //  values), all occurrences of double quotes with
  //  '&quot;' and all occurrences of '&' with '&amp;'.
  //  Without such filtering, an arbitrary string
  //  could not safely be inserted in a Web page.

  public static String filter(String input) {
    if (!hasSpecialChars(input)) {
      return(input);
    }
    StringBuffer filtered = new StringBuffer(input.length());
    char c;
    for(int i=0; i<input.length(); i++) {
      c = input.charAt(i);
      switch(c) {
        case '<': filtered.append("&lt;"); break;
        case '>': filtered.append("&gt;"); break;
        case '"': filtered.append("&quot;"); break;
        case '&': filtered.append("&amp;"); break;
        default: filtered.append(c);
      }
    }
    return(filtered.toString());
  }

  private static boolean hasSpecialChars(String input) {
    boolean flag = false;
    if ((input != null) && (input.length() > 0)) {
      char c;
      for(int i=0; i<input.length(); i++) {
        c = input.charAt(i);
        switch(c) {
          case '<': flag = true; break;
          case '>': flag = true; break;
          case '"': flag = true; break;
          case '&': flag = true; break;
        }
      }
    }
    return(flag);
  }
  
  public static String getNavigationBar(String pageUnder, String loginName)
  {
	  String htmlListItems = "";
	  String departmentsActive = "";
	  String employeeActive = "";
	  String groupActive = "";
	  String reportsActive = "";
	  String attendanceActive = "";
	  String htmlClass = "active";
	  
	  if(pageUnder.equals("Departments"))
	  {
		  departmentsActive = htmlClass;
	  }
	  else if(pageUnder.equals("Employees"))
	  {
		  employeeActive = htmlClass;
	  }
	  else if(pageUnder.equals("Group"))
	  {
		  groupActive = htmlClass;
	  }
	  else if(pageUnder.equals("Reports"))
	  {
		  reportsActive = htmlClass;
	  }
	  else if(pageUnder.equals("Attendance"))
	  {
		  attendanceActive = htmlClass;
	  }
	  
	  htmlListItems = 
				"<li class=\"" + departmentsActive + "\"><a href=\"Departments\">Departments</a></li>\r\n" + 
				"<li class=\"" + employeeActive + "\"><a href=\"Employees\">Employees</a></li>\r\n" + 
				"<li class=\"" + groupActive + "\"><a href=\"Group\">Group</a></li>\r\n" + 
				"<li class=\"" + reportsActive + "\"><a href=\"Reports\">Reports</a></li>\r\n" + 
				"<li class=\"" + attendanceActive + "\"><a href=\"Attendance\">Attendance</a></li>";
		  
	  
	  return "<nav class=\"navbar navbar-default\">\r\n" + 
	  		"    <div class=\"container-fluid\">\r\n" + 
	  		"        <ul class=\"nav navbar-nav\">\r\n" + 
	  					htmlListItems +
	  		"        </ul>\r\n" + 
	  		"        <ul class=\"nav navbar-nav navbar-right\">\r\n" + 
	  		"            <li><p class=\"navbar-text\">Hello "+ loginName +"</p></li>\r\n" + 
	  		"            <li><a href=\"Logout\"><span class=\"glyphicon glyphicon-log-out\"></span> Logout</a></li>\r\n" + 
	  		"        </ul>\r\n" + 
	  		"    </div>\r\n" + 
	  		"</nav>";
  }
  
//ALBERT'S CODE POPULATE employee DROPDOWN
  public static String employeeDropDown(String gM1) {
	  	
	  	String optionTag ="";
		EmployeeList<Employee> listInfo = DatabaseHelper.getEmployeeList();
		Employee tempEmployee = null;
		
		if(EmployeeValidations.isAlphabetic(gM1)) 
			gM1="-1";
		
		
		int check = Integer.parseInt(gM1);
		
		
		for(int i=0;listInfo.list.size()>i;i++)
		{
			tempEmployee = (Employee) listInfo.list.get(i);
			if(check == tempEmployee.getId())
				optionTag += "<option value='"+tempEmployee.getId()+"' selected> "+tempEmployee.getfName()+" "+tempEmployee.getlName()+" </option>\n";
			else
				optionTag += "<option value='"+tempEmployee.getId()+"'> "+tempEmployee.getfName()+" "+tempEmployee.getlName()+"</option>\n";
		}
		
		return optionTag;
		
	  
	  
  }
  
  //ALBERT'S CODE POPULATE employee DROPDOWN
  public static String departmentDropDown(String dID) {
	  	
	  	String optionTag ="";
		DepartmentList<Department> listInfo = DatabaseHelper.getDepartmentList();
		Department tempDepartment = null;
		
		if(!EmployeeValidations.tryParseInt(dID)) 
			dID="-1";
		
		int check = Integer.parseInt(dID);
		
		for(int i=0;listInfo.list.size()>i;i++)
		{
			tempDepartment = (Department) listInfo.list.get(i);
			
			if(tempDepartment.getId()==check)
				optionTag += "<option value='"+tempDepartment.getId()+"' selected> "+tempDepartment.getName()+"</option>\n";
			else
				optionTag += "<option value='"+tempDepartment.getId()+"'> "+tempDepartment.getName()+"</option>\n";
		}
		
		return optionTag;	  
	  
  }
  
  //ALBERT'S CODE insert group members into list
  public static List <String> populateGroupMembers(String gM1,String gM2,String gM3,String gM4,String gM5,String gM6) {
	  
	  List <String> temp = new ArrayList() ;
		temp.add(gM1);
		temp.add(gM2);
		if(!gM3.equals("none"))
			temp.add(gM3);
		if(!gM4.equals("none"))
			temp.add(gM4);
		if(!gM5.equals("none"))
			temp.add(gM5);
		if(!gM6.equals("none"))
			temp.add(gM6);
		
		return temp;
  }
  
  //ALBERT'S CODE checks if there are duplicate members
  public static boolean checkMembersDuplicate(List<String> groupMembers) {
	  
	  List<String> temp = groupMembers;
	
		
		for(int i=0;temp.size()>i;i++) {
		
			for(int j=i;temp.size()-1>j;j++) {
				if(temp.get(i).equals(temp.get(j+1))) {
					return true;
				}
			}
			
		}
		
		return false;
	  
  }
  //generates an HTML select list for specified years
  //can select a specific year passed to it
  public static String generateHtmlForYear(int selectedYear, int yearsToGenerate) {
	  //select opening tag
	  String out = "<select name = \"hiredDate\">\r\n";
	  out += "	<option selected disabled>Select Year</option>\r\n";
	  
	  //gets today's year
	  int year = new Date().getYear() + 1900;
	  //for will output an option for yearsToGenerate times
	  //ex: if 50 is passed, there will be 50 year options starting at this year
	  for(int i = 0; i < yearsToGenerate; i++) {
		  //if this is the selected year, output a this option is selected
		  if(selectedYear == year)
			  out += "	<option selected value=\""+year+"\">"+year+"</option>\r\n";
		  else
			  out += "	<option value=\""+year+"\">"+year+"</option>\r\n";
		  year--;
	  }
	  //close opening tag
	  out += "</select>";
	  return out;
  }
  
  //generates HTML select list for specified positions in String array
  //can select specified position
  public static String generateHtmlForPositions(String selectedPosition, String[] positions)
  {
	  String out = "<select name = \"jobPosition\">";
	  out += "	<option selected disabled>Select Position</option>";
	  for(String position : positions)
	  {
		  if(position.equals(selectedPosition))
			  out += "<option selected value=\"" + position + "\">" + position + "</option>";
		  else
			  out += "<option value=\"" + position +"\">" + position + "</option>";
	  }
	  out += "</select>";
	  return out;
  }
  //generating HTML select list with some default positions
  //selectedPosition will be selected
  public static String generateHtmlForPositions(String selectedPosition)
  {
	  //some default positions
	  String[] positions = {"General Staff", "Manager", "Reception", "Supervisor"};
	  return ServletUtilities.generateHtmlForPositions(selectedPosition, positions);
  }
  
  //returns a red "*" if passed true, otherwise just ""
  public static String getHtmlInputError(boolean isError) 
  {
    if(isError)
	  return "<span style=\"color: red;\">*</span>";
	return "";
  }
  
  //checks if specified session attribute exists
  //passing request and attribute name
  public static boolean doesSessionAttributeExist(HttpServletRequest request, String attributeName)
  {
	  HttpSession session = request.getSession();
	  if(session.getAttribute(attributeName) != null)
		  return true;
	  return false;
  }
  
  //checks if specified session attribute exists
  //passing session and attribute name
  public static boolean doesSessionAttributeExist(HttpSession session, String attributeName)
  {
	  if(session.getAttribute(attributeName) != null)
		  return true;
	  return false;
  }
  
  //use this method to sendRedirect if a cookie has been set in the same page
  public static void sendRedirect(HttpServletResponse response, String url)
  {
		//bug with Tomcat
		//solution found here: http://www.jguru.com/faq/view.jsp?EID=53251
		//if using response.sendRedirect() any cookies in this servlet will not be stored in the browser
		response.setStatus(HttpServletResponse.SC_FOUND);
		response.setHeader("Location", url); 
  }
}