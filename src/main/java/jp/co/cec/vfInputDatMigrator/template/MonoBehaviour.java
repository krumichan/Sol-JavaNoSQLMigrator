package jp.co.cec.vfInputDatMigrator.template;

import java.lang.reflect.Method;

/**
 * サービスクラスの基底クラス
 */
public class MonoBehaviour {

	/**
	 * メソッドを実行
	 * @param methodName　実行するメソッド名
	 * @throws Exception　実行失敗
	 */
	public void sendMessage(MonoBehaviour instance, String methodName) {
		try {
			Method m = instance.getClass().getMethod(methodName);
			m.invoke(instance);
		} catch (Exception e) {
		}
		
	}
	
	/**
	 * メソッドを実行
	 * @param methodName　実行するメソッド名
	 * @param params　実行時の引数
	 * @throws Exception　実行失敗
	 */
	public void sendMessage(MonoBehaviour instance, String methodName, Object... params) {
		try {
			Method m = instance.getClass().getMethod(methodName, params.getClass());
			Object conObj = params;
			m.invoke(instance, conObj);
		} catch (Exception e) {
		}
	}
	
}
