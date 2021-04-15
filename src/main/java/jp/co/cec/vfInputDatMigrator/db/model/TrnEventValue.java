package jp.co.cec.vfInputDatMigrator.db.model;

import java.sql.Timestamp;

public class TrnEventValue {
	private Timestamp logDate;
	private String logId;
	private String value;
	private Timestamp logDateTime;
	private String originalValue;
	private String subject;
	private Timestamp updatedAt;
	private String updatedBy;
	public Timestamp getLogDate() {
		return logDate;
	}
	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Timestamp getLogDateTime() {
		return logDateTime;
	}
	public void setLogDateTime(Timestamp logDateTime) {
		this.logDateTime = logDateTime;
	}
	public String getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
