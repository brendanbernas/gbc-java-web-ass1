//Brendan Bernas, 101012650

package servlet.entry;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Report;
import domain.ReportTemplate;
import domain.ReportTemplateSection;
import domain.ReportTemplateSectionCriteria;
import service.report.ReportTemplateDataMalformedException;
import utility.ServletUtilities;
import utility.database.ReportTemplateDAO;

@WebServlet("/ReportEvaluationEntry")
public class ReportEvaluationEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ReportEvaluationEntry() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String successPage = "/WEB-INF/pages/report/enter-evaluation-success.jsp";
		String errorPage = "/WEB-INF/pages/report/enter-evaluation-error.jsp";
		
		int departmentId;
		int reportType;
		int templateId;
		int selectedEmployeeOrGroupId;
		String reportTitle;
		GregorianCalendar reportDate = new GregorianCalendar();
		//key -> param
		//value -> integer value of param value
		//ex: 'evalSec1Crit2' -> 5
		HashMap<String, Integer> evaluations = new HashMap<String, Integer>();
		//key -> param
		//value -> param value
		//ex: 'commentSec1' -> 'John Doe is a very resourceful and hard-working accountant'
		HashMap<String, String> comments = new HashMap<String, String>();
		
		try {
			//getting parameters
			departmentId = Integer.parseInt(request.getParameter("depId"));
			//getting report type
			if(request.getParameter("reportType").equals("employee"))
				reportType = Report.TYPE_EMP;
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
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date date = df.parse(request.getParameter("date"));
			reportDate.setTime(date);
			
			//get sections
			ReportTemplate chosenTemplate = new ReportTemplateDAO().getFullReportTemplate(templateId);
			if(chosenTemplate == null) {
				//template not found
				throw new ReportTemplateDataMalformedException();
			} else {
				//loop through sections of the chosen template
				for(ReportTemplateSection section : chosenTemplate.getTemplateSections()) {
					//translates to the names of the params
					//ex: commentSec3 for the third comment
					String commentParam = "commentSec" + section.getSection();
					//no need to validate data, anything goes, even null
					comments.put(commentParam, request.getParameter(commentParam));
					
					//loop through criteria of the current section onject in loop
					for(ReportTemplateSectionCriteria criteria: section.getSectionCriteria()) {
						//translates to the names of the params
						//ex: evalSec1Crit1 for the first evaluation of the first section
						String evalParam = "evalSec" + section.getSection() + "Crit" + criteria.getNumber();
						//must convert and check for null and valid integer
						//NumberFormatException will check for null or bad format
						int evalValue = Integer.parseInt(request.getParameter(evalParam));
						evaluations.put(evalParam, evalValue);
					}
				}
				
				//create Report, SectionEvaluation, and CriteriaEvaluation objects
				//TODO this 
				
				//inserting object as rows in appropriate tables in DB
				//TODO this
			}
			
		}catch(NullPointerException e) {
			//one of the parameters have not been entered
			e.printStackTrace();
		}catch(NumberFormatException e) {
			//departmentId, reportType, templateId, selectedEmployeeOrGroupId, or one of the evaluations have failed parsing
			e.printStackTrace();
		}catch(ParseException e) {
			//date failed parsing
		} catch (ReportTemplateDataMalformedException e) {
			//failed to find matching report with id
			e.printStackTrace();
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
