package jp.co.cec.vfInputDatMigrator.db.model;

import java.sql.Timestamp;

public class MstSignalAcquisition {
	private String signalId;
	private String connector;
	private String connectorInstance;
	private String signalClass;
	private String signalCategory;
	private String signalName;
	private String subjectInstance;
	private String valueInstance;
	private String conversion;
	private String testCode;
	private String createdBy;
	private Timestamp createdAt;
	public String getSignalId() {
		return signalId;
	}
	public void setSignalId(String signalId) {
		this.signalId = signalId;
	}
	public String getConnector() {
		return connector;
	}
	public void setConnector(String connector) {
		this.connector = connector;
	}
	public String getConnectorInstance() {
		return connectorInstance;
	}
	public void setConnectorInstance(String connectorInstance) {
		this.connectorInstance = connectorInstance;
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
	public String getSubjectInstance() {
		return subjectInstance;
	}
	public void setSubjectInstance(String subjectInstance) {
		this.subjectInstance = subjectInstance;
	}
	public String getValueInstance() {
		return valueInstance;
	}
	public void setValueInstance(String valueInstance) {
		this.valueInstance = valueInstance;
	}
	public String getConversion() {
		return conversion;
	}
	public void setConversion(String conversion) {
		this.conversion = conversion;
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
