/**
 * @file	AbstractRDBService.java
 * @brief	データベース処理サービス基底クラス
 */

/**
 * @namespace	vfdbtransition.core.db.service
 * @brief		DBアクセス共通処理群
 */
package jp.co.cec.vfInputDatMigrator.template;

import org.apache.ibatis.session.SqlSession;

import jp.co.cec.vfInputDatMigrator.db.dao.MariaDbConnector;

/**
 * @brief	データベース処理サービス基底クラス
 * @details	RDB取得時の共通処理
 */
public abstract class AbstractRDBService {
	
	/**
	 * データベース接続
	 */
	protected SqlSession session;
	
	/**
	 * MariaDBからセッションを取得
	 * @param key　接続先
	 * @throws Exception　生成失敗
	 */
	public AbstractRDBService(MariaDbConnector.MarKey key) throws Exception {
		session = MariaDbConnector.getSession(key);
	}
}
