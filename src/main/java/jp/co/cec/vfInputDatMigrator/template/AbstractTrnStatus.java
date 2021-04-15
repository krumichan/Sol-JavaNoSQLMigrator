/**
 *
 */
package jp.co.cec.vfInputDatMigrator.template;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** 共通クラス.
 * @author user
 *
 */
public abstract class AbstractTrnStatus implements Serializable {
	/**
	 * シリアル・バージョンID (eclipseで生成シリアル・バージョンIDを使った; 同じ構造ならば同じ数値、だそうだ)
	 */
	private static final long serialVersionUID = -7651852075599943658L + 1L;

	// ----------------
	// カラム操作メソッド。
	// ----------------

	abstract public String getLogId();
	abstract public void setLogId(String logId);

	abstract public String getSubject();
	abstract public void setSubject(String subject);

	abstract public Date getLogDateTime();
	abstract public void setLogDateTime(Date logDateTime);

	abstract public Date getLogDate();
	abstract public void setLogDate(Date logDate);

	abstract public Date getLogEndDateTime();
	abstract public void setLogEndDateTime(Date logEndDateTime);

	abstract public String getValue();
	abstract public void setValue(String value);
	
	abstract public String getValueKey();
	abstract public void setValueKey(String valueKey);


	abstract public Map<Date,String> getMapValue();
	abstract public void setMapValue(Map<Date,String> mapValue);

	abstract public String getUpdatedBy();
	abstract public void setUpdatedBy(String updatedBy);

	abstract public Date getUpdatedAt();
	abstract public void setUpdatedAt(Date updatedAt);
	
	abstract public String getSignalId();
	abstract public void setSignalId(String signalId);
}
