package domain;

public class ReportCriteriaEvaluation {
	private int id, grade;
	private Report report;
	private ReportTemplateSectionCriteria criteriaTemplate;

	public ReportCriteriaEvaluation() {}

	public ReportCriteriaEvaluation(int id, int grade, Report report, ReportTemplateSectionCriteria criteriaTemplate) {
		super();
		this.id = id;
		this.grade = grade;
		this.report = report;
		this.criteriaTemplate = criteriaTemplate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public ReportTemplateSectionCriteria getCriteriaTemplate() {
		return criteriaTemplate;
	}

	public void setCriteriaTemplate(ReportTemplateSectionCriteria criteriaTemplate) {
		this.criteriaTemplate = criteriaTemplate;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
