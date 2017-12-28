package utility.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import domain.Department;


public class DepartmentDAO {

	private java.sql.Connection connection;
	
	public DepartmentDAO() {
		try {
			connection = DatabaseAccess.connectDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return all Departments in ArrayList
	 * @return
	 */
	public List<Department> getAllDepartments(){
		List<Department> departments = new ArrayList<Department>();
		try {
			String query = "SELECT id, name, location FROM department";
			PreparedStatement pStatement = connection.prepareStatement(query);
			ResultSet result = pStatement.executeQuery();
			while(result.next()) {
				departments.add(
						new Department(result.getInt("id"), result.getString("name"), result.getString("location")
								));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return departments;
	}

}
