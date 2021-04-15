package jp.co.cec.vfInputDatMigrator.function.conversion;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jp.co.cec.vfInputDatMigrator.constant.Constants;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockVisualInspection;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockWorkStatus;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.function.SelectCassDataFunction;
import jp.co.cec.vfInputDatMigrator.model.ApplicationConfigurationModel.SignalInform;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * 外見検査NGの場合、Cassandra用にマッピングするクラス
 */
public class ConversionNgFunction extends AbstractFunction {

	//　登録、完了、取消フラグ
	private enum FLAG {
		REGISTER
		,COMPLETE
		,CANCEL
		,NONE
	}
	
	/**
	 * 外見検査NGの場合、Cassandra用にマッピングするメソッド
	 * @param params 連動データリスト
	 * @return Cassandra用にマッピングされたデータ
	 * @throws Exception
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		List<TrnEvent> result = new ArrayList<TrnEvent>();
		
		//　データはリスト
		//　中身予想： [ interlock_visual_inspection ... , interlock_work_status... ]
		// interlock_work_statusは存在しない場合もある
		List<Object> objList = ClassUtil.autoCast(params[0]);

		InterlockVisualInspection ivi = null;
		for (Object obj : objList) {
			
			//　InterlockVisualInspectionを取得
			if (obj instanceof InterlockVisualInspection) {
				ivi = (InterlockVisualInspection)obj;
			}
			
			//　objがinterlockWorkStatusかをチェック
			FLAG flag = FLAG.NONE;
			if (obj instanceof InterlockWorkStatus &&
				ivi != null) {
				
				//　objがInterlockWorkStatusなら、取得方法判定を行う
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
				
				Timestamp date = null;
				switch (flag) {
				case CANCEL:
					date = iws.getCancel_date_time();
					break;
					
				case COMPLETE:
					date = iws.getInspection_updated_at();
					break;
					
				case REGISTER:
					date = ivi.getEntry_date_time();
					break;
					
					default:
						break;
				}
				
				// inspection　登録
				result.addAll(convert(ivi, date));
				
				// work status 登録
				result.addAll(convert(iws, flag));
			}
		}

		//　払出/戻し検査及び追加
		if (ivi != null) {
			List<TrnEvent> te = new SelectCassDataFunction().execute(ivi.getSerial_number(), ivi.getEntry_date_time());
			if (te != null) {
				result.addAll(te);	
			}
			
		}
		
		return ClassUtil.autoCast(result);
	}
	
	/**
	 * Cassandra用のtrn_eventデータリスト
	 * @param obj 対象(InterlockVisualInspection)
	 * @return　trn_eventデータリスト
	 * @throws Exception signalId又はlogId取得失敗、データベース切断等
	 */
	@SuppressWarnings({ "serial" })
	private List<TrnEvent> convert(InterlockVisualInspection ivi, Timestamp date) throws Exception {
		List<TrnEvent> res = new ArrayList<TrnEvent>();
		GetEventsFunction gef = new GetEventsFunction();
		SignalInform signalInform = config.idmMap.get(Constants.IDM_ID_NG);
		
		// visual_inspectionからTrnEvent取得
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
								"judgment_key"
						})
						,ivi.getNg_work_status_key()
						,date));
		return res;		
	}
	
	/**
	 * Cassandra用のtrn_eventデータリスト
	 * @param obj 対象(InterlockWorkStatus)
 	 * @param flag 取得方法(登録、完了、取消)
	 * @return　trn_eventデータリスト
	 * @throws Exception signalId又はlogId取得失敗、データベース切断等
	 */
	@SuppressWarnings({ "serial" })
	private List<TrnEvent> convert(InterlockWorkStatus iws, FLAG flag) throws Exception {
		List<TrnEvent> res = new ArrayList<TrnEvent>();
		GetEventsFunction gef = new GetEventsFunction();
		SignalInform signalInform = config.idmMap.get(Constants.IDM_ID_NG);

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
