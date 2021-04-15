package jp.co.cec.vfInputDatMigrator.function.conversion;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.time.DateUtils;

import com.google.common.base.Strings;

import jp.co.cec.vfInputDatMigrator.constant.Constants;
import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.model.MstSignal;
import jp.co.cec.vfInputDatMigrator.db.model.MstSignalAcquisition;
import jp.co.cec.vfInputDatMigrator.db.service.MstSignalAcquisitionService;
import jp.co.cec.vfInputDatMigrator.db.service.MstSignalService;
import jp.co.cec.vfInputDatMigrator.dto.RawTrnStatus;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;
import jp.co.cec.vfInputDatMigrator.util.FormatUtil;
import jp.co.cec.vfInputDatMigrator.util.TimeUtil;

/**
 * 連動データからCassandraへのデータリストを取得
 */
public class GetEventsFunction extends AbstractFunction {

	/**
	 * 　連動データからCassandraへのデータリストを取得
	 * @param params [0]：連動データ / [1]：信号マップ / [2]：連動データの移行対象のフィールドマップ / [3]：work_status_key / [4]：log_date_time
	 */
	@Override
	@SuppressWarnings({ "serial" })
	public <T> T execute(Object... params) throws Exception {
		List<TrnEvent> reses = new ArrayList<TrnEvent>();
		
		//　連動データを取得
		Object source = params[0];
		
		//　LogId, SignalIdを取得する為のデータ
		//　signal_class, signal_category, signal_name_suffix, connector_instance, subject_instance, value_instance
		Map<String, String> idData = ClassUtil.autoCast(params[1]);
		
		//　連動データからCassandraへの変換する対象フィールド名
		//　例)place_id, serial_number 等
		List<String> migTargets = ClassUtil.autoCast(params[2]);
		
		//　work_status_keyを取得
		String workStatusKey = (String)params[3];
		
		// log_date_timeを取得
		Timestamp logDateTime = (Timestamp)params[4]; 
		
		//　連動データの全てのゲッターメソッドを実行し、データを取得
		Map<String, Object> dats = ClassUtil.getGetterData(source);
		
		String signalClass = idData.get("signal_class");
		String signalCategory = idData.get("signal_category");
		String signalName = workStatusKey + idData.get("signal_name_suffix");
		String connectorInstance = idData.get("connector_instance");
		String subjectInstance = idData.get("subject_instance");
		String valueInstance = idData.get("value_instance");
		
		//　ログID取得
		String logId = getLogId(signalClass, signalCategory, signalName);
		
		Date startTime = TimeUtil.dttmToDateTime(FormatUtil.formatDateToStringSlashAndUnder3(logDateTime));
		Date startDate = DateUtils.truncate(startTime, Calendar.DAY_OF_MONTH);
		String updatedBy = Constants.APPLICATION_NAME;
		Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());
		
		//　信号ID取得
		String signalId = getSignalId(signalClass, signalCategory, signalName, connectorInstance, subjectInstance, valueInstance);
		
		for (String target : migTargets) {
			
			//　一つの連動データの中で、移行対象のデータを取得
			Object value = dats.get(target);
			if (Objects.isNull(value)) {
				continue;
			}
		
			//　もし、移行対象データがStringで、null又は空の場合、移行しない
			if (value instanceof String &&
				Strings.isNullOrEmpty(FormatUtil.trimStr((String)value))) {
				continue;
			}
			
			//　Cassandraへのデータ生成
			RawTrnStatus rts = new RawTrnStatus();
			String subject = (String)target;
			
			//　もし、subjectがフラグの場合、Byte形式のデータをBoolean形式のStringに変換
			if (subject.contains("flg")) {
				value = (Byte)value == 1 ? "TRUE" : "FALSE";
			}
			
			//　移行対象のデータをStringに変換
			String setVal = String.valueOf(value);
			if (subject.contains("created_at")) {
				setVal = FormatUtil.formatDateToString((Date)value);
			} else if (subject.contains("entry_date_time") || subject.contains("inspection_updated_at")) {
				setVal = FormatUtil.formatDateToStringUnder3((Timestamp)value);
			}
			
			rts.setLogId(logId);
			rts.setSubject(subject);
			rts.setLogDateTime(startTime);
			rts.setLogDate(startDate);
			rts.setLogEndDateTime(null);
			rts.setValueKey(setVal);
			rts.setValue(setVal);
			rts.setMapValue(new HashMap<Date, String>() {{
			    put(startDate, logId);
			}});
			rts.setUpdatedBy(updatedBy);
			rts.setUpdatedAt(updatedAt);
			rts.setSignalId(signalId);
			
			reses.add(new TrnEvent(rts));
		}
		
		return ClassUtil.autoCast(reses);
	}
	
	/**
	 * 信号マスタからLogIdを取得するメソッド
	 * @param element 取得時のキー
	 * @return ログID
	 * @throws Exception 取得失敗(データベース切断等)
	 */
	private String getLogId(String signalClass, String signalCategory, String signalName) throws Exception {
		MstSignalService mss = new MstSignalService(MariaDbConnector.MarKey.V2);
		MstSignal ms = mss.lineMstSignal(
				signalClass
				,signalCategory
				,signalName);
		if (ms == null) {
			throw new Exception(
					"ログIDが存在しません。" + "\n" 
					+ "signalClass : " + signalClass + "\n" 
					+ "signalCategory : " + signalCategory + "\n"
					+ "signalName : " + signalName);
		} else {
			return ms.getLogId();
		}
	}
	
	/**
	 * 信号取得マスタからSignalIdを取得するメソッド
	 * @param element 取得時のキー
	 * @return 信号ID
	 * @throws Exception 取得失敗(データベース切断等)
	 */
	private String getSignalId(	String signalClass, String signalCategory, String signalName,
								String connectorInstance, String subjectInstance, String valueInstance) throws Exception {
		MstSignalAcquisitionService msas = new MstSignalAcquisitionService(MariaDbConnector.MarKey.V2);
		MstSignalAcquisition msa = msas.lineMstSignalAcquisition(
				signalClass
				,signalCategory
				,signalName
				,connectorInstance
				,subjectInstance
				,valueInstance);
		if (msa == null) {
			throw new Exception(
					"ログIDが存在しません。" + "\n" 
					+ "signalClass : " + signalClass + "\n" 
					+ "signalCategory : " + signalCategory + "\n"
					+ "signalName : " + signalName + "\n"
					+ "connectorInstance : " + connectorInstance + "\n"
					+ "subjectInstance : " + subjectInstance + "\n"
					+ "valueInstance : " + valueInstance + "\n");
		} else {
			return msa.getSignalId();
		}
	}
}
