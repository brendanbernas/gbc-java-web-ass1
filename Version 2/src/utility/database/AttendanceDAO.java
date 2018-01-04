package utility.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import utility.employees.Employee;

public class AttendanceDAO {

	//INSERT attendance
	public static long insertAttendance(Date date){
		String query = "INSERT INTO attendance (date) values (?)";
		
		
		java.sql.Date insertDate = new java.sql.Date(date.getTime());
		
		ResultSet rs = null;
		long gID = -1;
		try {
			
			PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			pStatement.setDate(1, insertDate);

			
			pStatement.executeUpdate();
			rs = pStatement.getGeneratedKeys();
		
			if(rs.next())
				gID = rs.getLong(1);
			else
				throw new SQLException("ERROR Attendance WAS NOT INSERTED PROPERLY");
			
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return gID;
	}
	//Insert employee attendance
	public static void employeeAttendnaceCheck(int attendanceID, List<Employee> employeeList,String[] attended){
		//convert string to int array
		int[] check = new int[attended.length];
		int hasAttended = 0;
		
		for(int i = 0;i<attended.length;i++)
		{

			check[i] = Integer.parseInt(attended[i]);
		}
			
		String query = "INSERT attendance_list (employee_ID,attendance_id,status_check) values (?,?,?)";
		
		//insert employee attendance
			try {
				PreparedStatement pStatement = DatabaseAccess.connectDataBase().prepareStatement(query);
			for(Employee o : employeeList){
				for(int i = 0;i<check.length;i++){
					if(o.getId() == check[i]){
						hasAttended = 1;
					}
				}
					pStatement.setInt(1, o.getId());
					pStatement.setInt(2, attendanceID);
					pStatement.setInt(3, hasAttended);
					pStatement.executeUpdate();
					hasAttended = 0;
		
			}
	
			pStatement.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
