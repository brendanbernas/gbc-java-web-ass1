//Brendan Bernas, 101012650, COMP3095

package utility;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;
import utility.database.*;
import utility.departments.*;
import utility.employees.*;
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

  public static String headWithTitle(String title) {
    return( DOCTYPE + "\r\n" + 
    		"<html>\r\n" + 
    		"<head>\r\n" + 
    		"<meta charset=\"ISO-8859-1\">\r\n" + 
    		"<title>" + title + "</title>\r\n" + 
    		"<link href=\"css/bootstrap.css\" rel=\"stylesheet\">\r\n" + 
    		"</head>");
  }
  
  public static String bodyWithContent(String content) {
	  return "<body>\n" +
			  content + "\n" +
			  "</body>\n"+
			  "</html>";
  }
  
  //ALBERT'S CODE POPULATE employee DROPDOWN
  public static String employeeDropDown() {
	  	
	  	String optionTag ="";
		EmployeeList<Employee> listInfo = DatabaseHelper.getEmployeeList();
		Employee tempEmployee = null;
		
		for(int i=0;listInfo.list.size()>i;i++)
		{
			tempEmployee = (Employee) listInfo.list.get(i);
			optionTag += "<option value='"+tempEmployee.getId()+"'> "+tempEmployee.getfName()+" "+tempEmployee.getlName()+"</option>\n";
		}
		
		return optionTag;
		
	  
	  
  }
  
  //ALBERT'S CODE POPULATE employee DROPDOWN
  public static String departmentDropDown() {
	  	
	  	String optionTag ="";
		DepartmentList<Department> listInfo = DatabaseHelper.getDepartmentList();
		Department tempDepartment = null;
		
		for(int i=0;listInfo.list.size()>i;i++)
		{
			tempDepartment = (Department) listInfo.list.get(i);
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
}