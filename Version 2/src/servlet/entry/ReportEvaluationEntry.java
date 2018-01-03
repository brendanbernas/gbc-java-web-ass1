//Brendan Bernas, 101012650

package servlet.entry;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;

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
import utility.ServletUtilities;
import utility.database.ReportDAO;
import utility.database.ReportTemplateDAO;

@WebServlet("/ReportEvaluationEntry")
public class ReportEvaluationEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ReportEvaluationEntry() {
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
		
		String successPage = "/WEB-INF/pages/report/enter-evaluation-success.jsp";
		String errorPage = "/WEB-INF/pages/report/enter-evaluation-error.jsp";
		
		int departmentId;
		int reportType;
		int templateId;
		int selectedEmployeeOrGroupId;
		String reportTitle;
		GregorianCalendar reportDate = new GregorianCalendar();
		
		try {
			//getting parameters
			departmentId = Integer.parseInt(request.getParameter("depId"));
			//getting report type
			if(request.getParameter("reportType").equals("employee"))
				reportType = Report.TYPE_EMPLOYEE;
			else if(request.getParameter("reportType").equals("group"))
				reportType = Report.TYPE_GROUP;
			else {
				throw new NullPointerException("Report type data malformed or not sent");
			}
			templateId = Integer.parseInt(request.getParameter("templateId"));
			selectedEmployeeOrGroupId = Integer.parseInt(request.getParameter("employeeOrGroup"));
			reportTitle = request.getParameter("reportTitle");
			
			//if report title is missing, throw exception
			if(!ServletUtilities.checkParameterExists(reportTitle))
				throw new NullPointerException("Report Title missing");
			
			//getting date from form
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(request.getParameter("date"));
			reportDate.setTime(date);
			
			//get sections
			ReportTemplate chosenTemplate = new ReportTemplateDAO().getFullReportTemplate(templateId);
			if(chosenTemplate == null) {
				//template not found
				throw new ReportTemplateDataMalformedException();
			} else {
				//create Report, SectionEvaluation, and CriteriaEvaluation objects
				Report newReport = new Report();
				newReport.setTemplate(chosenTemplate);
				newReport.setMappingId(selectedEmployeeOrGroupId);
				newReport.setType(reportType);
				newReport.setDate(reportDate);
				newReport.setName(reportTitle);
				
				ArrayList<ReportSectionEvaluation> sectionEvaluations = new ArrayList<ReportSectionEvaluation>();
				ArrayList<ReportCriteriaEvaluation> criteriaEvaluations = new ArrayList<ReportCriteriaEvaluation>();
				
				newReport.setSectionEvaluationList(sectionEvaluations);
				newReport.setCriteriaEvaluationList(criteriaEvaluations);
				
				//loop through sections of the chosen template
				for(ReportTemplateSection section : chosenTemplate.getTemplateSections()) {
					//translates to the names of the params
					//ex: commentSec3 for the third comment
					String commentParam = "commentSec" + section.getSection();
					
					ReportSectionEvaluation secEval = new ReportSectionEvaluation();
					secEval.setReport(newReport);
					//no need to validate comment data, anything goes, even null
					secEval.setComment(request.getParameter(commentParam));
					secEval.setSectionTemplate(section);
					sectionEvaluations.add(secEval);
					
					//loop through criteria of the current section object in loop
					for(ReportTemplateSectionCriteria criteria: section.getSectionCriteria()) {
						//translates to the names of the params
						//ex: evalSec1Crit1 for the first evaluation of the first section
						String evalParam = "evalSec" + section.getSection() + "Crit" + criteria.getNumber();
						//must convert and check for null and valid integer
						//NumberFormatException will check for null or bad format
						int evalValue = Integer.parseInt(request.getParameter(evalParam));
						
						ReportCriteriaEvaluation critEval = new ReportCriteriaEvaluation();
						critEval.setReport(newReport);
						critEval.setCriteriaTemplate(criteria);
						critEval.setGrade(evalValue);
						criteriaEvaluations.add(critEval);
					}
				}
				
				//TODO check for unique comment sections
				
				//inserting object as rows in appropriate tables in DB
				if(new ReportDAO().insertNewReport(newReport) == true) {
					//new report has been inserted
					//TODO show success page
				}
				else {
					//failure
					//TODO show failure?
				}
			}
			
		}catch(NullPointerException e) {
			//one of the parameters have not been entered
			e.printStackTrace();
		}catch(NumberFormatException e) {
			//departmentId, reportType, templateId, selectedEmployeeOrGroupId, or one of the evaluations have failed parsing
			e.printStackTrace();
		}catch(ParseException e) {
			//date failed parsing
			e.printStackTrace();
		} catch (ReportTemplateDataMalformedException e) {
			//failed to find matching report with id
			e.printStackTrace();
		} catch (SQLException e) {
			//failed to insert new row
			e.printStackTrace();
		}
		
		//TODO redirect back to the entry form on failure
		//TODO set up params on entry form
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
