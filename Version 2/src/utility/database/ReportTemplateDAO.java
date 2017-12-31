package utility.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import domain.ReportTemplate;
import domain.ReportTemplateSection;
import domain.ReportTemplateSectionCriteria;

public class ReportTemplateDAO {

	public ReportTemplateDAO() {
		
	}
	
	public ReportTemplate getFullReportTemplate(int templateId){
		ReportTemplate templateOut = new ReportTemplate();
		String selTemplateSql = "SELECT id, department_id, template_name "
				+ "FROM report_template WHERE id = ?";
		String selSectionSql = "SELECT id, report_template_id, template_name, section_number "
				+ "FROM report_section_template WHERE report_template_id = ?";
		String selCriteriaSql = "SELECT id, report_section_template_id, template_name, criteria_number, max_evaluation "
				+ "FROM report_criteria_template WHERE report_section_template_id = ?";
		try {
			//connect to DB
			Connection con = DatabaseAccess.connectDataBase();
			
			//gets template from the db
			PreparedStatement selTemplate = con.prepareStatement(selTemplateSql);
			selTemplate.setInt(1, templateId);
			ResultSet resTemplate = selTemplate.executeQuery();
			if(resTemplate.next()) {
				templateOut.setId(templateId);
				templateOut.setDepartmentId(resTemplate.getInt(2));
				templateOut.setName(resTemplate.getString(3));
				
				//gets section(s) associated with the template
				List<ReportTemplateSection> sectionList =  new ArrayList<ReportTemplateSection>();
				PreparedStatement selSection = con.prepareStatement(selSectionSql);
				selSection.setInt(1, templateId);
				ResultSet resSection = selSection.executeQuery();
				while(resSection.next()) {
					//get section data and add to object
					//then add it to a list to be set to the template object
					ReportTemplateSection section = new ReportTemplateSection();
					section.setId(resSection.getInt(1));
					section.setReportTemplateId(resSection.getInt(2));
					section.setName(resSection.getString(3));
					section.setSection(resSection.getInt(4));
					sectionList.add(section);
					
					//gets criteria associated with the section
					List<ReportTemplateSectionCriteria> criteriaList = new ArrayList<ReportTemplateSectionCriteria>();
					PreparedStatement selCriteria = con.prepareStatement(selCriteriaSql);
					selCriteria.setInt(1, section.getId());
					ResultSet resCriteria = selCriteria.executeQuery();
					while(resCriteria.next()) {
						ReportTemplateSectionCriteria criteria = new ReportTemplateSectionCriteria();
						criteria.setId(resCriteria.getInt(1));
						criteria.setSectionTemplateId(resCriteria.getInt(2));
						criteria.setName(resCriteria.getString(3));
						criteria.setNumber(resCriteria.getInt(4));
						criteria.setMaxEvaluation(resCriteria.getInt(5));
						criteriaList.add(criteria);						
					}
					section.setSectionCriteria(criteriaList);
				}
				templateOut.setTemplateSections(sectionList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return templateOut;
	}
	
	/**
	 * Get names and ids from the templates in the DB that are associated with the departmentId passed
	 * HashMap:
	 * 	key -> id
	 * 	value -> name
	 * @param departmentId
	 * @return
	 */
	public HashMap<Integer, String> getTemplateNamesAndIdsByDepartment(int departmentId){
		Connection con = null;
		PreparedStatement selectTemplates = null;
		String sql = "SELECT id, template_name FROM report_template WHERE department_id = ?";
		HashMap<Integer, String> outMap = new HashMap<Integer, String>();
		try {
			con = DatabaseAccess.connectDataBase();
			selectTemplates = con.prepareStatement(sql);
			selectTemplates.setInt(1, departmentId);
			ResultSet rs = selectTemplates.executeQuery();
			//get id and name and add it to HashMap
			while(rs.next()) {
				outMap.put(
						rs.getInt(1), 
						rs.getString(2)
				);
			}
			rs.close();
			selectTemplates.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return the HashMap that was created
		//if no results from query, HashMap will be empty
		return outMap;
	}
	
	public boolean insertNewReportTemplate(ReportTemplate template) throws SQLException {
		PreparedStatement insertTemplate = null;
		PreparedStatement insertSection = null;
		PreparedStatement insertCriteria = null;
		
		//insert new report_template
		String insertTemplateSql = "INSERT INTO report_template (department_id, template_name)"
				+ " VALUES (?, ?)";
		String insertSectionSql = "INSERT INTO report_section_template (report_template_id, template_name, section_number)"
				+ " VALUES (?, ?, ?)";
		String insertCriteriaSql = "INSERT INTO report_criteria_template (report_section_template_id, template_name, criteria_number, max_evaluation)"
				+ " VALUES (?, ?, ?, ?)";
		
		Connection con = null;
		boolean insertSuccess = false;
		try {
			con = DatabaseAccess.connectDataBase();
			//setting auto-commit to false, so we can commit all the new rows as a transaction
			con.setAutoCommit(false);
			//insert template into db
			//note: current date is managed by MySQL function CURRENT_TIMESTAMP
			insertTemplate = con.prepareStatement(insertTemplateSql, Statement.RETURN_GENERATED_KEYS);
			insertTemplate.setInt(1, template.getDepartmentId());
			insertTemplate.setString(2, template.getName());
			insertTemplate.executeUpdate();
			
			//we must get the generated key so we can associate the child rows (report_section_template)
			int templateKey = -1;
			ResultSet rs = insertTemplate.getGeneratedKeys();
			if(rs.next())
				templateKey = rs.getInt(1);
			else
				throw new Exception("No template key generated");
			
			for(ReportTemplateSection section : template.getTemplateSections()) {
				insertSection = con.prepareStatement(insertSectionSql, Statement.RETURN_GENERATED_KEYS);
				//associate section table w/ template table
				insertSection.setInt(1, templateKey);
				insertSection.setString(2, section.getName());
				insertSection.setInt(3, section.getSection());
				insertSection.executeUpdate();
				
				//getting the generated section key so criteria can be associated
				int sectionKey = -1;
				ResultSet rsSectionKey = insertSection.getGeneratedKeys();
				if(rsSectionKey.next())
					sectionKey = rsSectionKey.getInt(1);
				else
					throw new Exception("No section key generated");
				for(ReportTemplateSectionCriteria criteria : section.getSectionCriteria()) {
					insertCriteria = con.prepareStatement(insertCriteriaSql);
					insertCriteria.setInt(1, sectionKey);
					insertCriteria.setString(2, criteria.getName());
					insertCriteria.setInt(3, criteria.getNumber());
					insertCriteria.setInt(4, criteria.getMaxEvaluation());
					insertCriteria.executeUpdate();
				}
				rsSectionKey.close();
				
			}
			rs.close();
			con.commit();
			insertSuccess = true;
		} catch (Exception e) {
			if(con!=null) {
				try {
					con.rollback();
					System.out.println("Report Template insert unsuccessful, rolling back transaction");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			//closing open resources
			if(insertTemplate != null)
				insertTemplate.close();
			if(insertSection != null)
				insertSection.close();
			if(insertCriteria != null)
				insertCriteria.close();
			con.setAutoCommit(true);

		}
		con.close();
		return insertSuccess;
	}
	

}
