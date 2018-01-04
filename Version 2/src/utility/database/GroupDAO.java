package utility.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class GroupDAO {

	public GroupDAO() {
	}
	
	public HashMap<Integer, String> getGroupNamesAndIdsByDepartment(int departmentId){
		Connection con = null;
		PreparedStatement selectGroups = null;
		String sql = "SELECT id, name FROM groups WHERE department_id = ?";
		HashMap<Integer, String> outMap = new HashMap<Integer, String>();
		try {
			con = DatabaseAccess.connectDataBase();
			selectGroups = con.prepareStatement(sql);
			selectGroups.setInt(1, departmentId);
			ResultSet rs = selectGroups.executeQuery();
			//get id and name and add it to HashMap
			while(rs.next()) {
				outMap.put(
						rs.getInt(1), 
						rs.getString(2)
				);
			}
			rs.close();
			selectGroups.close();
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
