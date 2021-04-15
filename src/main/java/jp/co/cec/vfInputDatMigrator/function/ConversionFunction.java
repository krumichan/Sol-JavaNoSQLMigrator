package jp.co.cec.vfInputDatMigrator.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.function.conversion.ConversionNgFunction;
import jp.co.cec.vfInputDatMigrator.function.conversion.ConversionOkFunction;
import jp.co.cec.vfInputDatMigrator.function.conversion.ConversionOtherFunction;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * 連動データをCassandra用にマッピングするクラス
 */
public class ConversionFunction extends AbstractFunction {
	
	/**
	 * 連動データをCassandra用にマッピングするメソッド
	 * @param params [0]：連動データリスト
	 * @return Cassandra用にマッピングされたデータリスト
	 * @throws Exception マッピング失敗(LogId又はSignalId取得失敗、データベース切断等)
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		
		//　Cassnadraへの挿入データリスト
		List<TrnEvent> result = new ArrayList<TrnEvent>();
		
		//　対象連動データ
		//　dataMapのキー：処理方法フラグ (OK, NG, 外観検査以外)　と　移行対象外フラグ
		//   -- 0  : 外観検査OK
		//   -- 1  : 外観検査NG
		//   -- 2  : 外観検査以外
		//   -- -1 : 移行対象外
		// dataMapの値：対象データリスト
		//   -- 中身 : interlock_visual_insepctionとinsterlock_work_statusのリスト
		//　　中身例） List [ ivi, ivi... , iws, iws... ]
		Map<Integer, List<Object>> dataList = ClassUtil.autoCast(params[0]);
		
		for (Entry<Integer, List<Object>> entry : dataList.entrySet()) {
			switch (entry.getKey()) {
			case 0:
				//　外観検査OK
				result = new ConversionOkFunction().execute(entry.getValue());
				break;
			case 1:
				//　外観検査NG
				result = new ConversionNgFunction().execute(entry.getValue());
				break;
			case 2:
				//　外観検査以外
				result = new ConversionOtherFunction().execute(entry.getValue());
				break;
			}
		}
		
		return ClassUtil.autoCast(result);
	}
}