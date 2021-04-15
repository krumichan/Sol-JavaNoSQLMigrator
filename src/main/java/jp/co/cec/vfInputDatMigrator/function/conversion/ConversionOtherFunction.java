package jp.co.cec.vfInputDatMigrator.function.conversion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jp.co.cec.vfInputDatMigrator.constant.Constants;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockWorkStatus;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.model.ApplicationConfigurationModel.SignalInform;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * 外見検査以外の場合、Cassandra用のアッピングクラス
 *
 */
public class ConversionOtherFunction extends AbstractFunction {

	//　登録、完了、取消フラグ
	private enum FLAG {
		REGISTER
		,COMPLETE
		,CANCEL
		,NONE
	}
	
	/**
	 * 外見検査以外の場合、Cassandra用のマッピングメソッド
	 * @param params [0]：連動データリスト
	 * @return Cassandra用にマッピングされたデータ
	 * @throws Exception マッピング失敗(該当するSignalId又はlogId無し、データベース切断等)
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		List<TrnEvent> result = new ArrayList<TrnEvent>();
		
		// objList -> [ InterlockWorkStatus... ]
		//　必ずInterlockWorkStatus形式でデータが入るので明示的置換可能
		List<Object> objList = ClassUtil.autoCast(params[0]);
		
		for (Object obj : objList) {
			FLAG flag = FLAG.NONE;
			
			InterlockWorkStatus iws = (InterlockWorkStatus)obj;
			boolean inspectionFlag = iws.getInspection_flg() == 1 ? true : false;
			boolean cancelFlag = iws.getCancel_flg() == 1 ? true : false;
			
			//　フラグを基に「登録、完了、取消」を判定
			//　cancel_flg = 1 / inspection_flg = 0 ⇒ 取消
			//　cancel_flg = 1 / inspection_flg = 1 ⇒ 取消
			//　cancel_flg = 0 / inspection_flg = 1 ⇒ 完了
			//　cancel_flg = 0 / inspection_flg = 0 ⇒ 登録
			if (cancelFlag) {
				flag = FLAG.CANCEL;		//　取消
			} else if (inspectionFlag) {
				flag = FLAG.COMPLETE;	//　完了
			} else {
				flag = FLAG.REGISTER;	// 登録
			}
			
			result.addAll(convert(obj, flag));
		}
		
		return ClassUtil.autoCast(result);
	}
	
	/**
	 * Cassandra用にマッピングするメソッド
	 * @param source 対象連動データリスト
	 * @param flag 取得方法(登録、完了、取消)
	 * @return 取得結果
	 * @throws Exception マッピング失敗(該当するSignalId又はlogId無し、データベース切断等)
	 */
	@SuppressWarnings({ "serial" })
	private List<TrnEvent> convert(Object source, FLAG flag) throws Exception {
		List<TrnEvent> res = new ArrayList<TrnEvent>();
		InterlockWorkStatus iws = (InterlockWorkStatus)source;
		GetEventsFunction gef = new GetEventsFunction();
		SignalInform signalInform = config.idmMap.get(Constants.IDM_ID_OTHER);
		
		// work_statusからTrnEvent取得
		switch (flag) {
		case REGISTER:
			//　登録
			res.addAll(
					gef.execute(
							//　取得元
							iws
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
									,"work_status_key"
									,"entry_worker"
									,"status_reason_key"
									,"status_reason"
									,"note"
									,"cancel_flg"
									,"inspection_flg"
									,"inspection_updated_at"
									,"created_by"
									,"created_at"
							})
							,iws.getWork_status_key()
							,iws.getEntry_date_time()));
			break;
			
		case COMPLETE:
			//　完了
			if (iws.getWork_status_key().equals("INSPECTION")) {
				res.addAll(
						gef.execute(
								//　取得元
								iws
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
										,"work_status_key"
										,"entry_worker"
										,"status_reason_key"
										,"status_reason"
										,"note"
										,"cancel_flg"
										,"inspection_flg"
										,"inspection_updated_at"
										,"created_by"
										,"created_at"
								})
								,iws.getWork_status_key()
								,iws.getInspection_updated_at()));
			}
			break;
			
		case CANCEL:
			//　取消
			res.addAll(
					gef.execute(
							//　取得元
							iws
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
									,"work_status_key"
									,"entry_worker"
									,"status_reason_key"
									,"status_reason"
									,"note"
									,"cancel_flg"
									,"cancel_date_time"
									,"cancel_reason"
									,"cancel_worker"
									,"inspection_flg"
									,"inspection_updated_at"
									,"created_by"
									,"created_at"
							})
							,iws.getWork_status_key()
							,iws.getCancel_date_time()));
			break;
			
		default:
			break;
		}
		
		return res;
	}
}
