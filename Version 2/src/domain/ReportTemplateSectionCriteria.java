package domain;

public class ReportTemplateSectionCriteria {

	private int id, sectionTemplateId, number, maxEvaluation;
	private String name;
	
	public ReportTemplateSectionCriteria() {}

	public ReportTemplateSectionCriteria(int id, int sectionTemplateId, int number, int maxEvaluation, String name) {
		super();
		this.id = id;
		this.sectionTemplateId = sectionTemplateId;
		this.number = number;
		this.maxEvaluation = maxEvaluation;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSectionTemplateId() {
		return sectionTemplateId;
	}

	public void setSectionTemplateId(int sectionTemplateId) {
		this.sectionTemplateId = sectionTemplateId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getMaxEvaluation() {
		return maxEvaluation;
	}

	public void setMaxEvaluation(int maxEvaluation) {
		this.maxEvaluation = maxEvaluation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
