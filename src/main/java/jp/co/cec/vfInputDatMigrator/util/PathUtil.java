package jp.co.cec.vfInputDatMigrator.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path Utility
 */
public class PathUtil {
	private PathUtil() { }
	
	/**
	 * カレントディレクトリを取得
	 * @return カレントディレクトリ
	 */
	public static Path getCWD() {
		String cwd = System.getProperty("user.dir", "");
		Path cwdPath = Paths.get(cwd);
		return cwdPath;
	}
	
	/**
	 * 指定されたパスのファイルが絶対経路かを確認
	 * @param filePath　確認対象
	 * @return　確認結果
	 */
	private static boolean isAbsolute(String filePath) {
		return Paths.get(filePath).isAbsolute();
	}
	
	/**
	 * 指定されたパスの絶対経路に変換
	 * @param filePath　変換対象
	 * @return　変換結果
	 */
	public static String getAbsolute(String filePath) {
		if (isAbsolute(filePath)) {
			return filePath;
		}
		Path cwdPath = Paths.get(System.getProperty("user.dir", ""));
		return cwdPath.resolve(filePath).normalize().toString();
	}
}
