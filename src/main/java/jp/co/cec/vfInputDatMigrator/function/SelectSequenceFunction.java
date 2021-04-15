package jp.co.cec.vfInputDatMigrator.function;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockSeqService;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * 連動順データを取得クラス
 * (設定ファイルのselLimitだけの分を取得)
 */
public class SelectSequenceFunction extends AbstractFunction {
	
	/**
	 * 連動順データ取得メソッド
	 * @param params 要らない
	 * @return 取得連動順リスト
	 * @throws Exception 取得失敗(データベース切断等)
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		try {
			int selLimit = Integer.parseInt(config.selLimit);

			return ClassUtil.autoCast(new InterlockSeqService(MariaDbConnector.MarKey.V1).queryInterlockSeqs(selLimit));
		} catch (Exception e) {
			throw new Exception("selLimit(" + config.selLimit + ")が不正です。");
		}
	}
}
