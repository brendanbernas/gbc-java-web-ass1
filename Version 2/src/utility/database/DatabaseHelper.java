/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Brendan Bernas, Albert Nguyen
 *Student Number: 101012650, 100865315
 *Date: Oct 19,2017
 *Description: Some simple timesavers for database statements/validations
 */
package utility.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utility.employees.*;
import utility.departments.*;

public class DatabaseHelper {
	//check if username and password exist in database
	//if they do exist return true, else false
	public boolean authenticateLogin(String username, String password) {
		String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
		try {
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, username);
			pStatement.setString(2, password);
			ResultSet result = pStatement.executeQuery();
			if(result.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;		
	}
	
	//check if the unique user id (stored in cookie) exists
	public static boolean authenticateLogin(String uuid)
	{
		String query = "SELECT * FROM logged_in WHERE id = ?";
		
		try {
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, uuid);
			ResultSet result = pStatement.executeQuery();
			if(result.next())
			{
				return true;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//returns id from admin table from username
	public static int getUserId(String username)
	{
		String query = "SELECT * FROM admin WHERE username = ?";
		try {
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, username);
			ResultSet result = pStatement.executeQuery();
			if(result.next())
				return result.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;	
	}
	
	/*
	 * If the unique identifier exists in Logged_In_T it will return the user id of the associated user
	 */
	public static int getUserIdFromUUID(String uniqueId)
	{
		String query = "SELECT * FROM logged_in WHERE id = ?";
		try {
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, uniqueId);
			ResultSet result = pStatement.executeQuery();
			if(result.next())
				//unique id exists return true
				return result.getInt("admin_id");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	//add a logged in user in Logged_In_T
	//uniqueId is generated on servlet and passed here
	public static void insertLoggedInUser(int userId, String uniqueId)
	{
		String query = "INSERT INTO logged_in (id, admin_id) values (?,?)";
		try {
			
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, uniqueId);
			pStatement.setInt(2, userId);
			pStatement.executeUpdate();
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//remove a logged in user from Logged_In_T
	//typically called when user logs out
	public static void removeLoggedInUser(String uniqueId)
	{
		String query = "DELETE FROM logged_in WHERE id = ?";
		try {
			
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, uniqueId);
			pStatement.executeUpdate();
			pStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//get user's full name from the database from username and password
	public String getUserFullName(String username, String password)
	{
		String query = "SELECT first_name, last_name FROM admin WHERE username = ? AND password = ?";
		try {
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, username);
			pStatement.setString(2, password);
			ResultSet result = pStatement.executeQuery();
			if(result.next())
				return result.getString("first_name") + " " + result.getString("last_name");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	//get user full name from user id
	public static String getUserFullName(int userId)
	{
		String query = "SELECT first_name, last_name FROM admin WHERE id = ?";
		try {
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setInt(1, userId);
			ResultSet result = pStatement.executeQuery();
			if(result.next())
				return result.getString("first_name") + " " + result.getString("last_name");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	//ALBERT'S CODE this will insert department
	public static Long insertDepartment (String departName, String location){
		
		String query = "INSERT INTO DEPARTMENT_T (DepartmentName, DepartmentLocation) values (?,?)";
		ResultSet rs = null;
		long gID = -1;
		try {
			
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, departName);
			pStatement.setString(2, location);
			
			pStatement.executeUpdate();
			rs = pStatement.getGeneratedKeys();
		
			if(rs.next())
				gID = rs.getLong(1);
			else
				throw new SQLException("ERROR DEPARTMENT WAS NOT INSERTED PROPERLY");
			
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return gID;
	}
	
	//ALBERT'S CODE this will check if group name is already taken
	public static boolean hasGroupName(String gName) {
		
		String query = "SELECT GroupName FROM group_T WHERE GroupName = ?";
		boolean check = false;
		try {
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, gName);
			ResultSet result = pStatement.executeQuery();
			if(result.next())
				check = true;
			
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
		
	//ALBERT'S CODE return all employees as list
	public static List<Employee> getEmployeeList() {
		
		String query = "SELECT * FROM employee";
		List<Employee> tempList = new ArrayList<Employee>();
		
		try {
			ResultSet rs = null;
			PreparedStatement fStatement =  DatabaseAccess.connectDataBase().prepareStatement(query);	
			
			rs = fStatement.executeQuery();
			
			while(rs.next()) {	
				
				tempList.add(
						new Employee(
								rs.getInt("EmployeeID"),rs.getString("EmployeeFirst"),rs.getString("EmployeeLast")
								,rs.getString("EmployeeEmail"),rs.getDate("DateHired"),"EmployeePosition")
						);			
			}		
			fStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return tempList;	
	}
	
	//ALBERT'S CODE return all employees as list - MODIFIED BY PETER get employees by department
		public static List<Employee> getEmployeeList(int departmentID) {
			
			String query = "SELECT * FROM employee WHERE department_id = ?";
			
			List<Employee> tempList = new ArrayList<Employee>();
			
			try {
				ResultSet rs = null;
				PreparedStatement fStatement =  DatabaseAccess.connectDataBase().prepareStatement(query);	
				fStatement.setInt(1, departmentID);
				rs = fStatement.executeQuery();
				
				while(rs.next()) {	
					
					tempList.add(
							new Employee(
									rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name")
									,rs.getString("email"),rs.getDate("date_hired"),rs.getString("position"))
							);			
				}		
				fStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
				return tempList;	
		}
	
	
	//ALBERT'S CODE return all department as list
	public static List<Department> getDepartmentList() {
		
		String query = "select * from department";
		List<Department> tempList = new ArrayList<Department>();
		
		try {
			ResultSet rs = null;
			PreparedStatement fStatement =  DatabaseAccess.connectDataBase().prepareStatement(query);	

			rs = fStatement.executeQuery();
	
			while(rs.next()) {
				
				tempList.add(new Department(rs.getInt("id"),rs.getString("name"),rs.getString("location")));
			}	
			fStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempList;
	}
	
	
	//ALBERT'S CODE will insert Group
	public static long insertGroup (String gName, String departID){
		
		
		int dID = Integer.parseInt(departID);
		ResultSet rs = null;
		long gID = -1;
		String query = "INSERT INTO group_t (GroupName,GroupDepartmentID) values (?,?)";
		
		try {
			
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, gName);
			pStatement.setInt(2, dID);
			pStatement.executeUpdate();
			
			rs=pStatement.getGeneratedKeys();
			if(rs.next()) {
				gID = rs.getLong(1);
			}	
			pStatement.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return gID;
	}
	
	//ALBERT'S CODE will insert Group Members
	public static void insertGroupMember (Long groupID, List<String> employeeID){
		
		String query = "INSERT INTO groupmembers_T (GroupID,EmployeeID) values (?,?)";
		int gID = (int)(long) groupID;
		

		
		try {
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			
			for(int i=0;employeeID.size()>i;i++) {
				
				pStatement.setInt(1, gID);
				pStatement.setInt(2, Integer.parseInt(employeeID.get(i)));
				pStatement.executeUpdate();
				
			}

			pStatement.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	  //ALBERT'S CODE
	  public static List<Employee> getGroupMembersList(int groupID){
		  
		  	List<Employee> tempList = new ArrayList<Employee>();
			String query = "select e.* from employee_T e join groupmembers_T g on e.EmployeeID = g.EmployeeID where g.GroupID = ?";

			
			try {
				ResultSet rs = null;
				PreparedStatement fStatement =  DatabaseAccess.connectDataBase().prepareStatement(query);	
				fStatement.setInt(1, groupID);
				
				rs = fStatement.executeQuery();
				
				while(rs.next()) {	
					
					tempList.add(
							new Employee(
									rs.getInt("EmployeeID"),rs.getString("EmployeeFirst"),rs.getString("EmployeeLast")
									,rs.getString("EmployeeEmail"),rs.getDate("DateHired"),"EmployeePosition")
							);			
				}		
				fStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
				return tempList;	
	  }
}
