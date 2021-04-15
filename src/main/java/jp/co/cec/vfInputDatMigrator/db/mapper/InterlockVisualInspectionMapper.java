package jp.co.cec.vfInputDatMigrator.db.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.cec.vfInputDatMigrator.db.model.InterlockVisualInspection;

/**
 * mariaDBのinterlock_visual_inspectionに接近するマッパ 
 */
@Mapper
public interface InterlockVisualInspectionMapper {
	
	/**
	 * interlock_visual_inspectionから特定なデータを取得
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param entryDateTime エントリーデート
	 * @param judgmentKey ジャッジメントキー
	 * @return 特定なデータ
	 */
	List<InterlockVisualInspection> linesInterlockVisualInspection(
			@Param("placeId") String placeId
			,@Param("serialNumber") String serialNumber
			,@Param("entryDateTime") LocalDateTime entryDateTime
			,@Param("judgmentKey") String judgmentKey);
	
	/**
	 * interlock_visual_inspectionから特定なデータを取得
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param entryDateTime エントリーデート
	 * @return　特定なデータ
	 */
	List<InterlockVisualInspection> linesInterlockVisualInspectionNonJudgmentKey(
			@Param("placeId") String placeId
			,@Param("serialNumber") String serialNumber
			,@Param("entryDateTime") LocalDateTime entryDateTime);
	
	/**
	 * interlock_visual_inspectionから有効期間を超えたデータを削除
	 * @param validityEndOn　有効期間
	 * @return　意味無し
	 */
	int deleteInterlockVisualInspectionUsingValidityEndOn(
			@Param("validityEndOn") LocalDateTime validityEndOn);
	
	/**
	 * interlock_visual_inspectionから主キーを使ってデータを削除
	 * @param execDateTime　実行日時
	 * @param execDataFlag　データフラグ
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param entryDateTime　エントリーデート
	 * @return　意味無し
	 */
	int deleteInterlockVisualInspectionUsingPrimaryKey(
			@Param("execDateTime") LocalDateTime execDateTime
			,@Param("execDataFlag") byte execDataFlag
			,@Param("placeId") String placeId
			,@Param("serialNumber") String serialNumber
			,@Param("entryDateTime") LocalDateTime entryDateTime);
}
