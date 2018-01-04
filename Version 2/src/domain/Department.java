/*Project: Pegasus Internal Web Application  
 *Assignment: #1
 *Author: Albert
 *Student Number: 100865315
 *Date: Oct 18,2017
 *Description: A class to define a department with all its data members; includes some getters or setters
 */
package domain;

public class Department {

	private int id;
	private String name;
	private String location;
	
	public Department(int id, String name, String location) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
