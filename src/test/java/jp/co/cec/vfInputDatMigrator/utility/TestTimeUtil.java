package jp.co.cec.vfInputDatMigrator.utility;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TestTimeUtil {

	private static Timestamp delDateStandard = Timestamp.valueOf("2020-01-08 12:00:00");
	
	public static String getOldDelDate() {
		Timestamp ts = delDateStandard;
		LocalDate end = LocalDateTime.now().toLocalDate();
		LocalDate start = ts.toLocalDateTime().toLocalDate();
		return String.valueOf(ChronoUnit.DAYS.between(start, end) - 2);
	}
}
