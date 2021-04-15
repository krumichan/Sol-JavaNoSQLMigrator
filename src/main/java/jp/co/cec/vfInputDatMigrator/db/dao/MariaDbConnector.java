package jp.co.cec.vfInputDatMigrator.db.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;

public class MariaDbConnector {

	/**
	 * SQLセッションファクトリーサービス
	 */
	private static Map<MarKey, SqlSessionFactoryCustom> factory;
	
	/**
	 * データベース接続
	 */
	private static Map<MarKey, SqlSession> session;
	
	
	/**
	 * MariaDbへの接続
	 * @param key　接続先がV1かV2か
	 * @param address　接続先のアドレス
	 * @param dbName　接続先のDB名
	 * @param user　接続先のユーザ名
	 * @param pass　接続先のパスワード
	 * @throws Exception　接続失敗
	 */
	public static void connect(MarKey key, String address, String dbName, String user, String pass) throws Exception {
		if (factory == null) {
			factory = new HashMap<MarKey, SqlSessionFactoryCustom>();
		}
		
		if (session == null) {
			session = new HashMap<MarKey, SqlSession>();
		}
		
		String host = "jdbc:mariadb://" + address + "/" + dbName;

		Properties prop = new Properties();
		prop.put("mariaDbHost", host);
		prop.put("mariaDbUserName", user);
		prop.put("mariaDbPassword", pass);
		
		SqlSessionFactoryCustom fact = new SqlSessionFactoryCustom(prop);
		SqlSession sess = fact.openSession(false);
		
		//　正常に接続されたのかをチェック
		sess.getConnection().createStatement().execute("SHOW DATABASES LIKE '" + dbName  + "' ");
		
		factory.put(key, fact);
		session.put(key, sess);
	}
	
	/**
	 * セッションを取得
	 * @param key 接続先がV1かV2か
	 * @return セッション
	 */
	public static SqlSession getSession(MarKey key) {
		return session.get(key);
	}
	
	/**
	 * Sessionをクローズする
	 */
	public static void close() {
		if (!Objects.isNull(session)) {
			for (SqlSession ses : session.values()) {
				ses.close();
			}
		}
	}
	
	public enum MarKey {
		V1
		,V2
	}
}
