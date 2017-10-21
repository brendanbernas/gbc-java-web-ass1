package utility.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		
	
	
}
