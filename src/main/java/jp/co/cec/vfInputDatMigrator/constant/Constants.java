package jp.co.cec.vfInputDatMigrator.constant;

import java.util.Arrays;
import java.util.List;

import jp.co.cec.vfInputDatMigrator.util.DialogUtil;

/**
 * 定数を置くクラス。 
 */
public class Constants {
	
	/**
	 * アプリケーション名
	 */
	public static final String APPLICATION_NAME = "VfInputDatMigrator";
	
	/**
	 * Dialog Utility {@link DialogUtil}　でダイアログ表示もするかどうか<br>
	 * false ならばコンソール出力のみ。
	 */
	public static final boolean IS_SHOW_MESSAGE_DIALOG = false;
	
    /**
     * millisecond .xxx
     * 例：2019-12-19 19:18:22.123
     */
    public static final String LOCAL_DATE_TIME_FORMAT_UNDER3 = "yyyy-MM-dd HH:mm:ss.SSS";
    
    /**
     * millisecond .xxx
     * 例：2019/12/19 19:18:22.123
     */
    public static final String LOCAL_DATE_TIME_FORMAT_SLASH_AND_UNDER3 = "yyyy/MM/dd HH:mm:ss.SSS";
    
    /**
     * 変換データを入れるV2Cassandraテーブル
     */
    public static final String V2_CASSANDRA_TABLE_NAME = "trn_event";
    
    /**
     * V1 Cassandra テーブル名
     */
    public static final String V1_CASSANDRA_TABLE_NAME = "trn_event_value";
    
    /**
     * V1 Cassandraから強制戻しを判断する時、絞込に使用するフィルター
     */
    public static final List<String> FILTER_ARRAY = Arrays.asList(new String[] {
    		"INSPECTION"
    		,"DISCARD"
    		,"OP_CHECK"
    		,"NO_RECLOSING"
    		,"STAY"
    		,"CHK_OK"
    		,"CHK_NG"
    		,"EJECTION"
    		,"GET_BACK"
    });
    
    /**
     * 強制戻しを求める際、cql文に使用する名称
     */
    public static final String ALIAS = "date_time";
    
    /**
     * 設定ファイルから取得したidmMasterの中で強制戻しの信号情報を取得する為のマップキー
     */
    public static final Integer IDM_ID_GET_BACK = 1;

    /**
     * 設定ファイルから取得したidmMasterの中で外観検査以外の信号情報を取得する為のマップキー
     */
    public static final Integer IDM_ID_OTHER = 2;
    
    /**
     *  設定ファイルから取得したidmMasterの中で外観検査NGの信号情報を取得する為のマップキー
     */
    public static final Integer IDM_ID_NG = 3;
    
    /**
     * 設定ファイルから取得したidmMasterの中で外観検査OKの信号情報を取得する為のマップキー
     */
    public static final Integer IDM_ID_OK = 4;
    
    /**
     * ロガー名デフォルト
     */
    public static final String DEFAULT_LOGGER_NAME = "myLogger";
}
