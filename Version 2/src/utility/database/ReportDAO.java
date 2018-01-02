package utility.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import domain.Report;
import domain.ReportCriteriaEvaluation;
import domain.ReportSectionEvaluation;

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
			String strDate = df.format((report.getDate().getTime()));
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
}
