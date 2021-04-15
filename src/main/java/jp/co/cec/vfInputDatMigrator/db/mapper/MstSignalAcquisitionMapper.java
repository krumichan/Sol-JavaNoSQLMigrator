package jp.co.cec.vfInputDatMigrator.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.cec.vfInputDatMigrator.db.model.MstSignalAcquisition;

/**
 * mariaDBのmst_signal_acquisitionに接近するマッパ 
 */
@Mapper
public interface MstSignalAcquisitionMapper {
	
	/**
	 * mst_signal_acquisitionから特定なデータを取得
	 * @param signalClass　信号クラス
	 * @param signalCategory　信号カテゴリ
	 * @param signalName　信号名
	 * @param connectorInstacne　コネクターインスタンス
	 * @param subjectInstance　インスタンス　(主体)
	 * @param valueInstance　インスタンス　(値)
	 * @return　特定なデータ
	 */
	public MstSignalAcquisition lineMstSignalAcquisition(
			@Param("signalClass") String signalClass
			,@Param("signalCategory") String signalCategory
			,@Param("signalName") String signalName
			,@Param("connectorInstance") String connectorInstance
			,@Param("subjectInstance") String subjectInstance
			,@Param("valueInstance") String valueInstance);
}
