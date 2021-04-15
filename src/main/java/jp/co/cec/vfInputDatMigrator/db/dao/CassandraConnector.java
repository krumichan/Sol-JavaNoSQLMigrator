package jp.co.cec.vfInputDatMigrator.db.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;

/**
 * CassandraDBの接続クラス
 */
public class CassandraConnector {
	private CassandraConnector() { }

	private static Map<CassKey, Cluster> cluster;
	private static Map<CassKey, Session> session;

	/**
	 * Cassnadraへの接続メソッド
	 * @param key 接続先がV1かV2か
	 * @param address 接続先のアドレス
	 * @param port 接続先のポート
	 * @param keyspace 接続先のKeyspace
	 */
	public static void connect(CassKey key, String address, Integer port, String keyspace) {
		if (cluster == null) {
			cluster = new HashMap<CassKey, Cluster>();
		}
		if (session == null) {
			session = new HashMap<CassKey, Session>();
		}
		
		Builder b = Cluster.builder()
				.addContactPoint(address);
		if (port != null) {
			b.withPort(port);
		}
		cluster.put(key, b.build());
		session.put(key, cluster.get(key).connect(keyspace));
	}
	
	/**
	 * セッションを取得
	 * @param key 接続先がV1かV2か
	 * @return セッション
	 */
	public static Session getSession(CassKey key) {
		return session.get(key);
	}
	
	/**
	 * SessionとClusterをクローズする
	 */
	public static void close() {
		if (!Objects.isNull(session)) {
			for (Session ses : session.values()) {
				if (!ses.isClosed()) {
					ses.close();
				}
			}
		}
		if (!Objects.isNull(cluster)) {
			for (Cluster clu : cluster.values()) {
				if (!clu.isClosed()) {
					clu.close();
				}
			}
		}
	}
	
	public enum CassKey {
		V1
		,V2
	}
}
