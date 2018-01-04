package domain;

public class ReportSectionEvaluation {
	private int id;
	private Report report;
	private ReportTemplateSection sectionTemplate;
	private String comment;
	
	public ReportSectionEvaluation() {}

	public ReportSectionEvaluation(int id, Report report, ReportTemplateSection sectionTemplate, String comment) {
		super();
		this.id = id;
		this.report = report;
		this.sectionTemplate = sectionTemplate;
		this.comment = comment;
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

	public ReportTemplateSection getSectionTemplate() {
		return sectionTemplate;
	}

	public void setSectionTemplate(ReportTemplateSection sectionTemplate) {
		this.sectionTemplate = sectionTemplate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
