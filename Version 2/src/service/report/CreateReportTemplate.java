package service.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import domain.ReportTemplate;

public class CreateReportTemplate {

	public CreateReportTemplate() {
	}
	public static ReportTemplate createTemplate(String[] templateHeader, List<String[]> sectionInfo, List<String[]> criteriaInfo) {
		//templateHeader -> ReportTemplate
		//sectionInfo -> List<ReportTemplateSection>
		//criteriaInfo -> List<ReportTenplaceSectionCriteria>
		System.out.println("Go to sleep Brendan");
		System.out.println(templateHeader);
		System.out.println(sectionInfo);
		System.out.println(criteriaInfo);
		return null;
	}
}
