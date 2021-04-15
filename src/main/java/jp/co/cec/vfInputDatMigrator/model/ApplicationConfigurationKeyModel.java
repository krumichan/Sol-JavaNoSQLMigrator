package jp.co.cec.vfInputDatMigrator.model;

/**
 * 設定データのキークラス
 */
public class ApplicationConfigurationKeyModel {
	
	/**
	 * V1サーバ接続先<br>
	 * 移行元DBサーバのアドレス<br>
	 */
	public static final String V1_SERVER_ADDRESS = "v1ServerAddress";
	
	/**
	 * V1DB名<br>
	 * 移行元DB名<br>
	 */
	public static final String V1_DATABASE_NAME = "v1DBName";
	
	/**
	 * V1DBユーザ名<br>
	 * 移行元ユーザ名<br>
	 */
	public static final String V1_USER_NAME = "v1UserName";
	
	/**
	 * V1DBパスワード<br>
	 * 移行元パスワード<br>
	 */
	public static final String V1_PASSWORD = "v1Password";
	
	/**
	 * V1Cassandraサーバ接続先<br>
	 * 移行元サーバのアドレスポート番号<br>
	 */
	public static final String V1_CASSANDRA_SERVER_ADDRESS = "v1CassServerAddress";
	
	/**
	 * V1DBKeySpace<br>
	 * 移行元CassandraのKeyspace<br>
	 */
	public static final String V1_DATABASE_KEY_SPACE = "v1DBKeyspace";
	
	/**
	 * V2MariaDbサーバ接続先<br>
	 * 移行先サーバのアドレスポート番号<br>
	 */
	public static final String V2_SERVER_ADDRESS = "v2ServerAddress";
	
	/**
	 * V2DB名<br>
	 * 移行元DB名<br>
	 */
	public static final String V2_DATABASE_NAME = "v2DBName";
	
	/**
	 * V2DBユーザ名<br>
	 * 移行元ユーザ名<br>
	 */
	public static final String V2_USER_NAME = "v2UserName";
	
	/**
	 * V2DBパスワード<br>
	 * 移行元パスワード<br>
	 */
	public static final String V2_PASSWORD = "v2Password";
	
	/**
	 * V2Cassandraサーバ接続先<br>
	 * 移行先サーバのアドレスポート番号<br>
	 * 初期値：localhost:9042
	 */
	public static final String V2_CASSANDRA_SERVER_ADDRESS = "v2CassServerAddress";
	
	/**
	 * V2DBKeySpace<br>
	 * 移行先CassandraのKeyspace<br>
	 */
	public static final String V2_DATABASE_KEY_SPACE = "v2DBKeyspace";

	/**
	 * ログ出力先<br>
	 * エラーログ出力フォルダ<br>
	 */
	public static final String LOG_PATH = "LogPath";
	
	/**
	 * 古いデータの保存期間<br>
	 * 連動順と連動データの古いものを削除(d,h,m,s)<br>
	 */
	public static final String OLD_DATA_PRESERVATION_PERIOD = "oldDataDel";
	
	/**
	 * リトライ回数<br>
	 * 回<br>
	 */
	public static final String RETRY_COUNT = "retryCount";
	
	/**
	 * リトライのインターバル<br>
	 * 秒<br>
	 */
	public static final String RETRY_INTERVAL = "retryInterval";
	
	/**
	 * 連動順データ取得件数<br>
	 * 件<br>
	 */
	public static final String SELECT_LIMIT = "selLimit";
	
	/**
	 * visual_inspection対象<br>
	 * 配列<br>
	 */
	public static final String VISUAL_INSPECTION_IN_STRING = "viInStr";
	
	/**
	 * work_status対象<br>
	 * 配列<br>
	 */
	public static final String WORK_STATUS_IN_STRING = "wsInStr";
	
	/**
	 * ログID及び信号IDを取得する際、使用するシグナル情報<br>
	 * リスト<br>
	 */
	public static final String IDM_MASTER = "idmMaster";
	
	/**
	 * シグナル情報を区別する為のID<br>
	 * 整数<br>
	 */
	public static final String ID = "id";
	
	/**
	 * シグナル情報<br>
	 */
	public static final String SIGNAL_INFORM = "signalInform";
	
	/**
	 * シグナル情報の説明<br>
	 * 文字列<br>
	 */
	public static final String COMMENT = "comment";
	
	/**
	 * シグナルクラス<br>
	 * 文字列<br>
	 */
	public static final String MAJOR = "major";
	
	/**
	 * シグナルクラス<br>
	 * 文字列<br>
	 */
	public static final String MEDIUM = "medium";
}
