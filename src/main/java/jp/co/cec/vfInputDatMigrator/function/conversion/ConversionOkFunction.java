package jp.co.cec.vfInputDatMigrator.function.conversion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jp.co.cec.vfInputDatMigrator.constant.Constants;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockVisualInspection;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.function.SelectCassDataFunction;
import jp.co.cec.vfInputDatMigrator.model.ApplicationConfigurationModel.SignalInform;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * 外見検査OKの場合、Cassandra用のマッピングクラス
 */
public class ConversionOkFunction extends AbstractFunction {

	/**
	 * 外見検査OKの場合、Cassandra用のマッピングメソッド
	 * @param params [0]：連動データリスト
	 * @return Cassandra用にマッピングされたデータ
	 * @throws Exception マッピング失敗(LogId又はSignalId取得失敗、データベース切断等) 
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		List<TrnEvent> result = new ArrayList<TrnEvent>();

		// objList == List<InterlockVisualInspection>
		List<Object> objList = ClassUtil.autoCast(params[0]);
		
		for (Object obj : objList) {
			
			//　Cassandaへの挿入データ取得
			result.addAll(convert(obj));
			
			//　払出/戻し検査及び追加
			InterlockVisualInspection source = (InterlockVisualInspection)obj;
			List<TrnEvent> te = new SelectCassDataFunction().execute(source.getSerial_number(), source.getEntry_date_time());
			if (te != null) {
				result.addAll(te);
			}
		}
		
		return ClassUtil.autoCast(result);
	}
	
	/**
	 * Cassandra用にマッピングするメソッド
	 * @param source 連動データリスト
	 * @return 取得結果
	 * @throws Exception SignalId又はLogId取得失敗、データベース切断等
	 */
	@SuppressWarnings({ "serial" })
	private List<TrnEvent> convert(Object source) throws Exception {
		List<TrnEvent> res = new ArrayList<TrnEvent>();
		InterlockVisualInspection ivi = (InterlockVisualInspection)source;
		GetEventsFunction gef = new GetEventsFunction();
		SignalInform signalInform = config.idmMap.get(Constants.IDM_ID_OK);
		
		// visual_inspectionからTrnEvent取得
		//　一つの連動データから
		//　複数のCassandraへのデータを取得
		res.addAll(
				gef.execute(
						//　取得元
						ivi
						//　LogId, SignalIdを取得する為のデータ
						,new HashMap<String, String>() {{ 
							put("signal_class", signalInform.major);
							put("signal_category", signalInform.medium);
							put("signal_name_suffix", "");
							put("connector_instance", "MstMigrator");
							put("subject_instance", "none");
							put("value_instance", "none");
						}}
						//　取得対象のカラム名
						,Arrays.asList(new String[] {
								"place_id"
								,"serial_number"
								,"entry_date_time"
								,"entry_worker"
								,"judgment_key"
								,"created_by"
								,"created_at"
						})
						,ivi.getJudgment_key()
						,ivi.getEntry_date_time()));
		
		return res;
	}
}
