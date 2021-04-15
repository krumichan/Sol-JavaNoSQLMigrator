package jp.co.cec.vfInputDatMigrator.main;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfInputDatMigrator.model.ApplicationConfigurationModel;
import jp.co.cec.vfInputDatMigrator.model.ApplicationConfigurationModel.IdmMaster;
import jp.co.cec.vfInputDatMigrator.model.ApplicationConfigurationModel.SignalInform;
import jp.co.cec.vfInputDatMigrator.util.JsonUtil;

public class VfInputDatMigratorInitialization {

	/**
	 * エラーメッセージ：configulationがnullです
	 */
	public static final String ERROR_STATE_CONFIGULATION_NULL = "configulationがnullです";

	/**
	 * エラーメッセージ：configulationの取得に失敗しました
	 */
	public static final String ERROR_STATE_CONFIGULATION_TYPE_ERROR = "configulationの取得に失敗しました";
	
	/**
	 * エラーメッセージ：v1ServerAddressがnull又は空です
	 */
	public static final String ERROR_STATE_V1_SERVER_ADDRESS_NULLOREMPTY = "v1ServerAddressがnull又は空です";
	
	/**
	 * エラーメッセージ：v1DBNameがnull又は空です
	 */
	public static final String ERROR_STATE_V1_DATABASE_NAME_NULLOREMPTY = "v1DBNameがnull又は空です";
	
	/**
	 * エラーメッセージ：v1UserNameがnull又は空です
	 */
	public static final String ERROR_STATE_V1_USER_NAME_NULLOREMPTY = "v1UserNameがnull又は空です";
	
	/**
	 * エラーメッセージ：V1Passwordがnull又は空です
	 */
	public static final String ERROR_STATE_V1_PASSWORD_NULLOREMPTY = "V1Passwordがnull又は空です";
	
	/**
	 * エラーメッセージ：v1CassServerAddressがnull又は空です
	 */
	public static final String ERROR_STATE_V1_CASSANDRA_SERVER_ADDRESS_NULLOREMPTY = "v1CassServerAddressがnull又は空です";
	
	/**
	 * エラーメッセージ：v1DBKeyspaceがnull又は空です
	 */
	public static final String ERROR_STATE_V1_CASSANDRA_KEYSPACE_NULLOREMPTY = "v1DBKeyspaceがnull又は空です";
	
	/**
	 * エラーメッセージ：v2ServerAddressがnull又は空です
	 */
	public static final String ERROR_STATE_V2_SERVER_ADDRESS_NULLOREMPTY = "v2ServerAddressがnull又は空です";
	
	/**
	 * エラーメッセージ：v2DBNameがnull又は空です
	 */
	public static final String ERROR_STATE_V2_DATABASE_NAME_NULLOREMPTY = "v2DBNameがnull又は空です";
	
	/**
	 * エラーメッセージ：v2UserNameがnull又は空です
	 */
	public static final String ERROR_STATE_V2_USER_NAME_NULLOREMPTY = "v2UserNameがnull又は空です";
	
	/**
	 * エラーメッセージ：V2Passwordがnull又は空です
	 */
	public static final String ERROR_STATE_V2_PASSWORD_NULLOREMPTY = "V2Passwordがnull又は空です";
	
	/**
	 * エラーメッセージ：v2CassServerAddressがnull又は空です
	 */
	public static final String ERROR_STATE_V2_CASSANDRA_SERVER_ADDRESS_NULLOREMPTY = "v2CassServerAddressがnull又は空です";
	
	/**
	 * エラーメッセージ：v2DBKeyspaceがnull又は空です
	 */
	public static final String ERROR_STATE_V2_CASSANDRA_KEYSPACE_NULLOREMPTY = "v2DBKeyspaceがnull又は空です";
	
	/**
	 * エラーメッセージ：logPathがnull又は空です
	 */
	public static final String ERROR_STATE_LOG_PATH_NULLOREMPTY = "logPathがnull又は空です";
	
	/**
	 * エラーメッセージ：oldDataDelが不正値です。\n形式は［数字］［文字］です。（例>30d）\n可能なフォーマットは d, h, m, sです。
	 */
	public static final String ERROR_STATE_OLD_DATA_DEL_ILLEGAL = "oldDataDelが不正値です。\n形式は［数字］［文字］です。（例>30d）\n可能なフォーマットは d, h, m, sです。";
	
	/**
	 * エラーメッセージ：retryCountが整数又はゼロ超過でなく、不正値です。
	 */
	public static final String ERROR_STATE_RETRY_COUNT_ILLEGAL = "retryCountが整数又はゼロ以上でなく、不正値です。";
	
	/**
	 * エラーメッセージ：retryIntervalが整数又はゼロ超過でなく、不正値です。
	 */
	public static final String ERROR_STATE_RETRY_INTERVAL_ILLEGAL = "retryIntervalが整数又はゼロ以上でなく、不正値です。";
	
	/**
	 * エラーメッセージ：selLimitが整数又はゼロ超過でなく、不正値です。
	 */
	public static final String ERROR_STATE_SEL_LIMIT_ILLEGAL = "selLimitが整数又はゼロ超過でなく、不正値です。";
	
	/**
	 * エラーメッセージ：viInStrがnullです
	 */
	public static final String ERROR_STATE_VISUAL_INSPECTION_STRING_NULLOREMPTY = "viInStrがnull又は空です";
	
	/**
	 * エラーメッセージ：viInStrがnullです
	 */
	public static final String ERROR_STATE_VISUAL_INSPECTION_STRING_ELEMENT_NULLOREMPTY = "viInStrの要素がnull又は空です";
	
	/**
	 * エラーメッセージ：wsInStrがnullです
	 */
	public static final String ERROR_STATE_WORK_STATUS_STRING_NULLOREMPTY = "wsInStrがnull又は空です";
	
	/**
	 * エラーメッセージ：wsInStrがnullです
	 */
	public static final String ERROR_STATE_WORK_STATUS_STRING_ELEMENT_NULLOREMPTY = "wsInStrの要素がnull又は空です";
	
	/**
	 * エラーメッセージ：idmMasterがnull又は空です
	 */
	public static final String ERROR_STATE_IDM_MASTER_NULLOREMPTY = "idmMasterがnull又は空です";
	
	/**
	 * エラーメッセージ：idがnullです
	 */
	public static final String ERROR_STATE_ID_NULL = "idがnullです";
	
	/**
	 * エラーメッセージ：signalInformがnullです
	 */
	public static final String ERROR_STATE_SIGNAL_INFROM_NULL = "signalInformがnullです";
	
	/**
	 * エラーメッセージ：majorがnullです
	 */
	public static final String ERROR_STATE_MAJOR_NULLOREMPTY = "majorがnullです";
	
	/**
	 * エラーメッセージ：mediumがnullです
	 */
	public static final String ERROR_STATE_MEDIUM_NULLOREMPTY = "mediumがnullです";
	
	/**
	 * FunctionのConfigurationを取得<br>
	 * 単体テストを可能にするためpublicとする
	 * @param configNode JSONのconfigurationノード
	 * @return Configuration
	 * @throws Exception(Configurationが異常で救いようがない)
	 */
	public ApplicationConfigurationModel convertConfiguration(JsonNode configNode) throws Exception {
		if (configNode == null) {
			throw new Exception(ERROR_STATE_CONFIGULATION_NULL);
		}
		
		JsonUtil mapper = new JsonUtil();
		ApplicationConfigurationModel config = null;
		try {
			config = mapper.convert(configNode, ApplicationConfigurationModel.class);
		} catch (Exception e) {
		}
		if (config == null) {
			throw new Exception(ERROR_STATE_CONFIGULATION_TYPE_ERROR);
		}
		
		if (isNullOrEmpty(config.v1ServerAddress)) {
			throw new Exception(ERROR_STATE_V1_SERVER_ADDRESS_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v1DBName)) {
			throw new Exception(ERROR_STATE_V1_DATABASE_NAME_NULLOREMPTY);
		}

		if (isNullOrEmpty(config.v1UserName)) {
			throw new Exception(ERROR_STATE_V1_USER_NAME_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v1Password)) {
			throw new Exception(ERROR_STATE_V1_PASSWORD_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v1CassServerAddress)) {
			throw new Exception(ERROR_STATE_V1_CASSANDRA_SERVER_ADDRESS_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v1DBKeyspace)) {
			throw new Exception(ERROR_STATE_V1_CASSANDRA_KEYSPACE_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v2ServerAddress)) {
			throw new Exception(ERROR_STATE_V2_SERVER_ADDRESS_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v2DBName)) {
			throw new Exception(ERROR_STATE_V2_DATABASE_NAME_NULLOREMPTY);
		}

		if (isNullOrEmpty(config.v2UserName)) {
			throw new Exception(ERROR_STATE_V2_USER_NAME_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v2Password)) {
			throw new Exception(ERROR_STATE_V2_PASSWORD_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v2CassServerAddress)) {
			throw new Exception(ERROR_STATE_V2_CASSANDRA_SERVER_ADDRESS_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v2DBKeyspace)) {
			throw new Exception(ERROR_STATE_V2_CASSANDRA_KEYSPACE_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.LogPath)) {
			throw new Exception(ERROR_STATE_LOG_PATH_NULLOREMPTY);
		}
		
		try {
			checkIntegerIncludeZero(config.retryCount);
		} catch (Exception e) {
			throw new Exception(ERROR_STATE_RETRY_COUNT_ILLEGAL);
		}
		
		try {
			checkIntegerIncludeZero(config.retryInterval);
		} catch (Exception e) {
			throw new Exception(ERROR_STATE_RETRY_INTERVAL_ILLEGAL);
		}
		
		try {
			checkInteger(config.selLimit);
		} catch (Exception e) {
			throw new Exception(ERROR_STATE_SEL_LIMIT_ILLEGAL);
		}
		
		try {
			String oldDataDel = config.oldDataDel;
			checkInteger(oldDataDel.substring(0, oldDataDel.length() - 1));
			String form = oldDataDel.substring(oldDataDel.length() - 1);
			if (!form.equals("d") && !form.equals("h") && !form.equals("m") && !form.equals("s")) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new Exception(ERROR_STATE_OLD_DATA_DEL_ILLEGAL);
		}
		
		if (config.viInStr == null || config.viInStr.length < 1) {
			throw new Exception (ERROR_STATE_VISUAL_INSPECTION_STRING_NULLOREMPTY);
		}
		for (int index = 0; index < config.viInStr.length; index++) {
			String str = config.viInStr[index];
			if (isNullOrEmpty(str)) {
				throw new Exception(ERROR_STATE_VISUAL_INSPECTION_STRING_ELEMENT_NULLOREMPTY);
			}
		}
		
		if (config.wsInStr == null || config.wsInStr.length < 1) {
			throw new Exception (ERROR_STATE_WORK_STATUS_STRING_NULLOREMPTY);
		}
		for (int index = 0; index < config.wsInStr.length; index++) {
			String str = config.wsInStr[index];
			if (isNullOrEmpty(str)) {
				throw new Exception(ERROR_STATE_WORK_STATUS_STRING_ELEMENT_NULLOREMPTY);
			}
		}
		
		if (isNullOrEmpty(config.idmMaster)) {
			throw new Exception(ERROR_STATE_IDM_MASTER_NULLOREMPTY);
		}
		
		for (int idmIndex = 0; idmIndex < config.idmMaster.size(); idmIndex++) {
			IdmMaster idmMaster = config.idmMaster.get(idmIndex);
			
			Integer id = idmMaster.id;
			if (Objects.isNull(id)) {
				throw new Exception("idmIndex[" + idmIndex + "]/" + ERROR_STATE_ID_NULL);
			}
			
			SignalInform signalInform = idmMaster.signalInform;
			if (Objects.isNull(signalInform)) {
				throw new Exception("idmIndex[" + idmIndex + "]/" + ERROR_STATE_SIGNAL_INFROM_NULL);
			}
			
			String major = signalInform.major;
			if (Objects.isNull(major)) {
				throw new Exception("idmIndex[" + idmIndex + "]/" + "signalInform" + "/" + ERROR_STATE_MAJOR_NULLOREMPTY);
			}
			
			String medium = signalInform.medium;
			if (Objects.isNull(medium)) {
				throw new Exception("idmIndex[" + idmIndex + "]/" + "signalInform" + "/" + ERROR_STATE_MEDIUM_NULLOREMPTY);
			}
			
			config.idmMap.put(id, signalInform);
		}
		
		return config;
	}
	
	/**
	 * null又は空かを確認
	 * @param tar 確認対象
	 * @return 確認結果
	 */
	private boolean isNullOrEmpty(String tar) {
		return Objects.isNull(tar) || tar.isEmpty();
	}
	
	/**
	 * null又は空かを確認
	 * @param tar 確認対象
	 * @return 確認結果
	 */
	private boolean isNullOrEmpty(List<?> tar) {
		return Objects.isNull(tar) || tar.size() == 0;
	}
	
	/**
	 * 0超過のString値をintに変換
	 * @param str 値
	 * @return　intに変換した結果
	 * @throws Exception 0以下又は変換失敗
	 */
	private int checkInteger(String str) throws Exception {
		int temp = Integer.parseInt(str);
		if (temp <= 0) {
			throw new Exception();
		}
		return temp;
	}
	
	/**
	 * 0以上のString値をintに変換
	 * @param str 値
	 * @return　intに変換した結果
	 * @throws Exception 0未満又は変換失敗
	 */
	private int checkIntegerIncludeZero(String str) throws Exception {
		int temp = Integer.parseInt(str);
		if (temp < 0) {
			throw new Exception();
		}
		return temp;
	}
}
