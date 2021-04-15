package jp.co.cec.vfInputDatMigrator.function;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockSeq;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockVisualInspection;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockWorkStatus;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockSeqService;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockVisualInspectionService;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockWorkStatusService;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;
import jp.co.cec.vfInputDatMigrator.util.FormatUtil;

/**
 * Cassandraに挿入済みの連動中データと連動データを削除するクラス
 */
public class DeleteProccessedFunction extends AbstractFunction {

	/**
	 *Cassandraに挿入済みの 連動順データと連動データを削除するメソッド
	 * @param params [0]：連動データマップ / [1]：連動順データ
	 * @return 削除成功要否
	 * @throws Exception 削除失敗
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		
		//　成功結果
		boolean result = false; 
		
		//　連動データ
		//　dataMapのキー：処理方法フラグ (外観検査OK, 外観検査NG, 外観検査以外)
		//　dataMapの値：連動データリスト　(連動データ種類別のリスト)
		//　構成： Map< flag(Integer), List< [interlock_visual_inspection](list), [interlock_work_status](list) > >
		//　[interlock_visual_inspection](list)又は[interlock_work_status](list)が存在しない場合もある
		Map<Integer, List<Object>> dataMap = ClassUtil.autoCast(params[0]);
		
		//　連動順データ
		InterlockSeq is = (InterlockSeq)params[1];
		
		InterlockVisualInspectionService ivis = new InterlockVisualInspectionService(MariaDbConnector.MarKey.V1);
		InterlockWorkStatusService iwss = new InterlockWorkStatusService(MariaDbConnector.MarKey.V1);
		InterlockSeqService iss = new InterlockSeqService(MariaDbConnector.MarKey.V1);
		try {
			//　連動データ削除
			//　削除対象:interlock_work_status
			for (Entry<Integer, List<Object>> entry : dataMap.entrySet()) {
				deleteWorkStatus(iwss, entry.getValue());
				
			}
			
			//　連動データ削除
			//　削除対象:interlock_visual_inspection
			for (Entry<Integer, List<Object>> entry : dataMap.entrySet()) {
				//　連動データ削除処理
				//　外観検査NGの場合は、連動データと一緒に連動順データも削除
				//　⇒ interlock_data_itemの場合、
				//　それに該当するinterlock_visual_inspectionのCHK_NGのデータを
				//　連動順から探し、削除
				deleteVisualInspection(ivis, iwss, iss, entry.getValue());
			}
			
			//　使用済みの連動順データ削除
			deleteSequence(iss, is);
			
			// 成功したら、コミット
			MariaDbConnector.getSession(MariaDbConnector.MarKey.V1).commit();
			result = true;
		} catch (Exception e) {
			
			//　失敗したら、ロールバック
			MariaDbConnector.getSession(MariaDbConnector.MarKey.V1).rollback();
			throw e;
		} finally {
		}
		
		return ClassUtil.autoCast(result);
	}
	
	/**
	 * interlock_work_status削除処理
	 * @param iwss interlock_work_StatusのDB連動インスタンス
	 * @param delTarget 削除対象の連動データ
	 */
	private void deleteWorkStatus(InterlockWorkStatusService iwss, Object delTarget) {
		
		//　削除対象がリストかをチェック
		if (delTarget instanceof List<?>) {
			
			//　リストの場合、再帰処理
			for (Object del : (List<?>)delTarget) {
				deleteWorkStatus(iwss, del);
			}
		} else {
			//　削除対象がリストではなければ、削除
			
			if (delTarget instanceof InterlockWorkStatus) {
				//　削除対象がinterlock_work_statusの場合
				InterlockWorkStatus iws = (InterlockWorkStatus) delTarget;
				iwss.deleteInterlockWorkStatusUsingPrimaryKey(
						iws.getExec_date_time().toLocalDateTime()
						,iws.getExec_data_flag()
						,iws.getPlace_id()
						,iws.getSerial_number()
						,iws.getEntry_date_time().toLocalDateTime()
						,iws.getWork_status_key());
			}
		}
	}
	
	/**
	 * interlock_visual_inspection削除処理
	 * @param ivis　interlock_visual_inspectionのDB連動インスタンス
	 * @param iwss　interlock_work_StatusのDB連動インスタンス
	 * @param iss　interlock_seqのDB連動インスタンス
	 * @param delTarget　削除対象の連動データ
	 */
	private void deleteVisualInspection(InterlockVisualInspectionService ivis, InterlockWorkStatusService iwss, InterlockSeqService iss, Object delTarget) {
		
		//　削除対象がリストかをチェック
		if (delTarget instanceof List<?>) {
			
			//　リストの場合、再帰処理
			for (Object del : (List<?>)delTarget) {
				deleteVisualInspection(ivis, iwss, iss, del);
			} 
		}else {
			//　削除対象がリストではなければ、削除
			
			if (delTarget instanceof InterlockVisualInspection) {
				// 削除対象がinterlock_visual_inspectionの場合
				//　削除条件
				//　- CHK_OKの場合、条件無しで削除
				//　- CHK_NGの場合、[place_id, serial_number, entry_date_time, ng_work_status_key]をキーとして
				//　   interlock_work_statusに検索し、有ったら、削除しないようにする
				InterlockVisualInspection ivi = (InterlockVisualInspection) delTarget;
				
				//　削除判断フラグ
				boolean delFlag = false;
				
				if (ivi.getJudgment_key().equals("CHK_OK")) {
					//　外観検査OKの場合
					delFlag = true;
				} else if (ivi.getJudgment_key().equals("CHK_NG")) {
					//　外観検査NGの場合
					delFlag = iwss.checkInterlockWorkStatus(
							ivi.getPlace_id()
							,ivi.getSerial_number()
							,FormatUtil.formatDateToStringUnder3(ivi.getEntry_date_time())
							,ivi.getNg_work_status_key()).isEmpty();
				}
				
				if (delFlag) {
					//　interlock_visual_inspectionからデータ削除
					ivis.deleteInterlockVisualInspectionUsingPrimaryKey(
							ivi.getExec_date_time().toLocalDateTime()
							,ivi.getExec_data_flag()
							,ivi.getPlace_id()
							,ivi.getSerial_number()
							,ivi.getEntry_date_time().toLocalDateTime());
					
					//　interlock_visual_insepctionから削除したデータを連動データでも削除
					iss.deleteInterlockSeqUsingPrimaryKey(
							ivi.getExec_date_time().toLocalDateTime()
							,"trn_visual_inspection");
				}
			}
		}
	}
	
	/**
	 * 連動順データ削除処理
	 * @param iss　interlock_seqのDB連動インスタンス
	 * @param is　削除対象の連動順データ
	 */
	private void deleteSequence(InterlockSeqService iss, InterlockSeq is) {
		iss.deleteInterlockSeqUsingPrimaryKey(
				is.getExecDateTime().toLocalDateTime()
				,is.getExecTable());
	}
}
