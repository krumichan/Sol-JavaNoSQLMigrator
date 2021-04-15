package jp.co.cec.vfInputDatMigrator.db.service;

import java.time.LocalDateTime;
import java.util.List;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.mapper.InterlockWorkStatusMapper;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockWorkStatus;
import jp.co.cec.vfInputDatMigrator.template.AbstractRDBService;
import jp.co.cec.vfInputDatMigrator.util.FormatUtil;

/**
 * interlock_work_statusに接近するサービス
 */
public class InterlockWorkStatusService extends AbstractRDBService {

	/**
	 * SqlFactoryとSqlSessionを生成
	 * @param prop 接続先の設定値
	 * @throws Exception　生成失敗
	 */
	public InterlockWorkStatusService(MariaDbConnector.MarKey key) throws Exception {
		super(key);
	}

	/**
	 * interlock_work_statusから特定なデータを取得
	 * @param 実行された時間
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param entryDateTime エントリーデート
	 * @param workStatusKey ワークステータスキー
	 * @return 特定なデータ
	 */
	public List<InterlockWorkStatus> linesTrnWorkStatus(
			LocalDateTime execDateTime
			,String placeId
			,String serialNumber
			,String entryDateTime
			,String workStatusKey) {
		List<InterlockWorkStatus> result = null;
		InterlockWorkStatusMapper mapper = session.getMapper(InterlockWorkStatusMapper.class);
		
		LocalDateTime ldtEntryDateTime = FormatUtil.formatStringToDateTimeUnder3(entryDateTime);
		result = mapper.linesInterlockWorkStatus(execDateTime, placeId, serialNumber, ldtEntryDateTime, workStatusKey);
		
		return result;
	}
	
	/**
	 * interlock_work_statusから特定なデータを取得
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param entryDateTime エントリーデート
	 * @param workStatusKey ワークステータスキー
	 * @return 特定なデータ
	 */
	public List<InterlockWorkStatus> checkInterlockWorkStatus (
			String placeId
			,String serialNumber
			,String entryDateTime
			,String workStatusKey) {
		List<InterlockWorkStatus> result = null;
		InterlockWorkStatusMapper mapper = session.getMapper(InterlockWorkStatusMapper.class);
		
		LocalDateTime ldtEntryDateTime = FormatUtil.formatStringToDateTimeUnder3(entryDateTime);
		result = mapper.checkInterlockWorkStatus(placeId, serialNumber, ldtEntryDateTime, workStatusKey);
		
		return result;
	}

	/**
	 * interlock_visual_inspectionから有効期間を超えたデータを削除
	 * @param validityEndOn　有効期間
	 * @return　意味無し
	 */
	public int deleteInterlockWorkStatusUsingValidityEndOn(
			LocalDateTime validityEndOn) {
		int result = -1;
		InterlockWorkStatusMapper mapper = session.getMapper(InterlockWorkStatusMapper.class);
		
		result = mapper.deleteInterlockWorkStatusUsingValidityEndOn(validityEndOn);
		
		return result;
	}

	/**
	 * interlock_visual_inspectionから主キーを使ってデータを削除
	 * @param execDateTime　実行日時
	 * @param execDataFlag　データフラグ
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param workStatusKey ワークステータスキー
	 * @return　意味無し
	 */
	public int deleteInterlockWorkStatusUsingPrimaryKey(
			LocalDateTime execDateTime
			,byte execDataFlag
			,String placeId
			,String serialNumber
			,LocalDateTime entryDateTime
			,String workStatusKey) {
		int result = -1;
		InterlockWorkStatusMapper mapper = session.getMapper(InterlockWorkStatusMapper.class);
		
		result = mapper.deleteInterlockWorkStatusUsingPrimaryKey(execDateTime, execDataFlag, placeId, serialNumber, entryDateTime, workStatusKey);
		
		return result;
	}
}
