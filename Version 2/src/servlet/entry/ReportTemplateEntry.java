package servlet.entry;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.ServletUtilities;


@WebServlet("/ReportTemplateEntry")
public class ReportTemplateEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReportTemplateEntry() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//first check for authorization
		HttpSession session = request.getSession();
		//if "user" attribute does not exist forward to login page with error message showing
		if(!ServletUtilities.doesSessionAttributeExist(session, "user"))
		{
			ServletUtilities.forwardToLoginWithErrorMessage(request, response);
			return;
		}		
		boolean isValid = true;
		boolean isMissingMandatoryParam = false;
		ArrayList<String> outErrList = new ArrayList<String>();

		//getting mandatory parameters
		String[] mandatoryParamKeys = {
				"rName",
				"depId",
				"s1name", "s1crit1", "s1crit1max",
				"s2name", "s2crit1", "s2crit1max",
				"s3name", "s3crit1", "s3crit1max"
		};
		//populating HashMap of mandatory keys - values
		//key -> parameter name
		//value -> parameter value
		Map<String, String> manParamMap = new HashMap<String, String>();
		for(int i = 0; i < mandatoryParamKeys.length; i++) {
			manParamMap.put(mandatoryParamKeys[i], request.getParameter(mandatoryParamKeys[i]));
		}
		//check each mandatory parameter, if it doesn't exist, set error as request attribute
		for(Entry<String, String> entry : manParamMap.entrySet()) {
			if(!ServletUtilities.checkParameterExists(entry.getValue())) {
				isValid = false;
				isMissingMandatoryParam = true;
				request.setAttribute("err" + entry.getKey(), true);
			}
			request.setAttribute(entry.getKey(), entry.getValue());
		}
		
		if(isValid) {
			//must check for duplicate sections if it passes the not empty test
			String[] sectionNames = {
					manParamMap.get("s1name"),
					manParamMap.get("s2name"),
					manParamMap.get("s3name"),
			};
			//if the validator returns false, show errors
			if(!ReportTemplateValidator.validSections(sectionNames)) {
				isValid = false;
				outErrList.add("Duplicate section names found, please correct them");
				request.setAttribute("errs1name", true);
				request.setAttribute("errs2name", true);
				request.setAttribute("errs3name", true);
			}
			
		}
		
		
		//getting other optional parameters
		//put into a list of string[] arrays, based on section
		ArrayList<String[]> optParamList = new ArrayList<String[]>();
		optParamList.add(
				new String[] {
						"s1crit2", "s1crit2max",
						"s1crit3", "s1crit3max",
						"s1crit4", "s1crit4max",
						"s1crit5", "s1crit5max"
				});
		optParamList.add(
				new String[] {
						"s2crit2", "s2crit2max",
						"s2crit3", "s2crit3max"
				});
		optParamList.add(
				new String[] {
						"s3crit2", "s3crit2max",
						"s3crit3", "s3crit3max"
				});
		boolean hasBadMatch = false;
		boolean hasIncompleteCritera = false;
		
		//get all the optional param values
		Map<String, String> optParamMap = new HashMap<String, String>();
		for(String[] sectionParamKeys: optParamList) {
			boolean foundEmpty = false;
			for(int i = 0; i < sectionParamKeys.length; i = i + 2) {
				//get param values and put into HashMap
				String paramValue1 = request.getParameter(sectionParamKeys[i]);
				String paramValue2 = request.getParameter(sectionParamKeys[i+1]);
				optParamMap.put(sectionParamKeys[i], paramValue1);
				optParamMap.put(sectionParamKeys[i+1], paramValue2);
				//if both values are empty,  set foundEmpty flag to true
				//for max eval, checking for 0 or not exists
				if(!ServletUtilities.checkParameterExists(paramValue1) && 
						(!ServletUtilities.checkParameterExists(paramValue2) || paramValue2.equals("0"))){
					foundEmpty = true;
				}
				//otherwise check if both values are full
				else if(ServletUtilities.checkParameterExists(paramValue1) && 
						ServletUtilities.checkParameterExists(paramValue2)){
					if(foundEmpty) {
						isValid = false;
						hasIncompleteCritera = true;
						request.setAttribute("err"+sectionParamKeys[i], true);
						request.setAttribute("err"+sectionParamKeys[i+1], true);
					}
				}
				//otherwise, the entry is not complete
				else {
					isValid = false;
					hasBadMatch = true;
					request.setAttribute("err"+sectionParamKeys[i], true);
					request.setAttribute("err"+sectionParamKeys[i+1], true);
				}
			}
		}
		
		
		if(isValid){
			//params are valid, try to insert into DB
			String [] templateHeader = {
					manParamMap.get("depId"),
					manParamMap.get("rName")
			};
			
			//organizing section info (only section names from the form)
			String[] sectionInfo = {
					manParamMap.get("s1name"),
					manParamMap.get("s2name"),
					manParamMap.get("s3name"),
			};
			
			//organization criteria info
			//index of criteriaInfo corresponds with index of sectionInfo
			ArrayList<String[]> criteriaInfo = new ArrayList<String[]>();
			criteriaInfo.add(new String[] {
					manParamMap.get("s1crit1"), manParamMap.get("s1crit1max"),
					optParamMap.get("s1crit2"), optParamMap.get("s1crit2max"),
					optParamMap.get("s1crit3"), optParamMap.get("s1crit3max"),
					optParamMap.get("s1crit4"), optParamMap.get("s1crit4max"),
					optParamMap.get("s1crit5"), optParamMap.get("s1crit5max")
			});
			criteriaInfo.add(new String[] {
					manParamMap.get("s2crit1"), manParamMap.get("s2crit1max"),
					optParamMap.get("s2crit2"), optParamMap.get("s2crit2max"),
					optParamMap.get("s2crit3"), optParamMap.get("s2crit3max")
			});
			criteriaInfo.add(new String[] {
					manParamMap.get("s3crit1"), manParamMap.get("s3crit1max"),
					optParamMap.get("s3crit2"), optParamMap.get("s3crit2max"),
					optParamMap.get("s3crit3"), optParamMap.get("s3crit3max")
			});
			
			//create template object and insert it into db
			ReportTemplate template;
			try {
				template = CreateReportTemplate.createTemplate(manParamMap.get("rName"), manParamMap.get("depId"), sectionInfo, criteriaInfo);
				if(new ReportTemplateDAO().insertNewReportTemplate(template) == false) {
					//something went wrong
					System.out.println("Error inserting " + template);
				}else {
					//insert success
					request.setAttribute("insertedTemplate", template);
					request.getRequestDispatcher("/WEB-INF/pages/report/template/create-report-success.jsp").forward(request, response);
				}
			} catch (ReportTemplateDataMalformedException e) {
				//programmer has not added the sections in the right order
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			if(isMissingMandatoryParam)
				outErrList.add("Please complete the mandatory sections (Template name, department, section names, and each section criteria one)");
			if(hasIncompleteCritera)
				outErrList.add("For your optional criteria, you have skipped over criteria");
			if(hasBadMatch)
				outErrList.add("For your optional criteria, check and make sure you haven't missed any information");
			request.setAttribute("errSummaryList", outErrList);
			request.getRequestDispatcher("/ReportCreate").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
