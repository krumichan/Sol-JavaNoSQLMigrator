package jp.co.cec.vfInputDatMigrator.util;

import static jp.co.cec.vfInputDatMigrator.constant.Constants.LOCAL_DATE_TIME_FORMAT_SLASH_AND_UNDER3;
import static jp.co.cec.vfInputDatMigrator.constant.Constants.LOCAL_DATE_TIME_FORMAT_UNDER3;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Format Utility
 */
public class FormatUtil {

	/**
	 * 半角・全角の空白とタブのトリムを行う
	 * @param target トリムを行う文字列
	 * @return トリムの結果
	 */
	public static String trimStr(String target){
	    if (target == null || target.length() == 0)
	        return target;
	    int st = 0;
	    int len = target.length();
	    char[] val = target.toCharArray();
	    while ((st < len) && ((val[st] <= ' ') || (val[st] == '　'))) {
	        st++;
	    }
	    while ((st < len) && ((val[len - 1] <= ' ') || (val[len - 1] == '　'))) {
	        len--;
	    }
	    return ((st > 0) || (len < target.length())) ? target.substring(st, len) : target;
	}
	
	/**
	 *　文字列の日時をDate形式に変換
	 * millisecond .xxx 例：2019-12-19 19:18:22.123
	 * @return	変換結果
	 */
	private static Date formatStringToDateUnder3(String targetString){
		Date result = null;
		try {
			if(targetString != null) {
				result = new SimpleDateFormat(LOCAL_DATE_TIME_FORMAT_UNDER3).parse(targetString);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * LocalDateTime形式の日時を年月日の間に'-'を付けて変換
	 * 例> yyyy-mm-dd HH:mm:ss.SSS
	 * @return	変換結果
	 */
	public static String formatDateToStringUnder3(Timestamp targetDate) {
		String result = null;
		if (targetDate == null) {
			return result;
		}
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT_UNDER3);
		result = targetDate.toLocalDateTime().format(dateTimeFormatter);

		return result;
	}
	
	/**
	 * LocalDateTime形式の日時を年月日の間に'-'を付けて変換
	 * 例> yyyy/mm/dd HH:mm:ss.SSS
	 * @return	変換結果
	 */
	public static String formatDateToStringSlashAndUnder3(Timestamp targetDate) {
		String result = null;
		if (targetDate == null) {
			return result;
		}
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT_SLASH_AND_UNDER3);
		result = targetDate.toLocalDateTime().format(dateTimeFormatter);

		return result;
	} 
	
	public static String formatDateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		return dateFormat.format(date); 
	}

	/**
	 *　文字列の日時をLocalDateDate形式に変換
	 * millisecond .xxx 例：2019-12-19 19:18:22.123
	 * @return	変換結果
	 */
	public static LocalDateTime formatStringToDateTimeUnder3(String targetString){
		LocalDateTime result;

		// 一度文字列をDateに変換
		Date temp = formatStringToDateUnder3(targetString);
		// DateをLocalDateTimeへ変換
		result = LocalDateTime.ofInstant(temp.toInstant(), ZoneId.systemDefault());

		return result;
	}
}
