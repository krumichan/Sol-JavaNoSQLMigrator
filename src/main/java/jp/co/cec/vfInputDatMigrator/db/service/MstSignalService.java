package jp.co.cec.vfInputDatMigrator.db.service;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.mapper.MstSignalMapper;
import jp.co.cec.vfInputDatMigrator.db.model.MstSignal;
import jp.co.cec.vfInputDatMigrator.template.AbstractRDBService;

/**
 * mst_signalに接近するサービス
 */
public class MstSignalService extends AbstractRDBService {

	/**
	 * SqlFactoryとSqlSessionを生成
	 * @param prop 接続先の設定値
	 * @throws Exception　生成失敗
	 */
	public MstSignalService(MariaDbConnector.MarKey key) throws Exception {
		super(key);
	}

	/**
	 * mst_signalから特定なデータを取得
	 * @param signalClass　信号クラス
	 * @param signalCategory　信号カテゴリ
	 * @param signalName　信号名
	 * @return　特定なデータ
	 */
	public MstSignal lineMstSignal(String signalClass, String signalCategory, String signalName) {
		MstSignal result = null;
		MstSignalMapper mapper = session.getMapper(MstSignalMapper.class);
		
		result = mapper.lineMstSignal(signalClass, signalCategory, signalName);
		
		return result;
	}
}
