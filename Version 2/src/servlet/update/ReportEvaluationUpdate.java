package servlet.update;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Report;
import domain.ReportCriteriaEvaluation;
import domain.ReportSectionEvaluation;
import domain.ReportTemplate;
import domain.ReportTemplateSection;
import domain.ReportTemplateSectionCriteria;
import service.report.ReportTemplateDataMalformedException;
import service.report.ReportValidator;
import utility.ServletUtilities;
import utility.database.ReportDAO;
import utility.database.ReportTemplateDAO;


@WebServlet("/ReportEvaluationUpdate")
public class ReportEvaluationUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReportEvaluationUpdate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//if "user" attribute does not exist forward to login page with error message showing
		if(!ServletUtilities.doesSessionAttributeExist(session, "user"))
		{
			ServletUtilities.forwardToLoginWithErrorMessage(request, response);
			return;
		}
		
		String errMessage = "";
		try {
			//get templateId and template object associated with it
			int templateId = Integer.parseInt(request.getParameter("templateId"));
			ReportTemplate chosenTemplate = new ReportTemplateDAO().getFullReportTemplate(templateId);
			if(chosenTemplate == null) {
				throw new ReportTemplateDataMalformedException();
			}
			
			//get reportId
			int reportId = Integer.parseInt(request.getParameter("reportId"));
			
			//only using id to update child tables of report, can leave other fields empty
			Report reportToUpdate = new Report();
			reportToUpdate.setId(reportId);
			
			ArrayList<ReportSectionEvaluation> sectionEvaluations = new ArrayList<ReportSectionEvaluation>();
			ArrayList<ReportCriteriaEvaluation> criteriaEvaluations = new ArrayList<ReportCriteriaEvaluation>();
			reportToUpdate.setSectionEvaluationList(sectionEvaluations);
			reportToUpdate.setCriteriaEvaluationList(criteriaEvaluations);
			
			//getting values from comment params and setting them to ReportSectionEvaluation objects
			for(ReportTemplateSection section : chosenTemplate.getTemplateSections()) {
				//translates to the names of the params
				//ex: commentSec3 for the third comment
				String commentParam = "commentSec" + section.getSection();
				
				ReportSectionEvaluation secEval = new ReportSectionEvaluation();
				secEval.setReport(reportToUpdate);
				secEval.setComment(request.getParameter(commentParam));
				secEval.setSectionTemplate(section);
				sectionEvaluations.add(secEval);
				
				//getting values from criteria params and setting them to ReportCriteriaEvaluation objects
				for(ReportTemplateSectionCriteria criteria: section.getSectionCriteria()) {
					//translates to the names of the params
					//ex: evalSec1Crit1 for the first evaluation of the first section
					String evalParam = "evalSec" + section.getSection() + "Crit" + criteria.getNumber();
					//must convert and check for null and valid integer
					//NumberFormatException will check for null or bad format
					int evalValue = Integer.parseInt(request.getParameter(evalParam));
					
					ReportCriteriaEvaluation critEval = new ReportCriteriaEvaluation();
					critEval.setReport(reportToUpdate);
					critEval.setCriteriaTemplate(criteria);
					critEval.setGrade(evalValue);
					criteriaEvaluations.add(critEval);
				}
			}

			int validStatus = new ReportValidator().isValidUpdateReport(reportToUpdate);
			if(validStatus == ReportValidator.VALID_UPDATE_REPORT) {
				//report is valid
				//inserting object as rows in appropriate tables in DB
				if(new ReportDAO().updateReportEvaluations(reportToUpdate) == true) {
					//report has been updated
					request.setAttribute("insertSuccess", "Successfully updated");
					request.getRequestDispatcher("/WEB-INF/pages/report/view/view-report.jsp").forward(request, response);
					return;
				}
				else {
					//failure
					errMessage = "Something went wrong, could not insert report";
				}
			}else {
				//report is invalid, show reason
				switch(validStatus) {
				case ReportValidator.INVALID_MISSING_COMMENT:
					errMessage = "One or more comments were missing";
					break;
				case ReportValidator.INVALID_NON_UNIQUE_COMMENT:
					errMessage = "One or more comments were not unique";
					break;
				}
			}
		}catch(NumberFormatException e) {
			//reportId didnt pass parsing
			errMessage = "Something went wrong";
			e.printStackTrace();
		} catch (ReportTemplateDataMalformedException e) {
			//reportTemplate was not found
			errMessage = "Something went wrong";
			e.printStackTrace();
		} catch (SQLException e) {
			// update has failed
			errMessage = "Something went wrong";
			e.printStackTrace();
		}
		
		//forward back to view report page with error message
		errMessage += ", no changes were made";
		request.setAttribute("insertFailure", errMessage);
		request.getRequestDispatcher("/WEB-INF/pages/report/view/view-report.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
