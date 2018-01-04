package domain;

<<<<<<< HEAD
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
	
	
=======
public class Group{

	private int id;
	private int departmentId;
	private String name;
	
	public Group() {
	}

	public Group(int id, int departmentId, String name) {
		super();
		this.id = id;
		this.departmentId = departmentId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
>>>>>>> master
}
