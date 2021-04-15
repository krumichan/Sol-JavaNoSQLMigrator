package jp.co.cec.vfInputDatMigrator.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.co.cec.vfInputDatMigrator.db.model.InterlockDataItem;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockSeq;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockVisualInspection;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockWorkStatus;
import jp.co.cec.vfInputDatMigrator.utility.TestJsonUtil;

public class TestInputDatMigratorMariDao {

	private String url = null;
	private String user = null;
	private String password = null;
	private String dbName = null;
	
	private static final String UTF8_BOM = "\uFEFF";
	
	public TestInputDatMigratorMariDao() { }
	public TestInputDatMigratorMariDao(String server, String user, String password, String dbName) {
		this.url = "jdbc:mariadb://" + server + "/";
		this.user = user;
		this.password = password;
		this.dbName = dbName;
	}
	
	private Connection connect() throws Exception {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public void runScript(String filename) throws Exception {
		String filePath = "VfInputDatMigrator" + "/" + filename;
		filePath = TestJsonUtil.getResourceFileFullPath(filePath);
		
		StringBuilder sb = new StringBuilder();
		Connection conn = null;
		Statement stmt = null;
		try (FileInputStream fis = new FileInputStream(filePath);
			 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			 BufferedReader br = new BufferedReader(isr);){
			conn = connect();
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
			
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) {
					continue;
				}
				
				sb.append(line + "\n");
				if (line.contains(";")) {
					stmt.execute(deleteBom(sb.toString()));
					sb = new StringBuilder();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			TestDbUtils.close(stmt);
			conn.setAutoCommit(true);
			TestDbUtils.close(conn);
		}
	}
	
	public List<InterlockSeq> getSeq() throws Exception {
		List<InterlockSeq> res = new ArrayList<InterlockSeq>();
		String sql = ""
				+ " SELECT "
				+ "		* "
				+ "	FROM interlock_seq "
				+ " ORDER BY exec_date_time, exec_table ";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = connect();
			stmt = conn.createStatement();
			stmt.execute(" USE " + dbName);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				InterlockSeq dat = new InterlockSeq();
				dat.setExecDateTime(rs.getTimestamp("exec_date_time"));
				dat.setExecDml(rs.getString("exec_dml"));
				dat.setExecTable(rs.getString("exec_table"));
				dat.setKey1(rs.getString("key1"));
				dat.setKey2(rs.getString("key2"));
				dat.setKey3(rs.getString("key3"));
				dat.setKey4(rs.getString("key4"));
				res.add(dat);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			TestDbUtils.close(rs);
			TestDbUtils.close(stmt);
			TestDbUtils.close(conn);
		}
		return res;
	}
	
	public List<InterlockDataItem> getDi() throws Exception {
		List<InterlockDataItem> res = new ArrayList<InterlockDataItem>();
		String sql = ""
				+ " SELECT "
				+ " 	* "
				+ "	FROM interlock_data_item "
				+ " ORDER BY exec_date_time ";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = connect();
			stmt = conn.createStatement();
			stmt.execute(" USE " + dbName);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				InterlockDataItem idi = new InterlockDataItem();
				idi.setExec_date_time(rs.getTimestamp("exec_date_time"));
				idi.setExec_data_flag(rs.getByte("exec_data_flag"));
				idi.setLog_id(rs.getString("log_id"));
				idi.setElement_index(rs.getString("element_index"));
				idi.setElement_index_key(rs.getString("element_index_key"));
				idi.setElement(rs.getString("element"));
				idi.setOriginal_item_name(rs.getString("original_item_name"));
				idi.setSubject_type(rs.getString("subject_type"));
				idi.setAcquisition(rs.getString("acquisition"));
				Double dob = rs.getDouble("calculation_point");
				if (rs.wasNull()) {
					dob = null;
				}
				idi.setCalculation_point(dob);
				idi.setGeneration_flag(rs.getString("generation_flag"));
				idi.setInput_data_type(rs.getString("input_data_type"));
				idi.setOutput_data_format(rs.getString("output_data_format"));
				idi.setUnit(rs.getString("unit"));
				idi.setDevice(rs.getString("device"));
				idi.setMaster_id(rs.getString("master_id"));
				idi.setChanging_point(rs.getString("changing_point"));
				idi.setHost_name(rs.getString("host_name"));
				idi.setValidity_start_on(rs.getTimestamp("validity_start_on"));
				idi.setValidity_end_on(rs.getTimestamp("validity_end_on"));
				idi.setCreated_by(rs.getString("created_by"));
				idi.setCreated_at(rs.getTimestamp("created_at"));
				idi.setUpdated_by(rs.getString("updated_by"));
				idi.setUpdated_at(rs.getTimestamp("updated_at"));
				res.add(idi);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			TestDbUtils.close(rs);
			TestDbUtils.close(stmt);
			TestDbUtils.close(conn);
		}
		
		return res;
	}
	
	public List<InterlockVisualInspection> getVi() throws Exception {
		List<InterlockVisualInspection> res = new ArrayList<InterlockVisualInspection>();
		String sql = ""
				+ " SELECT "
				+ "		* "
				+ " FROM interlock_visual_inspection "
				+ " ORDER BY exec_date_time ";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = connect();
			stmt = conn.createStatement();
			stmt.execute(" USE " + dbName);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				InterlockVisualInspection ivi = new InterlockVisualInspection();
				ivi.setExec_date_time(rs.getTimestamp("exec_date_time"));
				ivi.setExec_data_flag(rs.getByte("exec_data_flag"));
				ivi.setPlace_id(rs.getString("place_id"));
				ivi.setSerial_number(rs.getString("serial_number"));
				ivi.setEntry_date_time(rs.getTimestamp("entry_date_time"));
				ivi.setEntry_worker(rs.getString("entry_worker"));
				ivi.setJudgment_key(rs.getString("judgment_key"));
				ivi.setNg_work_status_key(rs.getString("ng_work_status_key"));
				ivi.setStatus_reason_key(rs.getString("status_reason_key"));
				ivi.setStatus_reason(rs.getString("status_reason"));
				ivi.setNote(rs.getString("note"));
				ivi.setCreated_by(rs.getString("created_by"));
				ivi.setCreated_at(rs.getTimestamp("created_at"));
				ivi.setUpdated_by(rs.getString("updated_by"));
				ivi.setUpdated_at(rs.getTimestamp("updated_at"));
				res.add(ivi);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			TestDbUtils.close(rs);
			TestDbUtils.close(stmt);
			TestDbUtils.close(conn);
		}
		return res;
	}
	
	public List<InterlockWorkStatus> getWs() throws Exception {
		List<InterlockWorkStatus> res = new ArrayList<InterlockWorkStatus>();
		String sql = ""
				+ " SELECT "
				+ "		* "
				+ " FROM interlock_work_status "
				+ " ORDER BY exec_date_time ";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = connect();
			stmt = conn.createStatement();
			stmt.execute(" USE " + dbName);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				InterlockWorkStatus iws = new InterlockWorkStatus();
				iws.setExec_date_time(rs.getTimestamp("exec_date_time"));
				iws.setExec_data_flag(rs.getByte("exec_data_flag"));
				iws.setPlace_id(rs.getString("place_id"));
				iws.setSerial_number(rs.getString("serial_number"));
				iws.setEntry_date_time(rs.getTimestamp("entry_date_time"));
				iws.setWork_status_key(rs.getString("work_status_key"));
				iws.setStatus_reason_key(rs.getString("status_reason_key"));
				iws.setStatus_reason(rs.getString("status_reason"));
				iws.setNote(rs.getString("note"));
				iws.setEntry_worker(rs.getString("entry_worker"));
				iws.setCancel_flg(rs.getByte("cancel_flg"));
				iws.setCancel_date_time(rs.getTimestamp("cancel_date_time"));
				iws.setCancel_reason(rs.getString("cancel_reason"));
				iws.setCancel_worker(rs.getString("cancel_worker"));
				iws.setInspection_flg(rs.getByte("inspection_flg"));
				iws.setInspection_updated_at(rs.getTimestamp("inspection_updated_at"));
				iws.setCreated_by(rs.getString("created_by"));
				iws.setCreated_at(rs.getTimestamp("created_at"));
				iws.setUpdated_by(rs.getString("updated_by"));
				iws.setUpdated_at(rs.getTimestamp("updated_at"));
				res.add(iws);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			TestDbUtils.close(rs);
			TestDbUtils.close(stmt);
			TestDbUtils.close(rs);
		}
		
		return res;
	}
	
	public void dropDb(String dbName) throws Exception {
		String sql = "drop database " + dbName;
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = connect();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.execute(sql);
			conn.commit();
		} finally {
			TestDbUtils.close(stmt);
			conn.setAutoCommit(true);
			TestDbUtils.close(conn);
		}
	}
	
	private String deleteBom(String str) {
		if (str.startsWith(UTF8_BOM)) {
			str = str.substring(1);
		}
		return str;
	}
}
