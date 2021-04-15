package jp.co.cec.vfInputDatMigrator.main;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfInputDatMigrator.db.model.InterlockSeq;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.function.CheckRemnantFunction;
import jp.co.cec.vfInputDatMigrator.function.ConnectCassandraFunction;
import jp.co.cec.vfInputDatMigrator.function.ConnectMariaDbFunction;
import jp.co.cec.vfInputDatMigrator.function.ConversionFunction;
import jp.co.cec.vfInputDatMigrator.function.DeleteExceedFunction;
import jp.co.cec.vfInputDatMigrator.function.DeleteProccessedFunction;
import jp.co.cec.vfInputDatMigrator.function.DisconnectDatabaseFunction;
import jp.co.cec.vfInputDatMigrator.function.InsertCassandraFunction;
import jp.co.cec.vfInputDatMigrator.function.JudgmentFunction;
import jp.co.cec.vfInputDatMigrator.function.SelectDataFunction;
import jp.co.cec.vfInputDatMigrator.function.SelectSequenceFunction;
import jp.co.cec.vfInputDatMigrator.model.ApplicationConfigurationModel;
import jp.co.cec.vfInputDatMigrator.template.MonoBehaviour;
import jp.co.cec.vfInputDatMigrator.util.TimeUtil;

/**
 * 手入力データ移行を実行するクラス
 */
public class VfInputDatMigratorExecutor extends MonoBehaviour {
	
	/**
	 * ロガー
	 */
	private Logger myLogger;
	
	/**
	 * 移行処理をリトライするかどうか
	 */
	private boolean retryFlag = false;
	
	/**
	 * 連動データ取得処理をリトライするかどうか
	 */
	private boolean retryAcquisitionFlag = false;
	
	/**
	 * リトライ回数
	 */
	private int retryCount = 0;
	
	/**
	 * リトライインターバル
	 */
	private int retryInterval = 0;
	
	/**
	 * 残り確認時、前の残り数を保存 <br>
	 * 次の処理で、前の残りが同じなら、処理を終了 <br>
	 * 無限ループを防止する為、必要
	 */
	private int saveRemnantCount = 0;
	
	/**
	 * 設定データ
	 */
	public static ApplicationConfigurationModel config = null;
	
	/**
	 * コンストラクタ
	 */
	public VfInputDatMigratorExecutor() {
		
	}
	
	/**
	 * コンストラクタ
	 */
	public VfInputDatMigratorExecutor(JsonNode configNode) throws Exception  {

		//　設定データチェック
		VfInputDatMigratorInitialization initialization = new VfInputDatMigratorInitialization();
		config = initialization.convertConfiguration(configNode);

		retryCount = Integer.parseInt(config.retryCount);
		retryInterval = Integer.parseInt(config.retryInterval);
		retryFlag = true;
		retryAcquisitionFlag = true;
	}
	
	/**
	 * 初期化メソッド<br>
	 * ロガーを初期化する
	 */
	public void init() {
		if (myLogger == null) {
			myLogger = LogManager.getLogger("myLogger");
		}
	}
	
	/**
	 * 手入力データ移行を実行するメソッド
	 * @throws Exception データベース接続失敗
	 */
	public void execute() throws Exception {
		
		while (retryFlag && retryCount > 0) {
			TimeUtil.sleep(retryInterval);
					
			// mariaDb接続テスト
			try {
				myLogger.info("mariaDbの接続テストを行います。");
				new ConnectMariaDbFunction().execute();
				myLogger.info("mariaDbの接続テストが成功的に終わりました。");
			} catch (Exception e) {
				myLogger.fatal("mariaDbの接続に失敗しました。" + "\n" + e.getMessage(), e);
				throw e;
			}
			
			// cassandraDb接続
			try {
				myLogger.info("cassandraの接続テストの行います。");
				new ConnectCassandraFunction().execute();
				myLogger.info("cassandraの接続テストが成功的に終わりました。");
			} catch (Exception e) {
				myLogger.fatal("cassandraの接続に失敗しました。" + "\n" + e.getMessage(), e);
				throw e;
			}
			
			//　不要連動順/不要連動データ削除
			boolean delFlag = true;
			try {
				myLogger.info("不要連動順/不要連動データ削除します。");
				new DeleteExceedFunction().execute();
				myLogger.info("不要連動順/不要連動データ削除しました。");
			} catch (Exception e) {
				myLogger.error("不要連動順/不要連動データ削除に失敗しました。", e);
				retryCount--;
				delFlag = false;
			}
			
			acquisitionLoop:while(retryAcquisitionFlag && delFlag) {
				//　連動順データ取得
				List<InterlockSeq> seqList = null;
				try {
					myLogger.info("連動順データを取得します。");
					seqList = new SelectSequenceFunction().execute();
					
					//　連動順データが無い場合
					if (seqList.isEmpty() || seqList == null || seqList.size() == 0) {
						retryAcquisitionFlag = false;
						retryFlag = false;
						myLogger.info("連動順データが無いのでプログラムを終了します。");
						continue;
					}
					myLogger.info("連動順データを取得しました。");
				} catch (Exception e) {
					myLogger.error("連動順データ取得に失敗しました。" + "\n" + e.getMessage(), e);
					retryCount--;
					break acquisitionLoop;
				}
				
				for (InterlockSeq is : seqList) {
					
					//　連動データ取得
					//　dataMapのキー：処理方法フラグ (外観検査OK, 外観検査NG, 外観検査以外)
					//　dataMapの値：連動データリスト　(連動データ種類別のリスト)
					//　構成： Map< flag(Integer), List< [interlock_visual_inspection](list), [interlock_work_status](list) > >
					//　[interlock_visual_inspection](list)又は[interlock_work_status](list)が存在しない場合もある
					Map<Integer, List<Object>> dataMap = null;
					try {
						myLogger.info("連動データを取得します。" + "\n" + is);
						dataMap = new SelectDataFunction().execute(is);
						if (dataMap == null || dataMap.isEmpty()) {
							//　データが存在しない場合処理しない
							myLogger.warn("連動データ取得処理結果がありません。" + "\n" + "処理をスキップします。" + "\n" + is);
							continue;
						}
						myLogger.info(is.getExecTable() + "テーブル(連動データ)を取得しました。");
					} catch (Exception e) {
						myLogger.error("連動データ取得に失敗しました。" + "\n" + e.getMessage(), e);
						retryCount--;
						break acquisitionLoop;
					}
					
					//　対象判定
					
					//　対象連動データ
					//　dataMapのキー：処理方法フラグ (OK, NG, 外観検査以外)　と　移行対象外フラグ
					//   -- 0  : 外観検査OK
					//   -- 1  : 外観検査NG
					//   -- 2  : 外観検査以外
					//   -- -1 : 移行対象外
					// dataMapの値：対象データリスト
					//   -- 中身 : interlock_visual_insepctionとinsterlock_work_statusのリスト
					//　　中身例） List [ ivi, ivi... , iws, iws... ]
					Map<Integer, List<Object>> targetDataMap = null;
					try {
						myLogger.info("挿入対象判定を行います。");
						targetDataMap = new JudgmentFunction().execute(dataMap);
						myLogger.info("挿入対象判定を行いました。");
					} catch (Exception e) {
						myLogger.error("挿入対象判定に失敗しました。", e);
						retryCount--;
						break acquisitionLoop;
					}
					
					if (targetDataMap != null && !targetDataMap.isEmpty()) {
						
						//　連動データ変換
						List<TrnEvent> trnEventList = null;
						try {
							myLogger.info(is.getExecTable() + "テーブル(連動データ)を変換します。");
							trnEventList = new ConversionFunction().execute(targetDataMap);
							myLogger.info(is.getExecTable() + "テーブル(連動データ)を変換しました。");
						} catch (Exception e) {
							myLogger.error("連動データ変換に失敗しました。" + "\n" + e.getMessage(), e);
							retryCount--;
							break acquisitionLoop;
						}
						
						//　連動データ反映
						try {
							myLogger.info("変換済みの連動データをCassandraに挿入します。");
							new InsertCassandraFunction().execute(trnEventList);
							myLogger.info("変換済みの連動データをCassandraに挿入しました。");
						} catch (Exception e) {
							myLogger.error("変換済みの連動データのCassandraの挿入に失敗しました。", e);
							retryCount--;
							break acquisitionLoop;
						}
					}
					
					//　連動データと連動順データ削除
					try {
						myLogger.info("連動データと連動順データを削除します。");
						new DeleteProccessedFunction().execute(dataMap, is);
						myLogger.info("連動データと連動順データを削除しました。");
					} catch (Exception e) {
						myLogger.error("連動データと連動順データ削除に失敗しました。", e);
						retryCount--;
						break acquisitionLoop;
					}
				}
				
				//　連動順データ残チェック
				try {
					myLogger.info("連動順データ残チェックをします。");
					int res = new CheckRemnantFunction().execute();
					
					//　残りがあるかを確認
					boolean chk = (res <= 0);
					
					//　もしもあったら、前回の残り数と同じかを確認
					//　同じなら、処理可能なデータが無いということとなるので、処理を終了
					chk = (chk == true) ? true : (res == saveRemnantCount);
					
					//　前回の残数を更新
					saveRemnantCount = res;
					
					if (chk) {
						retryFlag = false;
						retryAcquisitionFlag = false;
						myLogger.info("連動順データが無いのでプログラムを終了します。");
						break acquisitionLoop;
					}
					myLogger.info("連動順データ残チェックをしました。");
				} catch (Exception e) {
					myLogger.error("連動順データ残チェックに失敗しました。", e);
					retryCount--;
					break acquisitionLoop;
				}
			}
			
			try {
				myLogger.info("データベースを切断します。");
				//　切断対象はCassandraだけ。
				//　mariaDBはmyBatis（データプル）を使用するので自動切断される。
				new DisconnectDatabaseFunction().execute();
				myLogger.info("データベースを切断しました。");
			} catch (Exception e) {
				myLogger.error("データベースの切断に失敗しました。", e);
				continue;
			}
		}
		
		myLogger.info("処理を終了します。");
	}
	
	/**
	 * サービス終了時処理
	 */
	public void doEndEvent() {
		try {
			new DisconnectDatabaseFunction().execute();
		} catch (Exception e) {
		}
	}
}
