package jp.co.cec.vfInputDatMigrator.template;

import jp.co.cec.vfInputDatMigrator.main.VfInputDatMigratorExecutor;
import jp.co.cec.vfInputDatMigrator.model.ApplicationConfigurationModel;

/**
 * 処理共通クラス 
 */
public abstract class AbstractFunction {

	/**
	 * 設定データ
	 */
	protected ApplicationConfigurationModel config = null;
	
	/**
	 * コンストラクタ
	 */
	public AbstractFunction() {
		config = VfInputDatMigratorExecutor.config;
	}
	/**
	 * 処理ファンしょん
	 * @param <T> 任意の戻り値
	 * @param params　任意の引数
	 * @return　任意の結果
	 * @throws Exception　処理失敗
	 */
	public abstract <T> T execute(Object... params) throws Exception;
}
