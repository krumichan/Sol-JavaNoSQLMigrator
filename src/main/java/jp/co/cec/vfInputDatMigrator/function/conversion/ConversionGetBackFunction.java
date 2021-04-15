package jp.co.cec.vfInputDatMigrator.function.conversion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.datastax.driver.core.Row;

import jp.co.cec.vfInputDatMigrator.constant.Constants;
import jp.co.cec.vfInputDatMigrator.db.model.TrnWorkStatus;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.model.ApplicationConfigurationModel.SignalInform;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;
import jp.co.cec.vfInputDatMigrator.util.JsonUtil;

/**
 * 強制戻しデータを生成するクラス
 */
public class ConversionGetBackFunction extends AbstractFunction {
	
	/**
	 * 強制戻しデータを生成するメソッド
	 * @param params [0]：Cassandraから取得したRow
	 * @return　TrnEventに変換したデータ
	 * @throws Exception データベース切断等
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		List<TrnEvent> result = new ArrayList<TrnEvent>();
		
		Row row = (Row)params[0];
		List<Object> obj = convertJsonToWorkStatus(row);
		
		result.addAll(convert(obj, row));
		
		return ClassUtil.autoCast(result);
	}
	
	/**
	 * Json形式文字列をTrnWorkStatusクラスに変換
	 * @param row　Cassandraから取得したTrnEventValue値
	 * @return　変換結果
	 * @throws Exception　変換失敗
	 */
	private List<Object> convertJsonToWorkStatus(Row row) throws Exception {
		List<Object> res = new ArrayList<Object>();
		
		String originalValue = row.getString("original_value");
		
		JsonUtil ju = new JsonUtil();
		try {
			res.add(ju.strJsonRead(originalValue, TrnWorkStatus.class));
		} catch (Exception e) {
			throw new Exception("original_valueの変換に失敗しました。");
		}
		return res;
	}
	
	/**
	 * Cassandra用にマッピングするメソッド
	 * @param source 連動データ
	 * @return マッピングデータ
	 * @throws Exception SignalId又はLogId取得失敗、データベース切断等
	 */
	@SuppressWarnings({ "serial" })
	private List<TrnEvent> convert(List<Object> sources, Row row) throws Exception {
		List<TrnEvent> res = new ArrayList<TrnEvent>();
		TrnWorkStatus iws = (TrnWorkStatus)sources.get(0);
		GetEventsFunction gef = new GetEventsFunction();
		SignalInform signalInform = config.idmMap.get(Constants.IDM_ID_GET_BACK);
		
		// work_statusからTrnEvent取得
		res.addAll(
			gef.execute(
					iws
					,new HashMap<String, String>() {{ 
						put("signal_class", signalInform.major);
						put("signal_category", signalInform.medium);
						put("signal_name_suffix", "");
						put("connector_instance", "MstMigrator");
						put("subject_instance", "none");
						put("value_instance", "none");
					}}
					,Arrays.asList(new String[] {
							"place_id"
							,"serial_number"
							,"entry_date_time"
							,"work_status_key"
							,"created_by"
							,"created_at"
					})
					,iws.getWork_status_key()
					,iws.getEntry_date_time()));
				
		return res;
	}
}
