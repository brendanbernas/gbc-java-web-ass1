/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Albert Nguyen
 *Student Number: 100865315
 *Date: Oct 18,2017
 *Description: A class to define a employee with all its data members; includes some getters or setters
 */
package utility.employees;

import java.util.Date;

public class Employee {

	private int id;
	private String fName;
	private String lName;
	private String email;
	private Date dateHired;
	private String position;
	
	public Employee(int id, String fName, String lName, String email, Date dateHired, String position) {
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.dateHired = dateHired;
		this.position = position;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateHired() {
		return dateHired;
	}
	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
}
