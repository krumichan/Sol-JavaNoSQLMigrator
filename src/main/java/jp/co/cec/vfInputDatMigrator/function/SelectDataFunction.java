package jp.co.cec.vfInputDatMigrator.function;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockSeq;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockVisualInspection;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockWorkStatus;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockVisualInspectionService;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockWorkStatusService;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * 連動データ取得クラス
 */
public class SelectDataFunction extends AbstractFunction {
	
	/**
	 * 連動データ取得メソッド
	 * v1方のinterlock_visual_insepction又はinterlock_work_statusから取得
	 * @param params [0]：連動順データ
	 * @return 取得データリスト(1個か2個)
	 * @throws Exception 取得失敗(データベース切断等)
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		InterlockSeq is = (InterlockSeq)params[0];
		
		String execTable = is.getExecTable();
		LocalDateTime execDateTime = is.getExecDateTime().toLocalDateTime();
		String key1 = is.getKey1();
		String key2 = is.getKey2();
		String key3 = is.getKey3();
		String key4 = is.getKey4();
		
		//　マップキーの意味
		//　0 : OK
		//　1 : NG
		//　2 : 外観検査以外
		if (execTable.equals("trn_visual_inspection")) {
			//　insterlock_visual_inspectionから連動データ取得
			
			if (key4 != null) {
				
				//　処理方法がOKかNGかを判断
				Integer key = key4.equals("CHK_OK") ? 0 : -1;
				
				if (key == 0) {
					//　interlock_visual_inspectionから連動データ取得
					InterlockVisualInspectionService ivis = new InterlockVisualInspectionService(MariaDbConnector.MarKey.V1);
					List<InterlockVisualInspection> iviList = 
							ivis.linesInterlockVisualInspection(key1 ,key2 ,key3 ,key4);
					if (iviList == null || iviList.isEmpty()) {
						return null;
					}
					
					//　マップに連動データ挿入
					map.put(key, Arrays.asList(iviList));
				}
			}
			
		} else if (execTable.equals("trn_work_status")) {
			//　insterlock_work_statusとinterlock_visual_inspectionから連動データ取得
			
			//　interlock_visual_inspectionから連動データ取得
			//　存在しない場合もある
			InterlockVisualInspectionService ivis = new InterlockVisualInspectionService(MariaDbConnector.MarKey.V1);
			List<InterlockVisualInspection> iviList = 
					ivis.linesInterlockVisualInspectionNonJudgmentKey(key1 ,key2 ,key3);
			
			// interlock_work_statusから連動データ取得
			InterlockWorkStatusService iwss = new InterlockWorkStatusService(MariaDbConnector.MarKey.V1);
			List<InterlockWorkStatus> iwsList = 
					iwss.linesTrnWorkStatus(execDateTime, key1 ,key2 ,key3 ,key4);
			
			//　マップに連動データ挿入
			if (iwsList == null || iwsList.isEmpty()) {
				return null;
			} else if(iviList == null || iviList.isEmpty()) {
				map.put(2, Arrays.asList(iwsList));
			} else {
				map.put(1, Arrays.asList(iviList, iwsList));
			}
			
		}
		
		return ClassUtil.autoCast(map);
	}
}
