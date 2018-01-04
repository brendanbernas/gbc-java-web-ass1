/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Albert Nguyen
 *Student Number: 100865315
 *Date: Jan 04, 2017
 *Description: This class is for the group entity
 */package domain;

public class Group {
	private int id;
	private int departmentID;
	private String name;
	
	public Group(int id, int departmentID, String name) {
		this.id = id;
		this.departmentID = departmentID;
		this.name = name;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
