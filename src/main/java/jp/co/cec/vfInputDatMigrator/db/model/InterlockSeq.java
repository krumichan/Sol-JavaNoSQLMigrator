package jp.co.cec.vfInputDatMigrator.db.model;

import java.sql.Timestamp;

public class InterlockSeq {
	private Timestamp execDateTime;
	private String execDml;
	private String execTable;
	private String key1;
	private String key2;
	private String key3;
	private String key4;
	public Timestamp getExecDateTime() {
		return execDateTime;
	}
	public void setExecDateTime(Timestamp execDateTime) {
		this.execDateTime = execDateTime;
	}
	public String getExecDml() {
		return execDml;
	}
	public void setExecDml(String execDml) {
		this.execDml = execDml;
	}
	public String getExecTable() {
		return execTable;
	}
	public void setExecTable(String execTable) {
		this.execTable = execTable;
	}
	public String getKey1() {
		return key1;
	}
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public String getKey2() {
		return key2;
	}
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	public String getKey3() {
		return key3;
	}
	public void setKey3(String key3) {
		this.key3 = key3;
	}
	public String getKey4() {
		return key4;
	}
	public void setKey4(String key4) {
		this.key4 = key4;
	}
	@Override
	public String toString() {
		return "InterlockSeq [execDateTime=" + execDateTime + ", execDml=" + execDml + ", execTable=" + execTable
				+ ", key1=" + key1 + ", key2=" + key2 + ", key3=" + key3 + ", key4=" + key4 + "]";
	}
}
