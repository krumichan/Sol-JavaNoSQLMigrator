package jp.co.cec.vfInputDatMigrator.db.service;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.mapper.MstSignalAcquisitionMapper;
import jp.co.cec.vfInputDatMigrator.db.model.MstSignalAcquisition;
import jp.co.cec.vfInputDatMigrator.template.AbstractRDBService;

/**
 * mst_signal_acquisitionに接近するサービス
 */
public class MstSignalAcquisitionService extends AbstractRDBService {

	/**
	 * SqlFactoryとSqlSessionを生成
	 * @param prop 接続先の設定値
	 * @throws Exception　生成失敗
	 */
	public MstSignalAcquisitionService(MariaDbConnector.MarKey key) throws Exception {
		super(key);
	}

	/**
	 * mst_signal_acquisitionから特定なデータを取得
	 * @param signalClass　信号クラス
	 * @param signalCategory　信号カテゴリ
	 * @param signalName　信号名
	 * @return　特定なデータ
	 */
	public MstSignalAcquisition lineMstSignalAcquisition(
			String signalClass
			,String signalCategory
			,String signalName
			,String connectorInstance
			,String subjectInstance
			,String valueInstance) {
		MstSignalAcquisition result = null;
		MstSignalAcquisitionMapper mapper = session.getMapper(MstSignalAcquisitionMapper.class);
		
		result = mapper.lineMstSignalAcquisition(
				signalClass
				,signalCategory
				,signalName
				,connectorInstance
				,subjectInstance
				,valueInstance);
		
		return result;
	}
}
