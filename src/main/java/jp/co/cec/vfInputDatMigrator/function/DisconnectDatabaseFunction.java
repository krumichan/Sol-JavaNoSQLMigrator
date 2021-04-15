package jp.co.cec.vfInputDatMigrator.function;

import jp.co.cec.vfInputDatMigrator.db.dao.CassandraConnector;
import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * データベース切断クラス
 * (MariaDBはmyBatisを使用し、自動的に切断されるので除外)
 */
public class DisconnectDatabaseFunction extends AbstractFunction {

	/**
	 *　データベース切断メソッド
	 * @param params 要らない
	 * @return 切断成功結果 
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		boolean result = false;
		
		CassandraConnector.close();
		MariaDbConnector.close();
		
		result = true;
		return ClassUtil.autoCast(result);
	}
}
