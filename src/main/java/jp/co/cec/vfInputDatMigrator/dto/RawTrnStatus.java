/**
 *
 */
package jp.co.cec.vfInputDatMigrator.dto;

import java.util.Date;
import java.util.Map;

import jp.co.cec.vfInputDatMigrator.template.AbstractTrnStatus;



/** Kafka側で生成するクラス。
 * @author user
 *
 */
public class RawTrnStatus extends AbstractTrnStatus {
	/**
	 * シリアル・バージョンID
	 */
	private static final long serialVersionUID = -1813465866938834068L;

	// Column No.01 , データ項目名（日本語）: [ログID].
	private String logId;

	// Column No.02 , データ項目名（日本語）: [作業主体].
	private String subject;

	// Column No.03 , データ項目名（日本語）: [年月日時分秒].
	private Date logDateTime;

	// Column No.04 , データ項目名（日本語）: [年月日].
	private Date logDate;

	// Column No.08 , データ項目名（日本語）: [年月日時分秒(終了)].
	private Date logEndDateTime;

	private String valueKey;
	
	// Column No.10 , データ項目名（日本語）: [値].
	private String value;

	
	// Column No.12 , データ項目名（日本語）: [値(マップ型)].
	private Map<Date,String> mapValue;

	// Column No.13 , データ項目名（日本語）: [更新者].
	private String updatedBy;

	// Column No.14 , データ項目名（日本語）: [更新時間].
	private Date updatedAt;
	
	private String signalId;

	@Override
	public String getLogId() { return logId; }
	@Override
	public void setLogId(String logId) { this.logId = logId; }

	@Override
	public String getSubject() { return subject; }
	@Override
	public void setSubject(String subject) { this.subject = subject; }

	@Override
	public Date getLogDateTime() { return logDateTime; }
	@Override
	public void setLogDateTime(Date logDateTime) { this.logDateTime = logDateTime; }

	@Override
	public Date getLogDate() { return logDate; }
	@Override
	public void setLogDate(Date logDate) { this.logDate = logDate; }

	@Override
	public Date getLogEndDateTime() { return logEndDateTime; }
	@Override
	public void setLogEndDateTime(Date logEndDateTime) { this.logEndDateTime = logEndDateTime; }

	@Override
	public String getValue() { return value; }
	@Override
	public void setValue(String value) { this.value = value; }

	@Override
	public Map<Date,String> getMapValue() { return mapValue; }
	@Override
	public void setMapValue(Map<Date,String> mapValue) { this.mapValue = mapValue; }

	@Override
	public String getUpdatedBy() { return updatedBy; }
	@Override
	public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

	@Override
	public Date getUpdatedAt() { return updatedAt; }
	@Override
	public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

	@Override
	public String getValueKey() { return valueKey; }
	@Override
	public void setValueKey(String valueKey) { this.valueKey = valueKey; }
	@Override
	public String getSignalId() { return signalId; }
	@Override
	public void setSignalId(String signalId) { this.signalId = signalId; }

}
