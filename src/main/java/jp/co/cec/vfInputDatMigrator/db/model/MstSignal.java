package jp.co.cec.vfInputDatMigrator.db.model;

import java.sql.Timestamp;

public class MstSignal {
	private String logId;
	private String signalClass;
	private String signalCategory;
	private String signalName;
	private String dataType;
	private String valueType;
	private String timeDivision;
	private String valueKeyFormat;
	private String retentionType;
	private Long retentionPeriod;
	private String testCode;
	private String createdBy;
	private Timestamp createdAt;
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getSignalClass() {
		return signalClass;
	}
	public void setSignalClass(String signalClass) {
		this.signalClass = signalClass;
	}
	public String getSignalCategory() {
		return signalCategory;
	}
	public void setSignalCategory(String signalCategory) {
		this.signalCategory = signalCategory;
	}
	public String getSignalName() {
		return signalName;
	}
	public void setSignalName(String signalName) {
		this.signalName = signalName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getTimeDivision() {
		return timeDivision;
	}
	public void setTimeDivision(String timeDivision) {
		this.timeDivision = timeDivision;
	}
	public String getValueKeyFormat() {
		return valueKeyFormat;
	}
	public void setValueKeyFormat(String valueKeyFormat) {
		this.valueKeyFormat = valueKeyFormat;
	}
	public String getRetentionType() {
		return retentionType;
	}
	public void setRetentionType(String retentionType) {
		this.retentionType = retentionType;
	}
	public Long getRetentionPeriod() {
		return retentionPeriod;
	}
	public void setRetentionPeriod(Long retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
