package domain;

import java.util.List;

public class ReportTemplateSection {
	
	private int id, reportTemplateId, section;
	private String name;
	private List<ReportTemplateSectionCriteria> sectionCriteria;
	
	public ReportTemplateSection() {}
	
	public ReportTemplateSection(int id, int reportTemplateId, int section, String name, List<ReportTemplateSectionCriteria> sectionCriteria) {
		super();
		this.id = id;
		this.reportTemplateId = reportTemplateId;
		this.section = section;
		this.name = name;
		this.sectionCriteria = sectionCriteria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReportTemplateId() {
		return reportTemplateId;
	}

	public void setReportTemplateId(int reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ReportTemplateSectionCriteria> getSectionCriteria() {
		return sectionCriteria;
	}

	public void setSectionCriteria(List<ReportTemplateSectionCriteria> sectionCriteria) {
		this.sectionCriteria = sectionCriteria;
	}
}
