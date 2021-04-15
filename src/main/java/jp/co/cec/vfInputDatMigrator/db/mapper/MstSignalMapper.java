package jp.co.cec.vfInputDatMigrator.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.cec.vfInputDatMigrator.db.model.MstSignal;

/**
 * mariaDBのmst_signalに接近するマッパ 
 */
@Mapper
public interface MstSignalMapper {
	
	/**
	 * mst_signalから特定なデータを取得
	 * @param signalClass　信号クラス
	 * @param signalCategory　信号カテゴリ
	 * @param signalName　信号名
	 * @return　特定なデータ
	 */
	public MstSignal lineMstSignal(
			@Param("signalClass") String signalClass
			,@Param("signalCategory") String signalCategory
			,@Param("signalName") String signalName);
}
