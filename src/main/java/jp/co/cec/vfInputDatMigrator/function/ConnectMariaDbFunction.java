package jp.co.cec.vfInputDatMigrator.function;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;

/**
 * V1とV2 MariaDbの接続をテストするクラス
 */
public class ConnectMariaDbFunction extends AbstractFunction {
	
	/**
	 * V1とV2 MariaDbの接続をテストするメソッド
	 * @param params 要らない
	 * @throws Exception MariaDb接続失敗
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		
		//　mariaDBのV1接続テスト
		String host = config.v1ServerAddress;
		String dbName = config.v1DBName;
		String user = config.v1UserName;
		String pass = config.v1Password;
		try {
			MariaDbConnector.connect(MariaDbConnector.MarKey.V1, host, dbName, user, pass);
		} catch (Exception e) {
			throw new Exception(dbName + "データベースに接続できません。" + "\n" + " fail cause : " + e.getMessage());
		}

		//　mariaDBのV2接続テスト
		host = config.v2ServerAddress;
		dbName = config.v2DBName;
		user = config.v2UserName;
		pass = config.v2Password;
		try {
			MariaDbConnector.connect(MariaDbConnector.MarKey.V2, host, dbName, user, pass);
		} catch (Exception e) {
			throw new Exception(dbName + "データベースに接続できません。" + "\n" + " fail cause : " + e.getMessage());
		}
		
		return null;
	}
}
