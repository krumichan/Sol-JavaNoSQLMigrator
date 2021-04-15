package jp.co.cec.vfInputDatMigrator.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassUtil {
	private ClassUtil() { }
	
	/**
	 * クラス内部の全てとゲッターを実行し、データを取得
	 * @param obj　対象
	 * @return	全データ
	 * @throws Exception　ゲッターメソッドが不正
	 */
	public static Map<String, Object> getGetterData(Object obj) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		
		Field[] fields = obj.getClass().getDeclaredFields();
		Class<?> clazz = obj.getClass();
		
		String prifix = "get";
		for (Field field : fields) {
			
			String fieldName = field.getName();
			String firstUpper = fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
			String methodName = prifix + firstUpper;
			Object returnVal = null;
			
			try {
				//　大文字に始めるゲッターだと判定
				returnVal = clazz.getMethod(methodName).invoke(obj);
			} catch (Exception e) {
				try {
					//　小文字に始めるゲッターだと判定
					returnVal = clazz.getMethod(prifix + fieldName).invoke(obj);;
				} catch (Exception e1) {
					//　大文字でも小文字でもない場合、エラーを出力
					throw new Exception("【" + obj.getClass().getName() + "】に" 
							+ "「" + methodName + ", " + (prifix + fieldName) + "」メソッドが存在しません。");
				}
			}
			
			if (returnVal instanceof String) {
				returnVal = FormatUtil.trimStr((String)returnVal).isEmpty() ? null : returnVal;
			}
			
			res.put(
					fieldName
					,returnVal
					);
		}
		return res;
	}
	
	/**
	 * タイプを自動的にキャストする
	 * @param <T>　キャストするタイプ
	 * @param obj　キャスト対象
	 * @return　キャスト済み対象
	 */
	@SuppressWarnings("unchecked")
    public static <T> T autoCast(Object obj) {
        T castObj = (T) obj;
        return castObj;
    }
}