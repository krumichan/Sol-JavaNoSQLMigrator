package jp.co.cec.vfInputDatMigrator.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class TestDbUtils {
	
	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
		}
	}
	
	public static void close(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
		}
	}

	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
		}
	}
	
	public static void close(Session session) {
		try {
			session.close();
		} catch (Exception e) {
		}
	}
	
	public static void close(Cluster cluster) {
		try {
			cluster.close();
		} catch (Exception e) {
		}
	}
}
