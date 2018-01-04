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

//ALBERT'S CODE this will insert department
	public static Long insertDepartment (String departName, String location){

		String query = "INSERT INTO DEPARTMENT (Name, Location) values (?,?)";
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
	
	//ALBERT'S CODE this will insert department
		public static Long insertDepartment (String departName, String location){
			
			String query = "INSERT INTO DEPARTMENT (Name, Location) values (?,?)";
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
		
		//*ALBERT'S CODE return all department as list
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

		return out;
	}
}
