package jp.co.cec.vfInputDatMigrator.function;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import jp.co.cec.vfInputDatMigrator.constant.Constants;
import jp.co.cec.vfInputDatMigrator.db.dao.CassandraConnector;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.function.conversion.ConversionGetBackFunction;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.ClassUtil;

/**
 * Cassandraからデータを取得するクラス
 */
public class SelectCassDataFunction extends AbstractFunction {
	
	/**
	 * Caasandraからデータを取得するメソッド
	 * @param params [0]：visual inspectionのserial_number / [1]：visual inspectionのentry_date_time
	 * @param serialNumber
	 * @return 取得データ
	 * @throws Exception　取得失敗
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		List<TrnEvent> res = new ArrayList<TrnEvent>();
		
		String keyspace = config.v1DBKeyspace;
		String tableName = Constants.V1_CASSANDRA_TABLE_NAME;
		String serialNumber = (String)params[0];
		Timestamp entryDateTime = (Timestamp)params[1];

		final Session session = CassandraConnector.getSession(CassandraConnector.CassKey.V1);
		
		Statement stmt = getQuery(keyspace, tableName, serialNumber);
		List<Row> results = session.execute(stmt).all();
		if (results == null || results.isEmpty()) {
			return ClassUtil.autoCast(res);
		}
		
		//　work_status_key, judgement_key, EJECTION, GETBACKを含めているデータのみ抽出
		results = filterByKey(results);
		
		//　date_timeが連動データのendtry_date_timeの以前のもののみ抽出
		results = filterByDateTime(results, entryDateTime);
		
		//　date_timeを降順でソート
		sorting(results);
		
		//　date_timeが最新のものを取得
		Row row = results.get(0);
		if (row != null && row.getString("value").equals("GET_BACK")) {
			//　最新行がGET_BACKの場合、払出戻しデータを生成
			res.addAll(new ConversionGetBackFunction().execute(row));
			return ClassUtil.autoCast(res);
		}
		
		return ClassUtil.autoCast(res);
	}
	
	/**
	 * Cassandra用のクエリ生成メソッド
	 * @param keyspace Cassandraのkeyspace
	 * @param tableName Cassandraのtable name
	 * @param event 挿入対象のtrn eventデータ
	 * @return Cassandra挿入クエリ
	 */
	private Statement getQuery(String keyspace, String tableName, String serialNumber) {
		return QueryBuilder.select()
				.column("log_date")
				.column("log_id")
				.column("value")
				.fcall("blobAsBigInt", QueryBuilder.fcall("timestampAsBlob", QueryBuilder.column("log_date_time"))).as(Constants.ALIAS)
				.column("original_value")
				.column("subject")
				.column("updated_at")
				.column("updated_by")
				.from(keyspace, tableName)
				.where(QueryBuilder.eq("subject", serialNumber));
	}
	
	/**
	 * 強制戻しを求める際、絞込時にフィルターするメソッド
	 * @param rows　V1 Cassandraから取得した対象データ
	 * @return 対象データのフィルター処理結果
	 */
	private List<Row> filterByKey(List<Row> rows) {
		List<Row> result = new ArrayList<Row>();
		for (Row row : rows) {
			String key = row.getString("value");
			if (Constants.FILTER_ARRAY.contains(key)) {
				result.add(row);
			}
		}
		return result;
	}
	
	/**
	 * 強制戻しを求める際、絞込時にフィルターするメソッド
	 * @param rows　V1 Cassandraから取得した対象データ
	 * @param entryDateTime　フィルター用の時刻
	 * @return 対象データのフィルター処理結果
	 */
	private List<Row> filterByDateTime(List<Row> rows, Timestamp entryDateTime) {
		List<Row> result = new ArrayList<Row>();
		for (Row row : rows) {
			long dateTimeLong = row.getLong(Constants.ALIAS);
			Timestamp dateTime = new Timestamp(dateTimeLong);
			if (dateTime.before(entryDateTime)) {
				result.add(row);
			}
		}
		return result;
	}
	
	/**
	 * 強制戻しを求める際、絞込時にソートするメソッド
	 * @param rows　V1 Cassandraから取得した対象データ
	 */
	private void sorting(List<Row> rows) {
		if (rows.size() < 2) { return; }
		Collections.sort(rows, new Comparator<Row>() {
			@Override
			public int compare(Row r1, Row r2) {
				long r1ts = r1.getLong(Constants.ALIAS);
				long r2ts = r2.getLong(Constants.ALIAS);
				return Long.valueOf(r2ts).compareTo(Long.valueOf(r1ts));
			}
		});
	}
}
