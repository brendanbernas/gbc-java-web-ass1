//Brendan Bernas, 101012650

package service.report;

import java.util.ArrayList;

import domain.Report;
import domain.ReportSectionEvaluation;
import utility.database.ReportDAO;

public class ReportValidator {
	
	public static final int VALID_NEW_REPORT = 1;
	public static final int INVALID_NON_UNIQUE_COMMENT = 2;
	public static final int INVALID_MISSING_COMMENT = 3;
	//evaluation cannot be missing, servlet should catch NumberFormatException when trying to parse data
	//public static final int INVALID_MISSING_EVALUATION = 4;
	public static final int INVALID_MISSING_TITLE = 5;
	public static final int INVALID_DUPLICATE_TITLE = 6;
	public static final int VALID_UPDATE_REPORT = 7;

	public ReportValidator() {}

	/**
	 * Checks if given Report object conforms to business logic, so it is ready to be inserted to the DB
	 * @param report
	 * @return
	 */
	public int isValidNewReport(Report report) {
		//check for missing title
		if(report.getName() == null || report.getName().trim().equals(""))
			return INVALID_MISSING_TITLE;
		
		//check for non unique comment
		//loop through sections, check if in list, if in list return error, else add to list
		ArrayList<String> commentList = new ArrayList<String>();
		for(ReportSectionEvaluation section : report.getSectionEvaluationList()) {
			if(section.getComment() == null || section.getComment().trim().equals(""))
				return INVALID_MISSING_COMMENT;
			if(commentList.contains(section.getComment()))
				return INVALID_NON_UNIQUE_COMMENT;
			commentList.add(section.getComment());
		}
		
		//check for unique report title with date in the template that it is in
		if(!new ReportDAO().isReportTitleAndDateUniqueForTemplate(report))
			return INVALID_DUPLICATE_TITLE;
		
		//has passed previous tests, return report is valid
		return VALID_NEW_REPORT;
	}
	
	/**
	 * Checks if given Report object conforms to business logic, so it can be updated to object in the DB
	 * @param report
	 * @return
	 */
	public int isValidUpdateReport(Report report) {
		//check for non unique comment
		//loop through sections, check if in list, if in list return error, else add to list
		ArrayList<String> commentList = new ArrayList<String>();
		for(ReportSectionEvaluation section : report.getSectionEvaluationList()) {
			if(section.getComment() == null || section.getComment().trim().equals(""))
				return INVALID_MISSING_COMMENT;
			if(commentList.contains(section.getComment()))
				return INVALID_NON_UNIQUE_COMMENT;
			commentList.add(section.getComment());
		}
		
		//has passed previous tests, return report is valid
		return VALID_UPDATE_REPORT;
	}
}
