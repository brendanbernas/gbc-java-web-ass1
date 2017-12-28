package domain;

import java.util.List;

public class ReportTemplate {
	
	//TODO add dependent classes
	private int id, departmentId, mappingId, type;
	private String name;
	private List<ReportTemplateSection> templateSections;
	
	public ReportTemplate() {}

	public ReportTemplate(int id, int departmentId, int mappingId, int type, String name,
			List<ReportTemplateSection> templateSections) {
		super();
		this.id = id;
		this.departmentId = departmentId;
		this.mappingId = mappingId;
		this.type = type;
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

	public int getMappingId() {
		return mappingId;
	}

	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
