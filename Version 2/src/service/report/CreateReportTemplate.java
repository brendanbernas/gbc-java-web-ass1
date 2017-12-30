package service.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import domain.ReportTemplate;
import domain.ReportTemplateSection;
import domain.ReportTemplateSectionCriteria;

public class CreateReportTemplate {

	public CreateReportTemplate() {
	}
	
	/**
	 * Receives formatted form data and returns a new Template object ready to be inserted in the DB
	 * @param templateName
	 * @param departmentId
	 * @param sectionInfo
	 * @param criteriaInfo
	 * @return
	 * @throws ReportTemplateDataMalformedException
	 */
	public static ReportTemplate createTemplate(String templateName, String departmentId, String[] sectionInfo, List<String[]> criteriaInfo) throws ReportTemplateDataMalformedException {
		//templateName & departmentId -> ReportTemplate
		//sectionInfo -> List<ReportTemplateSection>
		//criteriaInfo -> List<ReportTenplaceSectionCriteria>
		
		//setting template information
		ReportTemplate template = new ReportTemplate();
		template.setName(templateName);
		template.setDepartmentId(Integer.parseInt(departmentId));
		List<ReportTemplateSection> sectionList = new ArrayList<ReportTemplateSection>();
		//goes through each section and sets it to the template
		try {
			for(int i = 0; i < sectionInfo.length; i++) {
				ReportTemplateSection section = new ReportTemplateSection();
				section.setName(sectionInfo[i]);
				List<ReportTemplateSectionCriteria> criteriaList = new ArrayList<ReportTemplateSectionCriteria>();
				for(int j = 0; j < criteriaInfo.get(i).length; j+=2) {
					//checks to ensure data passed is not "empty"
					if(!criteriaInfo.get(i)[j].equals("") && !criteriaInfo.get(i)[j+1].equals("0")) {
						ReportTemplateSectionCriteria criteria = new ReportTemplateSectionCriteria();
						criteria.setName(criteriaInfo.get(i)[j]);
						criteria.setMaxEvaluation(Integer.parseInt(criteriaInfo.get(i)[j+1]));
						criteria.setNumber(((j+1)/2)+ 1);
						criteriaList.add(criteria);
					}
				}
				section.setSection(i+1);
				section.setSectionCriteria(criteriaList);
				sectionList.add(section);
			}
			template.setTemplateSections(sectionList);
		}catch(IndexOutOfBoundsException e) {
			throw new ReportTemplateDataMalformedException("Sections are not equal");
		}catch(NumberFormatException e) {
			throw new ReportTemplateDataMalformedException("Max evaluations are not the in right indexes");
		}
		
		return template;
	}
}
