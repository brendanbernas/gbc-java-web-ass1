package utility.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import domain.Report;
import domain.ReportCriteriaEvaluation;
import domain.ReportSectionEvaluation;
import domain.ReportTemplate;
import domain.ReportTemplateSection;
import domain.ReportTemplateSectionCriteria;

public class ReportDAO {
	
	public ReportDAO() {}
	
	public boolean insertNewReport(Report report) throws SQLException {
		Connection con = null;
		PreparedStatement insReport = null;
		PreparedStatement insSecEval = null;
		PreparedStatement insCritEval = null;

		//insert statements to be used on DB
		String insReportSql = "INSERT INTO report (report_template_id, mapping_id, report_type, report_name, report_date)"
				+ " VALUES(?, ?, ?, ?, ?)";
		String insSecEvalSql = "INSERT INTO section_evaluation (report_id, report_section_template_id, comment)"
				+ " VALUES(?, ?, ?)";
		String insCritEvalSql ="INSERT INTO criteria_evaluation (report_id, report_criteria_template_id, grade)"
				+ " VALUES(?, ?, ?)";
		
		boolean insertSuccess = false;
		
		try {
			con = DatabaseAccess.connectDataBase();
			//setting false to insert report as a transaction			
			con.setAutoCommit(false);
			
			insReport = con.prepareStatement(insReportSql, Statement.RETURN_GENERATED_KEYS);
			insReport.setInt(1, report.getTemplate().getId());
			insReport.setInt(2, report.getMappingId());
			insReport.setInt(3, report.getType());
			insReport.setString(4, report.getName());
			//must format date to yyyy-MM-dd for db to insert it
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			insReport.setString(5, df.format(report.getDate().getTime()));
			insReport.executeUpdate();
			
			int reportKey = -1;
			ResultSet resReport = insReport.getGeneratedKeys();
			if(resReport.next()){
				reportKey = resReport.getInt(1);
				resReport.close();
			}
			else {
				throw new Exception("No report key generated");
			}
			
			//insert new row for each section evaluation
			for(ReportSectionEvaluation section : report.getSectionEvaluationList()) {
				insSecEval = con.prepareStatement(insSecEvalSql);
				insSecEval.setInt(1, reportKey);
				insSecEval.setInt(2, section.getSectionTemplate().getId());
				insSecEval.setString(3, section.getComment());
				//if no rows updated something went wrong, throw exception
				if(insSecEval.executeUpdate() <= 0) {
					throw new Exception("Error inserting ReportSectionEvaluation object: " + section);
				}
			}
			
			//insert new row for each criteria evaluation
			for(ReportCriteriaEvaluation criteria : report.getCriteriaEvaluationList()) {
				insCritEval = con.prepareStatement(insCritEvalSql);
				insCritEval.setInt(1, reportKey);
				insCritEval.setInt(2, criteria.getCriteriaTemplate().getId());
				insCritEval.setInt(3, criteria.getGrade());
				//if no rows updated something went wrong, throw exception
				if(insCritEval.executeUpdate() <= 0) {
					throw new Exception("Error insert ReportCriteriaEvaluation object: " + criteria);
				}
			}
			con.commit();
			insertSuccess = true;
			
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			if(insReport != null) {
				insReport.close();
			}
			if(insSecEval != null) {
				insSecEval.close();
			}
			if(insCritEval != null) {
				insCritEval.close();
			}
			
			con.setAutoCommit(true);
			con.close();
		}
		return insertSuccess;
	}
	
	/**
	 * Checks if the report title is unique in its template (on the same date)
	 * @param title
	 * @return
	 */
	public boolean isReportTitleAndDateUniqueForTemplate(Report report) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		boolean uniqueFlag = true;
		String sql = "SELECT id FROM report WHERE report_template_id = ? AND report_name = ? AND report_date = ?";
		try {
			con = DatabaseAccess.connectDataBase();
			statement = con.prepareStatement(sql);
			statement.setInt(1, report.getTemplate().getId());
			statement.setString(2, report.getName());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			statement.setString(3, df.format(report.getDate().getTime()));
			rs = statement.executeQuery();
			//if query returns results, means that a table exists with same title in the same template on same date
			if(rs.next())
				uniqueFlag = false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//close resources
			try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return uniqueFlag;
	}
	
	/**
	 * Returns a list of reports associated with the template id
	 * However, these reports contain null references to its associated template and evaluations
	 * @return
	 */
	public List<Report> getReportHeadersByTemplateId(int id){
		ArrayList<Report> list = new ArrayList<Report>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT id, mapping_id, report_type, report_name, report_date FROM report WHERE report_template_id = ?";
		try {
			con = DatabaseAccess.connectDataBase();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while(rs.next()) {
				//add report to list to be returned
				Report r = new Report();
				r.setId(rs.getInt(1));
				r.setMappingId(rs.getInt(2));
				r.setType(rs.getInt(3));
				r.setName(rs.getString(4));
				String strDate = rs.getString(5);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = new GregorianCalendar();
				c.setTime(df.parse(strDate));
				r.setDate(c);
				list.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Report getFullReport(int id) {
		Report report = new Report();
		Connection con = null;
		PreparedStatement reportStmt = null;
		PreparedStatement sectionStmt = null;
		PreparedStatement criteriaStmt = null;
		ResultSet rsRep = null;
		ResultSet rsSec = null;
		ResultSet rsCrit = null;
		try {
			con = DatabaseAccess.connectDataBase();
			String reportSql = "SELECT id, report_template_id, mapping_id, report_type, report_name, report_date FROM report WHERE id = ?";
			int reportTemplateId= 0;
			reportStmt = con.prepareStatement(reportSql);
			reportStmt.setInt(1, id);
			rsRep = reportStmt.executeQuery();
			if(rsRep.next()) {
				report.setId(rsRep.getInt(1));
				reportTemplateId = rsRep.getInt(2);
				report.setMappingId(rsRep.getInt(3));
				report.setType(rsRep.getInt(4));
				report.setName(rsRep.getString(5));
				String strDate = rsRep.getString(6);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = new GregorianCalendar();
				c.setTime(df.parse(strDate));
				report.setDate(c);
			}
			//get associated template
			ReportTemplate template = new ReportTemplateDAO().getFullReportTemplate(reportTemplateId);
			report.setTemplate(template);
			
			ArrayList<ReportSectionEvaluation> secEvals = new ArrayList<ReportSectionEvaluation>();
			report.setSectionEvaluationList(secEvals);
			ArrayList<ReportCriteriaEvaluation> critEvals = new ArrayList<ReportCriteriaEvaluation>();
			report.setCriteriaEvaluationList(critEvals);
			String sectionSql = "SELECT id, comment FROM section_evaluation WHERE report_id = ? AND report_section_template_id = ?";
			String criteriaSql = "SELECT id, grade FROM criteria_evaluation WHERE report_id = ? AND report_criteria_template_id = ?";
			//for each template section get evaluation and add it to the report object
			for(ReportTemplateSection templateEval : template.getTemplateSections()) {
				//get section evaluation for each section template
				sectionStmt = con.prepareStatement(sectionSql);
				sectionStmt.setInt(1, report.getId());
				sectionStmt.setInt(2, templateEval.getId());
				rsSec = sectionStmt.executeQuery();
				if(rsSec.next()) {
					ReportSectionEvaluation sEval = new ReportSectionEvaluation();
					sEval.setSectionTemplate(templateEval);
					sEval.setReport(report);
					sEval.setId(rsSec.getInt(1));
					sEval.setComment(rsSec.getString(2));
					secEvals.add(sEval);
				} else {
					throw new Exception("Incomplete report: Could not find matching evaluation for ReportTemplateSection object: " + templateEval);
				}
				
				for(ReportTemplateSectionCriteria templateCrit : templateEval.getSectionCriteria()) {
					//get criteria evaluation for each criteria template
					criteriaStmt = con.prepareStatement(criteriaSql);
					criteriaStmt.setInt(1, report.getId());
					criteriaStmt.setInt(2, templateCrit.getId());
					rsCrit = criteriaStmt.executeQuery();
					if(rsCrit.next()) {
						ReportCriteriaEvaluation cEval = new ReportCriteriaEvaluation();
						cEval.setCriteriaTemplate(templateCrit);
						cEval.setReport(report);
						cEval.setId(rsCrit.getInt(1));
						cEval.setGrade(rsCrit.getInt(2));
						critEvals.add(cEval);
					} else {
						throw new Exception("Incomplete report: Could not find matching evaluation for ReportTemplateSectionCriteria object: " + templateCrit);
					}
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//close all resources
			if(con != null) {try { con.close(); } catch (Exception e) { e.printStackTrace(); }}
			if(reportStmt != null) {try { reportStmt.close(); } catch (Exception e) { e.printStackTrace(); }}
			if(sectionStmt != null) {try { sectionStmt.close(); } catch (Exception e) { e.printStackTrace(); }}
			if(criteriaStmt != null) {try { criteriaStmt.close(); } catch (Exception e) { e.printStackTrace(); }}
			if(rsRep != null) {try { rsRep .close(); } catch (Exception e) { e.printStackTrace(); }}
			if(rsSec != null) {try { rsSec.close(); } catch (Exception e) { e.printStackTrace(); }}
			if(rsCrit != null) {try { rsCrit.close(); } catch (Exception e) { e.printStackTrace(); }}
		}
		
		return report;
	}
	
	public boolean updateReportEvaluations(Report report) throws SQLException {
		Connection con = null;
		PreparedStatement secStmt = null;
		PreparedStatement critStmt = null;
		String secSql = "UPDATE section_evaluation SET comment = ? WHERE report_id = ? AND report_section_template_id = ?";
		String critSql = "UPDATE criteria_evaluation SET grade = ? WHERE report_id = ? AND report_criteria_template_id = ?";
		boolean isSuccessful = false;
		try {
			con = DatabaseAccess.connectDataBase();
			//update as transaction
			con.setAutoCommit(false);
			
			for(ReportSectionEvaluation section : report.getSectionEvaluationList()) {
				secStmt = con.prepareStatement(secSql);
				secStmt.setString(1, section.getComment());
				secStmt.setInt(2, report.getId());
				secStmt.setInt(3, section.getSectionTemplate().getId());
				if(secStmt.executeUpdate() <= 0) {
					throw new Exception("Failed to update section evaluation");
				}
			}
			
			for(ReportCriteriaEvaluation criteria: report.getCriteriaEvaluationList()) {
				critStmt = con.prepareStatement(critSql);
				critStmt.setInt(1, criteria.getGrade());
				critStmt.setInt(2, report.getId());
				critStmt.setInt(3, criteria.getCriteriaTemplate().getId());
				if(critStmt.executeUpdate() <= 0) {
					throw new Exception("Failed to update criteria evalution");
				}
			}
			
			//previous statements executed, commit transactions
			con.commit();
			isSuccessful = true;
			
		} catch (Exception e) {
			if(con != null)
				con.rollback();
			e.printStackTrace();
		} finally {
			if(con != null) {
				con.setAutoCommit(true);
				con.close();
			}
			if(secStmt != null)
				secStmt.close();
			if(critStmt != null)
				critStmt.close();
		}
		return isSuccessful;
	}
}
