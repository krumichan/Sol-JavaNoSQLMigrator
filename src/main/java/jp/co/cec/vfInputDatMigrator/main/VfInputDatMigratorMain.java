package jp.co.cec.vfInputDatMigrator.main;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfInputDatMigrator.constant.Constants;
import jp.co.cec.vfInputDatMigrator.model.ApplicationConfigurationKeyModel;
import jp.co.cec.vfInputDatMigrator.template.MonoBehaviour;
import jp.co.cec.vfInputDatMigrator.util.DialogUtil;
import jp.co.cec.vfInputDatMigrator.util.JsonUtil;
import jp.co.cec.vfInputDatMigrator.util.LoggerUtil;
import jp.co.cec.vfInputDatMigrator.util.PathUtil;
import jp.co.cec.vfInputDatMigrator.util.PropertyUtil;

/**
 * Vf手入力データ移行ツールのメインクラス
 */
public class VfInputDatMigratorMain {
	
	/**
	 * ロガー
	 */
	private static Logger myLogger = null;
	
	/**
	 * 設定データのJson
	 */
	private static JsonNode conf = null;

	/**
	 * 終了処理が実行されたのか
	 */
	private static volatile boolean isExited = false;
	
	/**
	 * 生成したインスタンスリスト
	 */
	private static List<MonoBehaviour> serviceList = new ArrayList<MonoBehaviour>();

	/**
	 * Vf手入力データ移行ツールのメインメソッド 
	 * @param args 設定ファイルの場所
	 */
	public static void main(String[] args) {
		//　 引数チェック
		if (!checkArguments(args)) {
			System.exit(1);
			return;
		}
		
		//　設定データ取得
		if (!readAppConf(args[0])) {
			System.exit(1);
			return;
		}
		
		//　ロガー前の準備
		if (!prepareBeforeLogger(null)) {
			System.exit(1);
			return;
		}

		try {
			Runtime.getRuntime().addShutdownHook(new Thread(Constants.APPLICATION_NAME + " shutdownhook...") {
				@Override
				public void run() {
					doEndEvent();
				}
			});
			
			VfInputDatMigratorExecutor func = new VfInputDatMigratorExecutor(conf);
			serviceList.add(func);
			func.init();
			func.execute();
			
		} catch (Exception e) {
			myLogger.fatal("プログラムが異常終了しました。", e);
		} finally {
			doEndEvent();
		}
	}
	
	/**
	 * 終了時の処理
	 */
	public static void doEndEvent() {
		if (!isExited) {
			isExited = true;
			
			serviceList.forEach(mono -> {
				mono.sendMessage(mono, ("doEndEvent"));
			});
		} else {
			return;
		}
	}
	
	/**
	 * 設定ファイルパスがあるかをチェック
	 * @param args 設定ファイルパス
	 */
	private static boolean checkArguments(String[] args) {
		//　引数チェック
		if (args == null || args.length < 1) {
			DialogUtil.showMessageDialogWithText(Constants.IS_SHOW_MESSAGE_DIALOG, "Javaコマンドへの起動引数が異常なため、起動できませんでした。");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 設定ファイルの読込処理
	 * @param filePath 設定ファイルパス 
	 */
	public static boolean readAppConf(String filePath) {
		JsonUtil ju = new JsonUtil();
		try {
			conf = ju.jsonRead(filePath, JsonNode.class);
		} catch (Exception e) {
			String msg = String.format("共通設定ファイルの読み込みに失敗しました。ファイル名:%s", filePath);
			DialogUtil.showMessageDialogWithTextArea(Constants.IS_SHOW_MESSAGE_DIALOG, msg);
			return false;
		}
		
		return true;
	}
	
	/**
	 * ロガーの準備作業
	 * @param cwdPath 現在場所
	 */
	public static boolean prepareBeforeLogger(Path cwdPath) {
		//　カレントディレクトリ
		if (cwdPath == null) {
			cwdPath = PathUtil.getCWD();
		}
		
		String logFileDestination = PropertyUtil.getValue(conf, ApplicationConfigurationKeyModel.LOG_PATH);
		if (logFileDestination == null || logFileDestination.isEmpty()) {
			String message = ApplicationConfigurationKeyModel.LOG_PATH + "がnull又は空なので、起動できませんでした。";
			DialogUtil.showMessageDialogWithText(Constants.IS_SHOW_MESSAGE_DIALOG, message);
			return false;
		}
		
		logFileDestination += Constants.APPLICATION_NAME + ".log";
		if (!Paths.get(logFileDestination).isAbsolute()) {
			logFileDestination = cwdPath.resolve(logFileDestination).normalize().toString();
		}
		
		if (LoggerUtil.setLogPath(logFileDestination, Constants.IS_SHOW_MESSAGE_DIALOG, "myLogger", "dir_log")) {
		} else {
			DialogUtil.showMessageDialogWithText(Constants.IS_SHOW_MESSAGE_DIALOG, "LogPathの登録に失敗したので、起動できませんでした。");
			return false;
		}
		
		if (LoggerUtil.canWriteLog(Constants.IS_SHOW_MESSAGE_DIALOG, "myLogger")) {
			myLogger = LogManager.getLogger("myLogger");
		} else {
			return false;
		}
		
		return true;
	}
	
	/**起動したJarの絶対パスを取得する
	 * 例："C:\path\to\app.jar"を起動した場合、"C:\path\to"が返る。
	 * @throws URISyntaxException
	 *
	 */
	public static Path getAppPath(Class<?> klass) throws URISyntaxException{
		ProtectionDomain pd = klass.getProtectionDomain();
		CodeSource cs = pd.getCodeSource();
		URL url = cs.getLocation();
		URI uri = url.toURI();
		Path path = Paths.get(uri);
		if(Objects.nonNull(myLogger)){
			myLogger.trace("path:"+path);
			myLogger.trace("path.getParent:"+path.getParent());
		}
		return path.getParent();
	}
}
