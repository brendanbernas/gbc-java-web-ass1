package utility.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

import domain.ReportTemplate;
import domain.ReportTemplateSection;
import domain.ReportTemplateSectionCriteria;

public class ReportTemplateDAO {

	public ReportTemplateDAO() {
		
	}
	
	public List<ReportTemplate> findByDepartmentId(int departmentId){
		return null;
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
				ResultSet rsSectionKey = insertTemplate.getGeneratedKeys();
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
