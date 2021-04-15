package jp.co.cec.vfInputDatMigrator.function;

import java.util.List;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import jp.co.cec.vfInputDatMigrator.constant.Constants;
import jp.co.cec.vfInputDatMigrator.db.dao.CassandraConnector;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * Cassandra用のデータ挿入クラス
 */
public class InsertCassandraFunction extends AbstractFunction {

	/**
	 * Caasandra用のデータ挿入メソッド
	 * @param trnEventList
	 * @return
	 * @throws Exception
	 */
	public <T> T execute(Object... params) throws Exception {
		boolean result = false;
		
		//　Cassandraへの挿入対象リスト
		List<TrnEvent> trnEventList = ClassUtil.autoCast(params[0]);
		
		String keyspace = config.v2DBKeyspace;
		String tableName = Constants.V2_CASSANDRA_TABLE_NAME;

		// V2 Cassandraへのセッション取得
		final Session session = CassandraConnector.getSession(CassandraConnector.CassKey.V2);
		
		for (TrnEvent event : trnEventList) {
			//　挿入処理
			session.execute(getQuery(keyspace, tableName, event));
		}
		
		//　挿入成功結果
		result = true;
		return ClassUtil.autoCast(result);
	}
	
	/**
	 * Cassandra用のクエリ生成メソッド
	 * @param keyspace Cassandraのkeyspace
	 * @param tableName Cassandraのtable name
	 * @param event 挿入対象のtrn eventデータ
	 * @return Cassandra挿入クエリ
	 */
	private String getQuery(String keyspace, String tableName, TrnEvent event) {
		return QueryBuilder.insertInto(keyspace, tableName)
				.value("log_id", event.getLogId())
				.value("subject", event.getSubject())
				.value("log_date_time", event.getLogDateTime())
				.value("log_date", event.getLogDate())
				.value("log_end_date_time", event.getLogEndDateTime())
				.value("value_key", event.getValueKey())
				.value("value", event.getValue())
				.value("map_value", event.getMapValue())
				.value("updated_by", event.getUpdatedBy())
				.value("updated_at", event.getUpdatedAt())
				.value("signal_id", event.getSignalId())
				.toString();
	}
}
