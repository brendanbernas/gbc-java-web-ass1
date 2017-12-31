package utility.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class EmployeeDAO {

	public EmployeeDAO() {}
	
	public HashMap<Integer, String> getEmployeeNamesAndIdsByDepartment(int departmentId){
		Connection con = null;
		PreparedStatement selectEmployees = null;
		String sql = "SELECT id, first_name, last_name FROM employee WHERE department_id = ?";
		HashMap<Integer, String> outMap = new HashMap<Integer, String>();
		try {
			con = DatabaseAccess.connectDataBase();
			selectEmployees = con.prepareStatement(sql);
			selectEmployees.setInt(1, departmentId);
			ResultSet rs = selectEmployees.executeQuery();
			//get id and name and add it to HashMap
			while(rs.next()) {
				outMap.put(
						rs.getInt(1), 
						//"firstName lastName" as name
						rs.getString(2) + " " + rs.getString(3)
				);
			}
			rs.close();
			selectEmployees.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return the HashMap that was created
		//if no results from query, HashMap will be empty
		return outMap;
	}

}
