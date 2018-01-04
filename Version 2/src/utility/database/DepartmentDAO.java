package utility.database;

import java.sql.Connection;
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
	
	public Department getDepartmentById(int id) {
		Department out = new Department();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT id, name, location FROM department WHERE id = ?";
		try {
			con = DatabaseAccess.connectDataBase();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				out.setId(rs.getInt(1));
				out.setName(rs.getString(2));
				out.setLocation(rs.getString(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * Get the department of a template
	 * @param id
	 * @return
	 */
	public Department getDepartmentByTemplateId(int id) {
		Department out = new Department();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT d.id, d.name, d.location FROM department d NATURAL JOIN report_template r WHERE r.id = ?";
		try {
			con = DatabaseAccess.connectDataBase();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				out.setId(rs.getInt(1));
				out.setName(rs.getString(2));
				out.setLocation(rs.getString(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

}
