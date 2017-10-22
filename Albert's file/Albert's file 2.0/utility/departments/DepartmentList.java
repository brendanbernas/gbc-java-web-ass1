package utility.departments;

import java.util.ArrayList;
import java.util.List;

public class DepartmentList <Department>{
	
	public List <Department> list = new ArrayList<Department>();
	
	public void add(Department temp) {
		list.add(temp);
	}
	
}
