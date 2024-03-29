/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Brendan Bernas, Sergio Santilli
 *Student Number: 101012650
 *Date: Oct 19,2017
 *Description: DatabaseAccess - Example provides access to database
 */
package utility.database;
/****************************************************************************************************
* Description: DatabaseAccess - Example provides access to database
****************************************************************************************************/
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseAccess {
	private static String username = "admin";
	private static String password = "admin";
	private static String database = "COMP3095";

	  private static Connection connect = null;
	  
	  public static Connection connectDataBase() throws Exception {
	    try {
	      // This will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      // Setup the connection with the DB
	      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost:3306/"+database+"?"
		              + "user="+username+"&password="+password);
	      return connect;
	    } catch (Exception e) {
	      throw e;
	    } 
	  }
}