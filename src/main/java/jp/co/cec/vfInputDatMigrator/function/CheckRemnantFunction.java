package jp.co.cec.vfInputDatMigrator.function;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockSeqService;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * 連動順データに残りがあるかをチェックするクラス
 */
public class CheckRemnantFunction extends AbstractFunction {

	/**
	 * 連動順データに残りがあるかをチェックするメソッド
	 * @param params 要らない
	 * @return 残りがあるかどうか
	 * @throws Exception チェック失敗(データベース切断等)
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		int result = 0;
		
		InterlockSeqService iss = new InterlockSeqService(MariaDbConnector.MarKey.V1);
		result = iss.checkRemnantInterlockSeq();
		
		return ClassUtil.autoCast(result);
	}
}
