package jp.co.cec.vfInputDatMigrator.db.service;

import java.time.LocalDateTime;
import java.util.List;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;
import jp.co.cec.vfInputDatMigrator.db.mapper.InterlockSeqMapper;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockSeq;
import jp.co.cec.vfInputDatMigrator.template.AbstractRDBService;

/**
 * interlock_seqに接近するサービス
 */
public class InterlockSeqService extends AbstractRDBService {
	
	/**
	 * SqlFactoryとSqlSessionを生成
	 * @param prop 接続先の設定値
	 * @throws Exception　生成失敗
	 */
	public InterlockSeqService(MariaDbConnector.MarKey key) throws Exception {
		super(key);
	}
	
	/**
	 * interlock_seqからselLimitだけのデータを取得
	 * @param selLimit 取得制限
	 * @return 取得データリスト
	 */
	public List<InterlockSeq> queryInterlockSeqs(int selLimit) {
		List<InterlockSeq> result;
		InterlockSeqMapper mapper = session.getMapper(InterlockSeqMapper.class);
		
		//　全ての連動順データ情報を取得
		result = mapper.queryInterlockSeqs(selLimit);
		
		return result;
	}
	
	/**
	 * interlock_seqから有効期間を超えたデータを削除
	 * @param validityEndOn　有効期間
	 * @return 意味無し
	 */
	public int deleteInterlockSeqUsingValidityEndOn(LocalDateTime validityEndOn) {
		int result = -1;
		InterlockSeqMapper mapper = session.getMapper(InterlockSeqMapper.class);
		
		result = mapper.deleteInterlockSeqUsingValidityEndOn(validityEndOn);
		
		return result;
	}
	
	/**
	 * interlock_seqから主キーを使ってデータを削除
	 * @param execDateTime　実行データ日時(主キー)
	 * @param execTable 実行テーブル(主キー)
	 * @return 意味無し
	 */
	public int deleteInterlockSeqUsingPrimaryKey(LocalDateTime execDateTime, String execTable) {
		int result = -1;
		InterlockSeqMapper mapper = session.getMapper(InterlockSeqMapper.class);
		
		result = mapper.deleteInterlockSeqUsingPrimaryKey(execDateTime, execTable);
		
		return result;
	}
	
	/**
	 * interlock_seqからデータの残り数を取得
	 * @return 残り数
	 */
	public int checkRemnantInterlockSeq() {
		int result = -1;
		InterlockSeqMapper mapper = session.getMapper(InterlockSeqMapper.class);
		
		result = mapper.checkRemnantInterlockSeq();
		
		return result;
	}
}
