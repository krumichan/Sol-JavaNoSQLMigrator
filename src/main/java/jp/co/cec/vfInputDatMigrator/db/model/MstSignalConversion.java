package jp.co.cec.vfInputDatMigrator.db.model;

import java.sql.Timestamp;

public class MstSignalConversion {
	private String viewPartsId;
	private String conditions;
	private String elementKey;
	private String resourceType;
	private String majorClassification;
	private String mediumClassification;
	private String smallClassification;
	private String testCode;
	private Timestamp validityStartOn;
	private Timestamp validityEndOn;
	private String createdBy;
	private Timestamp createdAt;
	private String updatedBy;
	private Timestamp updatedAt;
	public String getViewPartsId() {
		return viewPartsId;
	}
	public void setViewPartsId(String viewPartsId) {
		this.viewPartsId = viewPartsId;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getElementKey() {
		return elementKey;
	}
	public void setElementKey(String elementKey) {
		this.elementKey = elementKey;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getMajorClassification() {
		return majorClassification;
	}
	public void setMajorClassification(String majorClassification) {
		this.majorClassification = majorClassification;
	}
	public String getMediumClassification() {
		return mediumClassification;
	}
	public void setMediumClassification(String mediumClassification) {
		this.mediumClassification = mediumClassification;
	}
	public String getSmallClassification() {
		return smallClassification;
	}
	public void setSmallClassification(String smallClassification) {
		this.smallClassification = smallClassification;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public Timestamp getValidityStartOn() {
		return validityStartOn;
	}
	public void setValidityStartOn(Timestamp validityStartOn) {
		this.validityStartOn = validityStartOn;
	}
	public Timestamp getValidityEndOn() {
		return validityEndOn;
	}
	public void setValidityEndOn(Timestamp validityEndOn) {
		this.validityEndOn = validityEndOn;
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
