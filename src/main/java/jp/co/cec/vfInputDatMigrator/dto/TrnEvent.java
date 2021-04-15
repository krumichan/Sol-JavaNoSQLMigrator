package jp.co.cec.vfInputDatMigrator.dto;

import java.util.Date;
import java.util.Map;

import jp.co.cec.vfInputDatMigrator.template.AbstractTrnStatus;

public class TrnEvent extends AbstractTrnStatus {

	/**
	 * シリアル・バージョンID
	 */
	private static final long serialVersionUID = 1L;
	
	private RawTrnStatus raw;

	public TrnEvent(RawTrnStatus raw) { super(); this.raw = raw; }

	@Override
	public String getLogId() { return this.raw.getLogId(); }

	@Override
	public void setLogId(String logId) { this.raw.setLogId(logId); }

	@Override
	public String getSubject() { return this.raw.getSubject(); }

	@Override
	public void setSubject(String subject) { this.raw.setSubject(subject); }

	@Override
	public Date getLogDateTime() { return this.raw.getLogDateTime(); }

	@Override
	public void setLogDateTime(Date logDateTime) { this.raw.setLogDateTime(logDateTime); }

	@Override
	public Date getLogDate() { return this.raw.getLogDate(); }

	@Override
	public void setLogDate(Date logDate) { this.raw.setLogDate(logDate); }

	@Override
	public Date getLogEndDateTime() { return this.raw.getLogEndDateTime(); }

	@Override
	public void setLogEndDateTime(Date logEndDateTime) { this.raw.getLogEndDateTime(); }

	@Override
	public String getValue() { return this.raw.getValue(); }

	@Override
	public void setValue(String value) { this.raw.setValue(value); }

	@Override
	public Map<Date, String> getMapValue() { return this.raw.getMapValue(); }

	@Override
	public void setMapValue(Map<Date, String> mapValue) { this.raw.setMapValue(mapValue); }

	@Override
	public String getUpdatedBy() { return this.raw.getUpdatedBy(); }

	@Override
	public void setUpdatedBy(String updatedBy) { this.raw.setUpdatedBy(updatedBy); }

	@Override
	public Date getUpdatedAt() { return this.raw.getUpdatedAt(); }

	@Override
	public void setUpdatedAt(Date updatedAt) { this.raw.setUpdatedAt(updatedAt); }

	@Override
	public String getValueKey() { return this.raw.getValueKey(); }

	@Override
	public void setValueKey(String valueKey) { this.raw.setValueKey(valueKey); }

	@Override
	public String getSignalId() { return this.raw.getSignalId(); }

	@Override
	public void setSignalId(String signalId) { this.raw.setSignalId(signalId); }
}
