package jp.co.cec.vfInputDatMigrator.db.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.cec.vfInputDatMigrator.db.model.InterlockWorkStatus;

/**
 * mariaDBのinterlock_work_statusに接近するマッパ 
 */
@Mapper
public interface InterlockWorkStatusMapper {
	
	/**
	 * interlock_work_statusから特定なデータを取得
	 * @param 実行された時間
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param entryDateTime エントリーデート
	 * @param workStatusKey ワークステータスキー
	 * @return 特定なデータ
	 */
	List<InterlockWorkStatus> linesInterlockWorkStatus(
			@Param("execDateTime") LocalDateTime execDateTime
			,@Param("placeId") String placeId
			,@Param("serialNumber") String serialNumber
			,@Param("entryDateTime") LocalDateTime entryDateTime
			,@Param("workStatusKey") String workStatusKey);
	
	/**
	 * interlock_work_statusから特定なデータを取得
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param entryDateTime エントリーデート
	 * @param workStatusKey ワークステータスキー
	 * @return 特定なデータ
	 */
	List<InterlockWorkStatus> checkInterlockWorkStatus(
			@Param("placeId") String placeId
			,@Param("serialNumber") String serialNumber
			,@Param("entryDateTime") LocalDateTime entryDateTime
			,@Param("workStatusKey") String workStatusKey);
	
	/**
	 * interlock_visual_inspectionから有効期間を超えたデータを削除
	 * @param validityEndOn　有効期間
	 * @return　意味無し
	 */
	int deleteInterlockWorkStatusUsingValidityEndOn(
			@Param("validityEndOn") LocalDateTime validityEndOn);
	
	/**
	 * interlock_visual_inspectionから主キーを使ってデータを削除
	 * @param execDateTime　実行日時
	 * @param execDataFlag　データフラグ
	 * @param placeId　場所ID
	 * @param serialNumber シリアルナンバー
	 * @param workStatusKey ワークステータスキー
	 * @return　意味無し
	 */
	int deleteInterlockWorkStatusUsingPrimaryKey(
			@Param("execDateTime") LocalDateTime execDateTime
			,@Param("execDataFlag") byte execDataFlag
			,@Param("placeId") String placeId
			,@Param("serialNumber") String serialNumber
			,@Param("entryDateTime") LocalDateTime entryDateTime
			,@Param("workStatusKey") String workStatusKey);
}
