package jp.co.cec.vfInputDatMigrator.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import jp.co.cec.vfInputDatMigrator.dto.RawTrnStatus;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.utility.TestJsonUtil;

public class TestInputDatMigratorCassDao {

	private String address = null;
	private Integer port = null;
	
	public TestInputDatMigratorCassDao(String address) {
		this.address = address.substring(0, address.indexOf(":"));
		this.port = Integer.parseInt(
				address.substring(address.indexOf(":") + 1)
				);
	}
	
	
	public void runScript(String filename) throws Exception {
		String filePath = "VfInputDatMigrator" + "/" + filename;
		filePath = TestJsonUtil.getResourceFileFullPath(filePath);
		
		StringBuilder sb = new StringBuilder();
		Cluster cluster = null;
		Session session = null;
		try (FileInputStream fis = new FileInputStream(filePath);
			 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			 BufferedReader br = new BufferedReader(isr);){
			cluster = connect();
			session = cluster.connect();
			
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) {
					continue;
				}
				
				sb.append(line + "\n");
				if (line.contains(";")) {
					session.execute(sb.toString());
					sb = new StringBuilder();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
			cluster.close();
		}
	}
	
	public List<TrnEvent> select(String keyspace, String tableName) throws Exception {
		List<TrnEvent> res = new ArrayList<TrnEvent>();
		
		String cql = " SELECT * FROM " + keyspace + "." + tableName;
		
		Cluster cluster = null;
		Session session = null;
		ResultSet rs = null;
		try {
			cluster = connect();
			session = cluster.connect();
			rs = session.execute(cql);
			List<Row> rowList = rs.all();
			for (Row row : rowList) {
				RawTrnStatus rts = new RawTrnStatus();
				rts.setLogId(row.getString("log_id"));
				rts.setSubject(row.getString("subject"));
				rts.setLogDateTime(row.getTimestamp("log_date_time"));
				rts.setLogDate(row.getTimestamp("log_date"));
				rts.setLogEndDateTime(null);
				rts.setValueKey(row.getString("value_key"));
				rts.setValue(row.getString("value"));
				rts.setMapValue(row.getMap("map_value", Date.class, String.class));
				rts.setUpdatedBy(row.getString("updated_by"));
				rts.setUpdatedAt(row.getTimestamp("updated_at"));
				rts.setSignalId(row.getString("signal_id"));
				res.add(new TrnEvent(rts));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
			cluster.close();
		}
		
		return res;
	}
	
	public void dropKeySpace(String keyspace) throws Exception {
		String cql = " DROP KEYSPACE IF EXISTS " + keyspace;
		
		Cluster cluster = null;
		Session session = null;
		try {
			cluster = connect();
			session = cluster.connect();
			session.execute(cql);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
			cluster.close();
		}
	}
	
	private Cluster connect() throws Exception {
		Builder b = Cluster.builder().addContactPoint(address);
		b.withPort(port);
		
		return b.build();
	}
}
