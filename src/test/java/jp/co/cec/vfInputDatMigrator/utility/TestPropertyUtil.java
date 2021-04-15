package jp.co.cec.vfInputDatMigrator.utility;

import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import jp.co.cec.vfInputDatMigrator.util.FormatUtil;

public class TestPropertyUtil {
	
	private static JsonNode property;
	private static Properties v1Prop;
	private static Properties v2Prop;
	
	/**
	 * V1方のMariaDb情報を取得
	 */
	public static Properties v1Prop() {
		return v1Prop;
	}

	/**
	 * V1方のMariaDb情報をセット
	 */
	public static void setV1Prop(Properties v1Prop) {
		TestPropertyUtil.v1Prop = v1Prop;
	}

	/**
	 * V2方のMariaDb情報を取得
	 */
	public static Properties v2Prop() {
		return v2Prop;
	}

	/**
	 * V2方のMariaDb情報をセット
	 */
	public static void setV2Prop(Properties v2Prop) {
		TestPropertyUtil.v2Prop = v2Prop;
	}

	/**
	 * 設定ファイルデータセット
	 */
	public static void setProperty(JsonNode node) {
		property = node;
	}
	
	/**
	 * JsonNode形式の設定ファイルデータを取得
	 */
	public JsonNode getProperty() {
		return property;
	}
	
	/**
	 * 引数のnodeからkeyに該当するデータを取得
	 * @param node データ取得先
	 * @param key データキー
	 * @return 取得データ
	 */
	public static String getValue(JsonNode node, String key) {
		JsonNode target = node.get(key);
		if (target != null) {
			return FormatUtil.trimStr(target.asText());
		}
		return null;
	}
	
	public static String[] getArrayValue(JsonNode node, String key) throws Exception {
		if (property == null) {
			return null;
		} else {
			ObjectReader reader = new ObjectMapper().reader(new TypeReference<String[]>() {});
			return reader.readValue(node.get(key));			
		}
	}
	
	/**
	 * Property Utilityに登録されているnodeからKeyに該当するデータを取得<br>
	 * (Property UtilityのsetPropertyメソッドを使って登録)
	 * @param key データキー
	 * @return 取得データ
	 */
	public static String getValue(String key) {
		if (property == null) {
			return null;
		} else {
			return getValue(property, key);
		}
	}
	
	public static String[] getArrayValue(String key) throws Exception {
		if (property == null) {
			return null;
		} else {
			ObjectReader reader = new ObjectMapper().reader(new TypeReference<List<String>>() {});
			return reader.readValue(property.get(key));			
		}
	}
}
