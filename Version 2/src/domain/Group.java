package domain;

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
}
