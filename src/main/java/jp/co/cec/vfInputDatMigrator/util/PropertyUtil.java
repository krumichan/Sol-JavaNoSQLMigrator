package jp.co.cec.vfInputDatMigrator.util;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Property Utility
 */
public class PropertyUtil {
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
}
