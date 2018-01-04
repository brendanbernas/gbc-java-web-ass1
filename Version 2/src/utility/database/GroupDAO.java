package utility.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Group;

public class GroupDAO {
	//ALBERT'S CODE this will check if group name is already taken
	public static boolean hasGroupName(String gName) {
		
		String query = "SELECT Name FROM groups WHERE name = ?";
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
	
	//ALBERT'S CODE will insert Group
	public static long insertGroup (String gName, String departID){
		
		
		int dID = Integer.parseInt(departID);
		ResultSet rs = null;
		long gID = -1;
		String query = "INSERT INTO groups (department_ID,name) values (?,?)";
		
		try {
			
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setInt(1, dID);
			pStatement.setString(2, gName);
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
		
		String query = "INSERT INTO group_member (group_ID,employee_ID) values (?,?)";
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
	  //ALBERT's CODE get all group
	  public static List<Group> getGroupsList() {
			
			String query = "select * from groups";
			List<Group> tempList = new ArrayList<Group>();
			
			try {
				ResultSet rs = null;
				PreparedStatement fStatement =  DatabaseAccess.connectDataBase().prepareStatement(query);	

				rs = fStatement.executeQuery();
		
				while(rs.next()) {
					
					tempList.add(new Group(rs.getInt("id"),rs.getInt("department_id"),rs.getString("name")));	
				}	
				fStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return tempList;
		}
	  
	  //ALBERT'S CODE
	  public static List<Group> getGroupsListByDepartment(int departmentID) {
			
			String query = "SELECT * FROM groups WHERE department_id = ?";
			List<Group> tempList = new ArrayList<Group>();
			
			try {
				ResultSet rs = null;
				PreparedStatement fStatement =  DatabaseAccess.connectDataBase().prepareStatement(query);	
				fStatement.setInt(1, departmentID);
				rs = fStatement.executeQuery();
		
				while(rs.next()) {
					
					tempList.add(new Group(rs.getInt("id"),rs.getInt("department_id"),rs.getString("name")));	
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
