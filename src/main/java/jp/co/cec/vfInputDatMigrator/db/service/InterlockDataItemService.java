package jp.co.cec.vfInputDatMigrator.db.service;

import java.time.LocalDateTime;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.mapper.InterlockDataItemMapper;
import jp.co.cec.vfInputDatMigrator.template.AbstractRDBService;

/**
 * interlock_data_itemに接近するサービス
 */
public class InterlockDataItemService extends AbstractRDBService {

	/**
	 * SqlFactoryとSqlSessionを生成
	 * @param prop 接続先の設定値
	 * @throws Exception　生成失敗
	 */
	public InterlockDataItemService(MariaDbConnector.MarKey key) throws Exception {
		super(key);
	}

	/**
	 * interlock_Data_itemから有効期間を超えたデータを削除
	 * @param validityEndOn　有効期間
	 * @return　意味無し
	 */
	public int deleteInterlockDataItemUsingValidityEndOn(LocalDateTime validityEndOn) {
		int result = -1;
		InterlockDataItemMapper mapper = session.getMapper(InterlockDataItemMapper.class);
		
		result = mapper.deleteInterlockDataItemUsingValidityEndOn(validityEndOn);
		
		return result;
	}
}
