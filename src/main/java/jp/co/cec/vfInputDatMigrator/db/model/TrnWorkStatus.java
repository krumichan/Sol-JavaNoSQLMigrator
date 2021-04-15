package jp.co.cec.vfInputDatMigrator.db.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TrnWorkStatus {
	private String place_id;
	private String serial_number;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone=JsonFormat.DEFAULT_TIMEZONE)
	private Timestamp entry_date_time;
	private String work_status_key;
	private String status_reason_key;
	private String status_reason;
	private String note;
	private String entry_worker;
	private Byte cancel_flg;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone=JsonFormat.DEFAULT_TIMEZONE)
	private Timestamp cancel_date_time;
	private String cancel_reason;
	private String cancel_worker;
	private Byte inspection_flg;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone=JsonFormat.DEFAULT_TIMEZONE)
	private Timestamp inspection_updated_at;
	private String created_by;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone=JsonFormat.DEFAULT_TIMEZONE)
	private Timestamp created_at;
	private String updated_by;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone=JsonFormat.DEFAULT_TIMEZONE)
	private Timestamp updated_at;
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
	public String getWork_status_key() {
		return work_status_key;
	}
	public void setWork_status_key(String work_status_key) {
		this.work_status_key = work_status_key;
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
	public String getEntry_worker() {
		return entry_worker;
	}
	public void setEntry_worker(String entry_worker) {
		this.entry_worker = entry_worker;
	}
	public Byte getCancel_flg() {
		return cancel_flg;
	}
	public void setCancel_flg(Byte cancel_flg) {
		this.cancel_flg = cancel_flg;
	}
	public Timestamp getCancel_date_time() {
		return cancel_date_time;
	}
	public void setCancel_date_time(Timestamp cancel_date_time) {
		this.cancel_date_time = cancel_date_time;
	}
	public String getCancel_reason() {
		return cancel_reason;
	}
	public void setCancel_reason(String cancel_reason) {
		this.cancel_reason = cancel_reason;
	}
	public String getCancel_worker() {
		return cancel_worker;
	}
	public void setCancel_worker(String cancel_worker) {
		this.cancel_worker = cancel_worker;
	}
	public Byte getInspection_flg() {
		return inspection_flg;
	}
	public void setInspection_flg(Byte inspection_flg) {
		this.inspection_flg = inspection_flg;
	}
	public Timestamp getInspection_updated_at() {
		return inspection_updated_at;
	}
	public void setInspection_updated_at(Timestamp inspection_updated_at) {
		this.inspection_updated_at = inspection_updated_at;
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
