package domain;

import java.util.Calendar;
import java.util.List;

public class Report {

	//two types of report types
	public static final int TYPE_EMPLOYEE = 1;
	public static final int TYPE_GROUP = 2;
	
	private int id;
	private ReportTemplate template;
	private int mappingId;
	private int type;
	private String name;
	private Calendar date;
	private List<ReportCriteriaEvaluation> criteriaEvaluationList;
	private List<ReportSectionEvaluation> sectionEvaluationList;
	
	
	public Report() {}

	public Report(int id, ReportTemplate template, int mappingId, int type, String name, Calendar date,
			List<ReportCriteriaEvaluation> criteriaEvaluationList,
			List<ReportSectionEvaluation> sectionEvaluationList) {
		this.id = id;
		this.template = template;
		this.mappingId = mappingId;
		this.type = type;
		this.name = name;
		this.date = date;
		this.criteriaEvaluationList = criteriaEvaluationList;
		this.sectionEvaluationList = sectionEvaluationList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ReportTemplate getTemplate() {
		return template;
	}

	public void setTemplate(ReportTemplate template) {
		this.template = template;
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

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public List<ReportCriteriaEvaluation> getCriteriaEvaluationList() {
		return criteriaEvaluationList;
	}

	public void setCriteriaEvaluationList(List<ReportCriteriaEvaluation> criteriaEvaluationList) {
		this.criteriaEvaluationList = criteriaEvaluationList;
	}

	public List<ReportSectionEvaluation> getSectionEvaluationList() {
		return sectionEvaluationList;
	}

	public void setSectionEvaluationList(List<ReportSectionEvaluation> sectionEvaluationList) {
		this.sectionEvaluationList = sectionEvaluationList;
	}	
}
