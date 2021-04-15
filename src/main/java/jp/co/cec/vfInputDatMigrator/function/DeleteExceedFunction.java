package jp.co.cec.vfInputDatMigrator.function;

import java.time.LocalDateTime;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockDataItemService;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockSeqService;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockVisualInspectionService;
import jp.co.cec.vfInputDatMigrator.db.service.InterlockWorkStatusService;
import jp.co.cec.vfInputDatMigrator.template.AbstractFunction;
import jp.co.cec.vfInputDatMigrator.util.LoggerUtil;
import jp.co.cec.vfInputDatMigrator.util.TimeUtil;

/**
 * 不要な連動順・連動データ削除するクラス
 */
public class DeleteExceedFunction extends AbstractFunction {

	/**
	 * 不要な連動順・連動データ削除するメソッド
	 * @param params 要らない
	 * @throws Exception 削除失敗(データベース切断等)
	 */
	@Override
	public <T> T execute(Object... params) throws Exception {
		String oldDataDel = config.oldDataDel;
		LocalDateTime validityEndOn = covertDate(oldDataDel);
		LoggerUtil.getLogger().info("[" + validityEndOn + "]を過ぎたデータを削除します。");
		
		//　不要連動順データ削除
		//　削除基準：有効期間
		//　削除対象：interlock_seq
		InterlockSeqService iss = new InterlockSeqService(MariaDbConnector.MarKey.V1);
		iss.deleteInterlockSeqUsingValidityEndOn(
				validityEndOn);
		
		//　不要連動データ削除
		//　削除基準：有効期間
		//　削除対象：interlock_visual_inspection, interlock_work_status
		InterlockVisualInspectionService ivis = new InterlockVisualInspectionService(MariaDbConnector.MarKey.V1);
		InterlockWorkStatusService iwss = new InterlockWorkStatusService(MariaDbConnector.MarKey.V1);
		InterlockDataItemService idis = new InterlockDataItemService(MariaDbConnector.MarKey.V1);
		ivis.deleteInterlockVisualInspectionUsingValidityEndOn(
				validityEndOn);
		iwss.deleteInterlockWorkStatusUsingValidityEndOn(
				validityEndOn);
		idis.deleteInterlockDataItemUsingValidityEndOn(
				validityEndOn);
		
		MariaDbConnector.getSession(MariaDbConnector.MarKey.V1).commit();
		return null;
	}
	
	/**
	 * 今日の日時から設定ファイル上の保管期間を減らして返す
	 * @param oldDataDel 保管期間
	 * @return 変換日時
	 * @throws Exception 変換失敗(不正な保管期間データ)
	 */
	private LocalDateTime covertDate(String oldDataDel) {
		LocalDateTime result = TimeUtil.convertUtcToDefaultTimeZone(LocalDateTime.now());
		String form = oldDataDel.substring(oldDataDel.length() - 1);
		int validity = Integer.parseInt(oldDataDel.substring(0, oldDataDel.length() - 1));
		
		switch (form.toLowerCase()) {
		case "d":
			return result.plusDays(-validity);
		
		case "h":
			return result.plusHours(-validity);
			
		case "m":
			return result.plusMinutes(-validity);
			
		case "s":
			return result.plusSeconds(-validity);
			
		//　必ず発生しない
		default:
			return null;
		}
	}
}
