package utility.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;

import utility.employees.*;
import utility.departments.*;

public class DatabaseHelper {
	public boolean authenticateLogin(String username, String password) {
		String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
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
	
	//ALBERT'S CODE this will insert department
	public static void insertDepartment (String departName, String location){
		
		String query = "INSERT INTO DEPARTMENT_T (DepartmentName, DepartmentLocation) values (?,?)";
		try {
			
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setString(1, departName);
			pStatement.setString(2, location);
			pStatement.executeUpdate();
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	//ALBERT'S CODE this return all employees as list USED BY ServletUtilities.employeDropdown() NOTE: I cannot use [PREPARE STATEMENT] to execute this method for some reason, hence [STATEMENT] is used instead.
	public static EmployeeList<Employee> getEmployeeList() {
		
		String query = "SELECT * FROM employee_t";
		EmployeeList<Employee> tempList = new EmployeeList<Employee>();
		int count=0;
		
		try {
			ResultSet rs = null;
			Statement fStatement =  DatabaseAccess.connectDataBase().createStatement();	
			rs = fStatement.executeQuery(query);
			
			while(rs.next()) {	
				
				tempList.add(new Employee());			
				tempList.list.get(count).setId(rs.getInt("EmployeeID"));
				tempList.list.get(count).setfName(rs.getString("EmployeeFirst"));
				tempList.list.get(count).setlName(rs.getString("EmployeeLast"));
				tempList.list.get(count).setEmail(rs.getString("EmployeeEmail"));
				tempList.list.get(count).setDateHired(rs.getDate("DateHired"));
				tempList.list.get(count).setPosition(rs.getString("EmployeePosition"));
				count++;
			}		
			fStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return tempList;	
	}
	
	//ALBERT'S CODE this return all department as list USED BY ServletUtilities.departmentDropDown() NOTE: I cannot use [PREPARE STATEMENT] to execute this method for some reason, hence [STATEMENT] is used instead.
	public static DepartmentList<Department> getDepartmentList() {
		
		String query = "SELECT * FROM department_t";
		DepartmentList<Department> tempList = new DepartmentList<Department>();
		int count=0;
		
		try {
			ResultSet rs = null;
			Statement fStatement =  DatabaseAccess.connectDataBase().createStatement();	
			rs = fStatement.executeQuery(query);
	
			while(rs.next()) {	

				tempList.add(new Department());
				tempList.list.get(count).setId(rs.getInt("DepartmentID"));
				tempList.list.get(count).setName((rs.getString("DepartmentName")));
				tempList.list.get(count).setLocation((rs.getString("DepartmentLocation")));
				count++;
				
			}	
			fStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempList;
	}
	
	
	//ALBERT'S CODE this will insert Group
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
	
	//ALBERT'S CODE this will insert Group Members
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
}
