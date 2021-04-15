package jp.co.cec.vfInputDatMigrator.function;

import jp.co.cec.vfInputDatMigrator.db.dao.CassandraConnector;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;

/**
 * Cassandraに接続するクラス
 */
public class ConnectCassandraFunction extends AbstractFunction {
	
	/**
	 * Cassandraに接続するメソッド 
	 * @param params 要らない
	 * @throws Exception 接続失敗
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		
		String server = null;
		String address = null;
		Integer port = null;
		String keyspace = null;
		
		//　CassandraのV1データセット
		try {
			server = config.v1CassServerAddress;
			address = server.substring(0, server.indexOf(":"));
			port = Integer.parseInt(server.substring(server.indexOf(":") + 1));
			keyspace = config.v1DBKeyspace;
		} catch (Exception e) {
			throw new Exception("V1Cassandra設定データに不正があります。", e);
		}	
		
		//　V1 Cassandraへの接続確立
		try {
			CassandraConnector.connect(CassandraConnector.CassKey.V1, address, port, keyspace);
		} catch (Exception e) {
			throw new Exception("V1Cassandra接続に失敗しました。", e);
		}

		//　CassandraのV2データセット
		try {
			server = config.v2CassServerAddress;
			address = server.substring(0, server.indexOf(":"));
			port = Integer.parseInt(server.substring(server.indexOf(":") + 1));
			keyspace = config.v2DBKeyspace;
		} catch (Exception e) {
			throw new Exception("V2Cassandra設定データに不正があります。", e);
		}	

		//　V2 Cassandraへの接続確立
		try {
			CassandraConnector.connect(CassandraConnector.CassKey.V2, address, port, keyspace);
		} catch (Exception e) {
			throw new Exception("V2Cassandra接続に失敗しました。", e);
		}
		
		return null;
	}
}
