package jp.co.cec.vfInputDatMigrator.db.mapper;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * mariaDBのinterlock_data_itemに接近するマッパ 
 */
@Mapper
public interface InterlockDataItemMapper {
	
	/**
	 * 有効期間を超えたデータを削除
	 * @param validityEndOn 有効期間
	 */
	int deleteInterlockDataItemUsingValidityEndOn(
			@Param("validityEndOn") LocalDateTime validityEndOn);
}
