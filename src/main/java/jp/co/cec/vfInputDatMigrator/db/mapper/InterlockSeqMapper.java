package jp.co.cec.vfInputDatMigrator.db.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.cec.vfInputDatMigrator.db.model.InterlockSeq;

/**
 * mariaDBのinterlock_seqに接近するマッパ 
 */
@Mapper
public interface InterlockSeqMapper {
	
	/**
	 * interlock_seqからselLimitだけのデータを取得
	 * @param selLimit 取得制限
	 * @return 取得データリスト
	 */
	List<InterlockSeq> queryInterlockSeqs(
			@Param("selLimit") int selLimit);
	
	/**
	 * interlock_seqから有効期間を超えたデータを削除
	 * @param validityEndOn　有効期間
	 * @return 意味無し
	 */
	int deleteInterlockSeqUsingValidityEndOn(
			@Param("validityEndOn") LocalDateTime validityEndOn);
	
	/**
	 * interlock_seqから主キーを使ってデータを削除
	 * @param execDateTime　実行データ日時(主キー)
	 * @param execTable 実行テーブル(主キー)
	 * @return 意味無し
	 */
	int deleteInterlockSeqUsingPrimaryKey(
			@Param("execDateTime") LocalDateTime execDateTime
			,@Param("execTable") String execTable);
	
	/**
	 * interlock_seqからデータの残り数を取得
	 * @return 残り数
	 */
	int checkRemnantInterlockSeq();
}
