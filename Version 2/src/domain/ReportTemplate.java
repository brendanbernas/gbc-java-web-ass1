package domain;

import java.util.List;

public class ReportTemplate {
	
	//TODO add dependent classes
	private int id, departmentId;
	private String name;
	private List<ReportTemplateSection> templateSections;
	
	public ReportTemplate() {}

	public ReportTemplate(int id, int departmentId, String name,
			List<ReportTemplateSection> templateSections) {
		super();
		this.id = id;
		this.departmentId = departmentId; 
		this.name = name;
		this.templateSections = templateSections;
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

	public List<ReportTemplateSection> getTemplateSections() {
		return templateSections;
	}

	public void setTemplateSections(List<ReportTemplateSection> templateSections) {
		this.templateSections = templateSections;
	}	
}
