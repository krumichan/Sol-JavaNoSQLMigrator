package jp.co.cec.vfInputDatMigrator.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Time Utility
 */
public class TimeUtil {
	
	private static Logger myLogger = LogManager.getLogger("myLogger");
	
	/**
	 * @fn LocalDateTime convertDefaultTimeZoneToUtc(LocalDateTime target)
	 * @brief  対象の世界標準時をシステムデフォルトのタイムゾーン時間に変換(LocalDateTime）
	 * @param 	target	変換対象
	 * @return	変換値
	 */
	public static LocalDateTime convertUtcToDefaultTimeZone(LocalDateTime target) {
		LocalDateTime result = null;

		if (target != null) {
			result = target.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
		}

		return result;
	}
	
	/**
	 * 指定され秒までスリープ
	 * @param second 指定秒
	 */
	public static void sleep(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
		}
	}
	
	public static Timestamp convertLocalDateTimeToTimeStamp(LocalDateTime time) {
		return Timestamp.valueOf(convertUtcToDefaultTimeZone(time));
	}
	
	/**
	 * 日時をDate型に変換して返す
	 * @param targetDttm 対象の日時(文字列)
	 * @return Date型の日時
	 */
	public static Date dttmToDateTime(String targetDttm) {
		Date date = null;
		try{
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			date = sdFormat.parse(targetDttm);
		}catch(Exception e){
			return null;
		}
		return date;
	}
	
	/**
	 * 日時をマイクロ秒に変換し、マイクロ秒のlongで返す
	 * @param targetDttm 対象の日時(文字列)
	 * @return マイクロ秒
	 */
	public static long dttmToMicroSec(String targetDttm) {
		long microSec = 0L;
		String beforeFormat = "uuuu/MM/dd HH:mm:ss.SSS";
		if(targetDttm != null && targetDttm.contains("-")){
			beforeFormat = "uuuu-MM-dd HH:mm:ss.SSS";
		}
		if(targetDttm !=null && !(targetDttm.contains("."))){
			targetDttm = targetDttm + ".000";
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(beforeFormat);

		try {
			LocalDateTime ldt = LocalDateTime.parse(targetDttm,dtf);
			microSec = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() * 1000L;
		} catch (DateTimeParseException e) {
			myLogger.warn("書式変換に失敗しました。対象:" + targetDttm + ", 変換書式:" + beforeFormat);
		}

		return microSec;
	}
	
	/**
	 * 現在時間を、マイクロ秒のlongで返す
	 * @param targetDttm 対象の日時(文字列)
	 * @return マイクロ秒
	 */
	public static long nowToMicroSec() {
		Instant now = Instant.now();
		long micro = TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
		long microSec = TimeUnit.MILLISECONDS.toMicros(now.toEpochMilli()) + micro;
		return microSec;
	}
}
