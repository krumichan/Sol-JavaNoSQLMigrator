package jp.co.cec.vfInputDatMigrator.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 設定ファイルのデータモデル 
 */
public class ApplicationConfigurationModel {
	
	/**
	 * V1サーバ接続先<br>
	 * 移行元DBサーバのアドレス<br>
	 * 初期値：無し
	 */
	public String v1ServerAddress;
	
	/**
	 * V1DB名<br>
	 * 移行元DB名<br>
	 * 初期値：無し
	 */
	public String v1DBName;
	
	/**
	 * V1DBユーザ名<br>
	 * 移行元ユーザ名<br>
	 * 初期値：無し
	 */
	public String v1UserName;
	
	/**
	 * V1DBパスワード<br>
	 * 移行元パスワード<br>
	 * 初期値：無し
	 */
	public String v1Password;
	
	/**
	 * V1Cassandraサーバ接続先<br>
	 * 移行元サーバのアドレスポート番号<br>
	 */
	public String v1CassServerAddress;
	
	/**
	 * V1DBKeySpace<br>
	 * 移行元CassandraのKeyspace<br>
	 */
	public String v1DBKeyspace;
	
	/**
	 * V2MariaDBサーバ接続先<br>
	 * 移行先サーバのアドレスポート番号<br>
	 * 初期値：localhost:3306
	 */
	public String v2ServerAddress;
	
	/**
	 * V2DB名<br>
	 * 移行元DB名<br>
	 * 初期値：無し
	 */
	public String v2DBName;
	
	/**
	 * V2DBユーザ名<br>
	 * 移行元ユーザ名<br>
	 * 初期値：無し
	 */
	public String v2UserName;
	
	/**
	 * V2DBパスワード<br>
	 * 移行元パスワード<br>
	 * 初期値：無し
	 */
	public String v2Password;
	
	/**
	 * V2DBKeySpace<br>
	 * 移行先CassandraのKeyspace<br>
	 * 初期値：
	 */
	public String v2DBKeyspace;
	
	/**
	 * V2Cassandraサーバ接続先<br>
	 * 移行先サーバのアドレスポート番号<br>
	 * 初期値：localhost:9042
	 */
	public String v2CassServerAddress;
	
	/**
	 * ログ出力先<br>
	 * エラーログ出力フォルダ<br>
	 * 初期値：無し
	 */
	public String LogPath;
	
	/**
	 * 古いデータの保存期間<br>
	 * 連動順と連動データの古いものを削除(d,h,m,s)<br>
	 * 初期値：30d
	 */
	public String oldDataDel;
	
	/**
	 * リトライ回数<br>
	 * 回<br>
	 * 初期値：3
	 */
	public String retryCount;
	
	/**
	 * リトライのインターバル<br>
	 * 秒<br>
	 * 初期値：10
	 */
	public String retryInterval;
	
	/**
	 * 連動順データ取得件数<br>
	 * 件<br>
	 * 初期値：1000
	 */
	public String selLimit;
	
	/**
	 * visual_inspection対象<br>
	 * 配列<br>
	 */
	public String[] viInStr;
	
	/**
	 * work_status対象<br>
	 * 配列<br>
	 */
	public String[] wsInStr;
	
	/**
	 * ログID及び信号IDを取得する際、使用するシグナル情報
	 */
	public List<IdmMaster> idmMaster;
	
	/**
	 * idmMasterをマップ形式に保存する為のマップ<br>
	 * 構成：<id, signalInform> <br>
	 */
	public Map<Integer, SignalInform> idmMap = new HashMap<Integer, SignalInform>();
	
	/**
	 * シグナル情報を持っているクラス
	 */
	public static class IdmMaster {
		
		/**
		 * シグナル情報を区別する為のID
		 */
		public Integer id;
		
		/**
		 * シグナル情報
		 */
		public SignalInform signalInform;
	}
	
	/**
	 * シグナル情報クラス
	 */
	public static class SignalInform {
		
		/**
		 *　シグナル情報の説明 
		 */
		public String comment;
		
		/**
		 * シグナルクラス
		 */
		public String major;
		
		/**
		 * シグナルカテゴリ
		 */
		public String medium;
	}
}
