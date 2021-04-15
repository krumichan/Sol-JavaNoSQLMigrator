package jp.co.cec.vfInputDatMigrator.db.model;

import java.sql.Timestamp;

public class InterlockVisualInspection {
	private Timestamp exec_date_time;
	private Byte exec_data_flag;
	private String place_id;
	private String serial_number;
	private Timestamp entry_date_time;
	private String entry_worker;
	private String judgment_key;
	private String ng_work_status_key;
	private String status_reason_key;
	private String status_reason;
	private String note;
	private String created_by;
	private Timestamp created_at;
	private String updated_by;
	private Timestamp updated_at;
	public Timestamp getExec_date_time() {
		return exec_date_time;
	}
	public void setExec_date_time(Timestamp exec_date_time) {
		this.exec_date_time = exec_date_time;
	}
	public Byte getExec_data_flag() {
		return exec_data_flag;
	}
	public void setExec_data_flag(Byte exec_data_flag) {
		this.exec_data_flag = exec_data_flag;
	}
	public String getPlace_id() {
		return place_id;
	}
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public Timestamp getEntry_date_time() {
		return entry_date_time;
	}
	public void setEntry_date_time(Timestamp entry_date_time) {
		this.entry_date_time = entry_date_time;
	}
	public String getEntry_worker() {
		return entry_worker;
	}
	public void setEntry_worker(String entry_worker) {
		this.entry_worker = entry_worker;
	}
	public String getJudgment_key() {
		return judgment_key;
	}
	public void setJudgment_key(String judgment_key) {
		this.judgment_key = judgment_key;
	}
	public String getNg_work_status_key() {
		return ng_work_status_key;
	}
	public void setNg_work_status_key(String ng_work_status_key) {
		this.ng_work_status_key = ng_work_status_key;
	}
	public String getStatus_reason_key() {
		return status_reason_key;
	}
	public void setStatus_reason_key(String status_reason_key) {
		this.status_reason_key = status_reason_key;
	}
	public String getStatus_reason() {
		return status_reason;
	}
	public void setStatus_reason(String status_reason) {
		this.status_reason = status_reason;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
}