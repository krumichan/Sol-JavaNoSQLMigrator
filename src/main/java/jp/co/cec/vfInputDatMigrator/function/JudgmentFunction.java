package jp.co.cec.vfInputDatMigrator.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import jp.co.cec.vfInputDatMigrator.db.model.InterlockVisualInspection;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockWorkStatus;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * 連動データからCassandraに挿入除外対象かを判断するクラス
 */
public class JudgmentFunction extends AbstractFunction {

	private Logger myLogger = null;
	public JudgmentFunction() {
		myLogger = LogManager.getLogger("myLogger");
	}
	
	/**
	 * 連動データからCassandraに挿入除外対象かを判断するメソッド
	 * @param params[0]：連動データマップ
	 * @return 除外判断済みの連動データリスト
	 * @throws Exception 判断失敗
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		
		//　対象連動データ
		//　dataMapのキー：処理方法フラグ (OK, NG, 外観検査以外)　と　移行対象外フラグ
		//   -- 0  : 外観検査OK
		//   -- 1  : 外観検査NG
		//   -- 2  : 外観検査以外
		//   -- -1 : 移行対象外
		// dataMapの値：対象データリスト
		//   -- 中身 : interlock_visual_insepctionとinsterlock_work_statusのリスト
		//　　中身例） List [ ivi, ivi... , iws, iws... ]
		Map<Integer, List<Object>> result = new HashMap<Integer, List<Object>>();
		
		//　連動データ
		//　dataMapのキー：処理方法フラグ (外観検査OK, 外観検査NG, 外観検査以外)
		//　dataMapの値：連動データリスト　(連動データ種類別のリスト)
		//　構成： Map< flag(Integer), List< [interlock_visual_inspection](list), [interlock_work_status](list) > >
		//　[interlock_visual_inspection](list)又は[interlock_work_status](list)が存在しない場合もある
		Map<Integer, List<Object>> dataMap = ClassUtil.autoCast(params[0]);
		
		//　対象を入れるリスト
		List<Object> target = new ArrayList<Object>();
		
		//　対象除外を入れるリスト
		List<Object> noneTarget = new ArrayList<Object>();
		
		//　対象判定処理
		for (Entry<Integer, List<Object>> entry : dataMap.entrySet()) {
			judgment(entry.getValue(), target, noneTarget);

			//　あるキーに対する対象がある場合、resultリストに入れる
			if (!target.isEmpty()) {
				result.put(entry.getKey(), target);
			}
		}
		
		//　対象外をログに出力
		if (noneTarget != null && !noneTarget.isEmpty()) {
			printNonTarget(noneTarget);
		}
		
		return ClassUtil.autoCast(result);
	}
	
	/**
	 * 連動データ(interlock_visual_insepction）が除外対象かをチェック
	 * @param ivi 連動データ
	 * @return　判断結果
	 * @throws Exception　判断失敗
	 */
	private boolean judgment(InterlockVisualInspection ivi) throws Exception {
		return Arrays.asList(config.viInStr).contains(ivi.getJudgment_key());
	}
	
	/**
	 * 連動データ(interlock_work_status）が除外対象かをチェック
	 * @param iws 連動データ
	 * @return　判断結果
	 * @throws Exception　判断失敗
	 */
	private boolean judgment(InterlockWorkStatus iws) throws Exception {
		return Arrays.asList(config.wsInStr).contains(iws.getWork_status_key());
	}
	
	/**
	 * 除外判定処理
	 * @param data　対象
	 * @param target　対象リスト
	 * @param nonTarget　除外リスト
	 * @throws Exception　処理失敗
	 */
	private void judgment(
			Object data
			,List<Object> target
			,List<Object> nonTarget) throws Exception {
		
		//　取得データがリストかをチェック
		if (data instanceof List<?>) {
			
			//　リストの場合、再帰処理
			for (Object obj : (List<?>)data) {
				judgment(obj, target, nonTarget);
			}
		} else {
			//　取得データがリストでなければ
			//　対象判定実施
			boolean judge = false;
			if (data instanceof InterlockVisualInspection) {
				judge = judgment((InterlockVisualInspection)data);
			} else if (data instanceof InterlockWorkStatus) {
				judge = judgment((InterlockWorkStatus)data);
			}
			
			if (judge) {
				//　対象の場合、targetリストに入れる
				target.add(data);
			} else {
				//　対象除外の場合、notTargetリストに入れる
				nonTarget.add(data);
			}
		}
	}
	
	/**
	 * 連動データの中で挿入対象外データを出力
	 * @param nonTarget　挿入対象外データ
	 */
	private String printNonTarget(Object target) {
		
		//　ログに出力する内容
		StringBuilder sb = new StringBuilder();
		
		//　出力対象がリストかをチェック
		if (target instanceof List<?>) {
			
			//　リストの場合、再帰処理を行う
			for (Object obj : (List<?>)target) {
				sb.append(printNonTarget(obj));
			}
			
		} else {
			//　リストではなければ、出力文字列を返す
			
			if (target instanceof InterlockVisualInspection) {
				//　対象がinterlock_visual_insepctionの場合
				InterlockVisualInspection ivi = (InterlockVisualInspection)target;
				return sb.append("除外対象：visual_inspection" 
						+ "[" + ivi.getPlace_id() + ","
						+ ivi.getSerial_number() + ","
						+ ivi.getEntry_date_time() + ","
						+ ivi.getJudgment_key() + "]\n"
						).toString();
			} else if (target instanceof InterlockWorkStatus) {
				//　対象がinterlock_work_statusの場合
				InterlockWorkStatus iws = (InterlockWorkStatus)target;
				return sb.append("除外対象：work_status" 
						+ "[" + iws.getPlace_id() + ","
						+ iws.getSerial_number() + ","
						+ iws.getEntry_date_time() + ","
						+ iws.getWork_status_key() + "]\n"
						).toString();
			} else {
				//　何も該当しない場合
				//　基本的には発生しない
				return "";
			}
		}
		
		//　内容出力
		myLogger.info(sb.toString());
		
		return sb.toString();
	}
}
