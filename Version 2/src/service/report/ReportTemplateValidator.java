package service.report;

import java.util.ArrayList;

public class ReportTemplateValidator {

	public ReportTemplateValidator() {
	}
	
	/**
	 * Returns true if String array does not have any duplicate elements
	 * @param sections
	 * @return
	 */
	public static boolean validSections(String[] sections) {
		ArrayList<String> sectionList = new ArrayList<String>();
		//add each section to the array list
		//before adding it, check if it already exists
		// if exists -> return false
		// otherwise -> return true
		for(String section : sections) {
			if(sectionList.contains(section))
				return false;
			sectionList.add(section);
		}
		return true;
		
	}

}
