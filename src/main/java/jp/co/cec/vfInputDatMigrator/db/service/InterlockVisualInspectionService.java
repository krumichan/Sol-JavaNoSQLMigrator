package jp.co.cec.vfInputDatMigrator.db.service;

import java.time.LocalDateTime;
import java.util.List;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.mapper.InterlockVisualInspectionMapper;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockVisualInspection;
import jp.co.cec.vfInputDatMigrator.template.AbstractRDBService;
import jp.co.cec.vfInputDatMigrator.util.FormatUtil;

/**
 * interlock_visual_inspectionに接近するサービス
 */
public class InterlockVisualInspectionService extends AbstractRDBService {

	/**
	 * SqlFactoryとSqlSessionを生成
	 * @param prop 接続先の設定値
	 * @throws Exception　生成失敗
	 */
	public InterlockVisualInspectionService(MariaDbConnector.MarKey key) throws Exception {
		super(key);
	}
	
	/**
	 * interlock_visual_inspectionから特定なデータを取得
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param entryDateTime エントリーデート
	 * @param judgmentKey ジャッジメントキー
	 * @return 特定なデータ
	 */
	public List<InterlockVisualInspection> linesInterlockVisualInspection(
			String placeId
			,String serialNumber
			,String entryDateTime
			,String judgmentKey) {
		List<InterlockVisualInspection> result = null;
		InterlockVisualInspectionMapper mapper = session.getMapper(InterlockVisualInspectionMapper.class);
		
		LocalDateTime ldtEntryDateTime = FormatUtil.formatStringToDateTimeUnder3(entryDateTime);
		result = mapper.linesInterlockVisualInspection(placeId, serialNumber, ldtEntryDateTime, judgmentKey);
		
		return result;
	}

	/**
	 * interlock_visual_inspectionから特定なデータを取得
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param entryDateTime エントリーデート
	 * @return　特定なデータ
	 */
	public List<InterlockVisualInspection> linesInterlockVisualInspectionNonJudgmentKey(
			String placeId
			,String serialNumber
			,String entryDateTime) {
		List<InterlockVisualInspection> result = null;
		InterlockVisualInspectionMapper mapper = session.getMapper(InterlockVisualInspectionMapper.class);
		
		LocalDateTime ldtEntryDateTime = FormatUtil.formatStringToDateTimeUnder3(entryDateTime);
		result = mapper.linesInterlockVisualInspectionNonJudgmentKey(placeId, serialNumber, ldtEntryDateTime);
		
		return result;
	}

	/**
	 * interlock_visual_inspectionから有効期間を超えたデータを削除
	 * @param validityEndOn　有効期間
	 * @return　意味無し
	 */
	public int deleteInterlockVisualInspectionUsingValidityEndOn(
			LocalDateTime validityEndOn) {
		int result = -1;
		InterlockVisualInspectionMapper mapper = session.getMapper(InterlockVisualInspectionMapper.class);
		
		result = mapper.deleteInterlockVisualInspectionUsingValidityEndOn(validityEndOn);
		
		return result;
	}

	/**
	 * interlock_visual_inspectionから主キーを使ってデータを削除
	 * @param execDateTime　実行日時
	 * @param execDataFlag　データフラグ
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param entryDateTime　エントリーデート
	 * @return　意味無し
	 */
	public int deleteInterlockVisualInspectionUsingPrimaryKey(
			LocalDateTime execDateTime
			,byte execDataFlag
			,String placeId
			,String serialNumber
			,LocalDateTime entryDateTime) {
		int result = -1;
		InterlockVisualInspectionMapper mapper = session.getMapper(InterlockVisualInspectionMapper.class);
		
		result = mapper.deleteInterlockVisualInspectionUsingPrimaryKey(execDateTime, execDataFlag, placeId, serialNumber, entryDateTime);
		
		return result;
	}
}
