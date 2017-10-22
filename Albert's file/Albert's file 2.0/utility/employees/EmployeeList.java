package utility.employees;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList <Employee>{
	
	public List<Employee> list = new ArrayList<Employee>();
	
	public void add(Employee employee) {
		list.add(employee);
	}
	

	
}
