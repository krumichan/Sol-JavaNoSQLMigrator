package jp.co.cec.vfInputDatMigrator.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jp.co.cec.vfInputDatMigrator.dao.TestInputDatMigratorMariDao;
import jp.co.cec.vfInputDatMigrator.db.dao.SqlSessionFactoryCustom;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockDataItem;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockSeq;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockVisualInspection;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockWorkStatus;
import jp.co.cec.vfInputDatMigrator.function.ConnectMariaDbFunction;
import jp.co.cec.vfInputDatMigrator.function.DeleteExceedFunction;
import jp.co.cec.vfInputDatMigrator.function.DisconnectDatabaseFunction;
import jp.co.cec.vfInputDatMigrator.function.JudgmentFunction;
import jp.co.cec.vfInputDatMigrator.function.SelectDataFunction;
import jp.co.cec.vfInputDatMigrator.function.SelectSequenceFunction;
import jp.co.cec.vfInputDatMigrator.function.TestVfInputDatMigratorFunction;
import jp.co.cec.vfInputDatMigrator.main.VfInputDatMigratorExecutor;
import jp.co.cec.vfInputDatMigrator.utility.TestTimeUtil;

public class VFfInputDatMigratorAcquisitionTest {

	private static ByteArrayOutputStream logOutputStream;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logOutputStream = new ByteArrayOutputStream();
		WriterAppender appender = new WriterAppender(new PatternLayout("[%p] %d %c %l%n - %m%n"), logOutputStream);
		LogManager.getRootLogger().addAppender(appender);
		LogManager.getRootLogger().setAdditivity(false);
		SqlSessionFactoryCustom.isTest = true;
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		logOutputStream.reset();
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test100_1000() {
	
		JsonNode config = null;
		TestInputDatMigratorMariDao dao = null;
		try {
			String fileName = "execute/config/test_configOldDel.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(fileName);
			dao = new TestInputDatMigratorMariDao(
					config.get("v1ServerAddress").asText()
					,config.get("v1UserName").asText()
					,config.get("v1Password").asText()
					,config.get("v1DBName").asText()
					);
			dao.runScript("execute/sql/oldDel.sql");
			String delDate = TestTimeUtil.getOldDelDate();
			((ObjectNode)config).put("oldDataDel", delDate + "d");
			new VfInputDatMigratorExecutor(config);
		} catch (Exception e) {
			fail("Set interlock_seq data fail.");
		}
		
		try {
			new ConnectMariaDbFunction().execute();
		} catch (Exception e) {
			fail("Preparation fail.");
		}
		
		try {
			assertTrue(dao.getSeq().size() == 7);
			assertTrue(dao.getDi().size() == 4);
			assertTrue(dao.getVi().size() == 2);
			assertTrue(dao.getWs().size() == 5);
			
			new DeleteExceedFunction().execute();
			
			List<InterlockSeq> testSeq = dao.getSeq();
			List<InterlockDataItem> testDi = dao.getDi();
			List<InterlockVisualInspection> testVi = dao.getVi();
			List<InterlockWorkStatus> testWs = dao.getWs();
			
			assertTrue(testSeq.size() == 4);
			assertTrue(testDi.size() == 2);
			assertTrue(testVi.size() == 1);
			assertTrue(testWs.size() == 4);
			
			InterlockSeq seq = testSeq.get(0);
			assertTrue(seq.getExecDateTime().equals(Timestamp.valueOf("2020-01-15 13:40:55.731523")));
			assertTrue(seq.getExecDml().equals("UPDATE"));
			assertTrue(seq.getExecTable().equals("trn_work_status"));
			assertTrue(seq.getKey1().equals("70100999"));
			assertTrue(seq.getKey2().equals("1110002890"));
			assertTrue(seq.getKey3().equals("2020-01-08 09:00:02.342"));
			assertTrue(seq.getKey4().equals("INSPECTION"));
			
			seq = testSeq.get(1);
			assertTrue(seq.getExecDateTime().equals(Timestamp.valueOf("2020-01-17 14:55:45.671251")));
			assertTrue(seq.getExecDml().equals("INSERT"));
			assertTrue(seq.getExecTable().equals("trn_visual_inspection"));
			assertTrue(seq.getKey1().equals("70100999"));
			assertTrue(seq.getKey2().equals("IT00000051"));
			assertTrue(seq.getKey3().equals("2020-01-17 05:55:45.670"));
			assertTrue(seq.getKey4().equals("CHK_OK"));

			seq = testSeq.get(2);
			assertTrue(seq.getExecDateTime().equals(Timestamp.valueOf("2020-01-24 10:49:40.589111")));
			assertTrue(seq.getExecDml().equals("UPDATE"));
			assertTrue(seq.getExecTable().equals("mst_data_item"));
			assertTrue(seq.getKey1().equals("99999999-70100999-insp"));
			assertTrue(seq.getKey2().equals("2017-11-30 15:00:00"));
			assertTrue(seq.getKey3().equals("2999-12-31 14:59:59"));
			assertTrue(Objects.isNull(seq.getKey4()));
			
			seq = testSeq.get(3);
			assertTrue(seq.getExecDateTime().equals(Timestamp.valueOf("2020-01-27 13:45:44.400741")));
			assertTrue(seq.getExecDml().equals("UPDATE"));
			assertTrue(seq.getExecTable().equals("trn_work_status"));
			assertTrue(seq.getKey1().equals("70100999"));
			assertTrue(seq.getKey2().equals("1110002890"));
			assertTrue(seq.getKey3().equals("2020-01-08 09:00:02.342"));
			assertTrue(seq.getKey4().equals("INSPECTION"));

			InterlockDataItem idi = testDi.get(0);
			assertTrue(idi.getExec_date_time().equals(Timestamp.valueOf("2020-01-24 10:49:40.589111")));
			assertTrue(idi.getExec_data_flag().equals(Byte.valueOf((byte)0)));
			assertTrue(idi.getLog_id().equals("99999999-70100999-insp"));
			assertTrue(idi.getElement_index().equals("none"));
			assertTrue(idi.getElement_index_key().equals("none"));
			assertTrue(idi.getElement().equals("外観検査判定結果"));
			assertTrue(idi.getOriginal_item_name().equals("外観検査判定結果"));
			assertTrue(idi.getSubject_type().equals("object"));
			assertTrue(idi.getAcquisition().equals("ProductionControlApi"));
			assertTrue(Objects.isNull(idi.getCalculation_point()));
			assertTrue(idi.getGeneration_flag().equals("__C_____________"));
			assertTrue(idi.getInput_data_type().equals("string"));
			assertTrue(Objects.isNull(idi.getOutput_data_format()));
			assertTrue(Objects.isNull(idi.getUnit()));
			assertTrue(idi.getDevice().equals("VF"));
			assertTrue(idi.getMaster_id().equals("99999999-70100999"));
			assertTrue(idi.getChanging_point().equals("first"));
			assertTrue(idi.getHost_name().equals("ProductionControlApi"));
			assertTrue(idi.getValidity_start_on().equals(Timestamp.valueOf("2017-11-30 15:00:00")));
			assertTrue(idi.getValidity_end_on().equals(Timestamp.valueOf("2999-12-31 14:59:59")));
			assertTrue(idi.getCreated_by().equals("ProductionControlApi"));
			assertTrue(idi.getCreated_at().equals(Timestamp.valueOf("2018-10-29 14:52:25")));
			assertTrue(idi.getUpdated_by().equals("ProductionControlApi"));
			assertTrue(idi.getUpdated_at().equals(Timestamp.valueOf("2020-01-23 16:30:49")));
			
			idi = testDi.get(1);
			assertTrue(idi.getExec_date_time().equals(Timestamp.valueOf("2020-01-24 10:49:40.589111")));
			assertTrue(idi.getExec_data_flag().equals(Byte.valueOf((byte)1)));
			assertTrue(idi.getLog_id().equals("99999999-70100999-insp"));
			assertTrue(idi.getElement_index().equals("none"));
			assertTrue(idi.getElement_index_key().equals("none"));
			assertTrue(idi.getElement().equals("外観検査判定結果"));
			assertTrue(idi.getOriginal_item_name().equals("外観検査判定結果"));
			assertTrue(idi.getSubject_type().equals("object"));
			assertTrue(idi.getAcquisition().equals("ProductionControlApi"));
			assertTrue(Objects.isNull(idi.getCalculation_point()));
			assertTrue(idi.getGeneration_flag().equals("__C_____________"));
			assertTrue(idi.getInput_data_type().equals("string"));
			assertTrue(Objects.isNull(idi.getOutput_data_format()));
			assertTrue(Objects.isNull(idi.getUnit()));
			assertTrue(idi.getDevice().equals("VF"));
			assertTrue(idi.getMaster_id().equals("99999999-70100999"));
			assertTrue(idi.getChanging_point().equals("first"));
			assertTrue(idi.getHost_name().equals("ProductionControlApi"));
			assertTrue(idi.getValidity_start_on().equals(Timestamp.valueOf("2017-11-30 15:00:00")));
			assertTrue(idi.getValidity_end_on().equals(Timestamp.valueOf("2999-12-31 14:59:59")));
			assertTrue(idi.getCreated_by().equals("ProductionControlApi"));
			assertTrue(idi.getCreated_at().equals(Timestamp.valueOf("2018-10-29 14:52:25")));
			assertTrue(idi.getUpdated_by().equals("ProductionControlApi"));
			assertTrue(idi.getUpdated_at().equals(Timestamp.valueOf("2020-01-24 10:49:40")));
			
			InterlockVisualInspection ivi = testVi.get(0);
			assertTrue(ivi.getExec_date_time().equals(Timestamp.valueOf("2020-01-17 14:55:45.671251")));
			assertTrue(ivi.getExec_data_flag() == 1);
			assertTrue(ivi.getPlace_id().equals("70100999"));
			assertTrue(ivi.getSerial_number().equals("IT00000051"));
			assertTrue(ivi.getEntry_date_time().equals(Timestamp.valueOf("2020-01-17 05:55:45.670")));
			assertTrue(ivi.getEntry_worker().equals("t6e"));
			assertTrue(ivi.getJudgment_key().equals("CHK_OK"));
			assertTrue(ivi.getNg_work_status_key().equals(""));
			assertTrue(ivi.getStatus_reason_key().equals(""));
			assertTrue(ivi.getStatus_reason().equals(""));
			assertTrue(ivi.getNote().equals("外観検査OKテスト"));
			assertTrue(ivi.getCreated_by().equals("testadmin"));
			assertTrue(ivi.getCreated_at().equals(Timestamp.valueOf("2020-01-17 05:55:45")));
			assertTrue(ivi.getUpdated_by().equals("testadmin"));
			assertTrue(ivi.getUpdated_at().equals(Timestamp.valueOf("2020-01-17 05:55:45")));
			
			InterlockWorkStatus iws = testWs.get(0);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-15 13:40:55.731523")));
			assertTrue(iws.getExec_data_flag() == 0);
			assertTrue(iws.getPlace_id().equals("70100999"));
			assertTrue(iws.getSerial_number().equals("1110002890"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-08 09:00:02.342")));
			assertTrue(iws.getWork_status_key().equals("INSPECTION"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("シナリオ9-3"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 0);
			assertTrue(iws.getCancel_date_time() == null);
			assertTrue(iws.getCancel_reason().equals(""));
			assertTrue(iws.getCancel_worker().equals(""));
			assertTrue(iws.getInspection_flg() == 0);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-08 09:00:02.342")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-08 09:00:02")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-08 09:00:02")));
			
			iws = testWs.get(1);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-15 13:40:55.731523")));
			assertTrue(iws.getExec_data_flag() == 1);
			assertTrue(iws.getPlace_id().equals("70100999"));
			assertTrue(iws.getSerial_number().equals("1110002890"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-08 09:00:02.342")));
			assertTrue(iws.getWork_status_key().equals("INSPECTION"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("シナリオ9-3"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 0);
			assertTrue(iws.getCancel_date_time() == null);
			assertTrue(iws.getCancel_reason().equals(""));
			assertTrue(iws.getCancel_worker().equals(""));
			assertTrue(iws.getInspection_flg() == 1);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-15 04:40:55.729")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-08 09:00:02")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-15 04:40:55")));
			
			iws = testWs.get(2);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-27 13:45:44.400741")));
			assertTrue(iws.getExec_data_flag() == 0);
			assertTrue(iws.getPlace_id().equals("70100999"));
			assertTrue(iws.getSerial_number().equals("1110002890"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-08 09:00:02.342")));
			assertTrue(iws.getWork_status_key().equals("INSPECTION"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("シナリオ9-3"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 0);
			assertTrue(iws.getCancel_date_time() == null);
			assertTrue(iws.getCancel_reason().equals(""));
			assertTrue(iws.getCancel_worker().equals(""));
			assertTrue(iws.getInspection_flg() == 1);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-15 04:40:55.729")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-08 09:00:02")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-15 04:40:55")));
			
			iws = testWs.get(3);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-27 13:45:44.400741")));
			assertTrue(iws.getExec_data_flag() == 1);
			assertTrue(iws.getPlace_id().equals("70100999"));
			assertTrue(iws.getSerial_number().equals("1110002890"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-08 09:00:02.342")));
			assertTrue(iws.getWork_status_key().equals("INSPECTION"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("シナリオ9-3"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 1);
			assertTrue(iws.getCancel_date_time().equals(Timestamp.valueOf("2020-01-27 04:45:44.399")));
			assertTrue(iws.getCancel_reason().equals("取消テスト"));
			assertTrue(iws.getCancel_worker().equals("testadmin"));
			assertTrue(iws.getInspection_flg() == 1);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-15 04:40:55.729")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-08 09:00:02")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-27 04:45:44")));
		} catch (Exception e) {
			fail("The program should not raise an exception.");
		} finally {
			try {
				new DisconnectDatabaseFunction().execute();
				dao.dropDb("old_del");
			} catch (Exception e) {
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test100_2000() {
	
		JsonNode config = null;
		TestInputDatMigratorMariDao dao = null;
		try {
			String fileName = "execute/config/test_configDatExists.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(fileName);
			dao = new TestInputDatMigratorMariDao(
					config.get("v1ServerAddress").asText()
					,config.get("v1UserName").asText()
					,config.get("v1Password").asText()
					,config.get("v1DBName").asText()
					);
			dao.runScript("execute/sql/datExists.sql");
			String delDate = TestTimeUtil.getOldDelDate();;
			((ObjectNode)config).put("oldDataDel", delDate + "d");
			new VfInputDatMigratorExecutor(config);
		} catch (Exception e) {
			fail("Set configuration fail.");
		}
		
		try {
			new ConnectMariaDbFunction().execute();
			new DeleteExceedFunction().execute();
		} catch (Exception e) {
			fail("Preparation fail.");
		}
		
		try {
			List<InterlockSeq> seqList = null;
			seqList = new SelectSequenceFunction().execute();
			
			assertTrue(seqList.size() == 4);
			
			Map<Integer, List<Object>> dataMap = null;
			dataMap = new SelectDataFunction().execute(seqList.get(0));
			assertTrue(Objects.isNull(dataMap));
			
			dataMap = new SelectDataFunction().execute(seqList.get(1));
			assertTrue(Objects.isNull(dataMap));
			
			dataMap = new SelectDataFunction().execute(seqList.get(2));
			assertTrue(dataMap.size() == 1);
			List<Object> listList = dataMap.get(0);
			assertTrue(!Objects.isNull(listList) && listList.size() == 1);
			List<Object> list = (List<Object>) listList.get(0);
			assertTrue(!Objects.isNull(listList) && list.size() == 1);
			InterlockVisualInspection ivi = (InterlockVisualInspection)list.get(0);
			assertTrue(ivi.getExec_date_time().equals(Timestamp.valueOf("2020-01-17 15:36:40.091906")));
			assertTrue(ivi.getExec_data_flag() == 1);
			assertTrue(ivi.getPlace_id().equals("70100999"));
			assertTrue(ivi.getSerial_number().equals("IT00000051"));
			assertTrue(ivi.getEntry_date_time().equals(Timestamp.valueOf("2020-01-17 06:36:40.091")));
			assertTrue(ivi.getEntry_worker().equals("fujiwara"));
			assertTrue(ivi.getJudgment_key().equals("CHK_OK"));
			assertTrue(ivi.getNg_work_status_key().equals(""));
			assertTrue(ivi.getStatus_reason_key().equals(""));
			assertTrue(ivi.getStatus_reason().equals(""));
			assertTrue(ivi.getNote().equals("外観検査OKテスト3"));
			assertTrue(ivi.getCreated_by().equals("testadmin"));
			assertTrue(ivi.getCreated_at().equals(Timestamp.valueOf("2020-01-17 06:36:40")));
			assertTrue(ivi.getUpdated_by().equals("testadmin"));
			assertTrue(ivi.getUpdated_at().equals(Timestamp.valueOf("2020-01-17 06:36:40")));
			
			dataMap = new SelectDataFunction().execute(seqList.get(3));
			assertTrue(dataMap.size() == 1);
			listList = dataMap.get(2);
			assertTrue(!Objects.isNull(listList) && listList.size() == 1);
			list = (List<Object>)listList.get(0);
			assertTrue(!Objects.isNull(listList) && list.size() == 2);
			InterlockWorkStatus iws = (InterlockWorkStatus)list.get(0);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-23 13:35:43.486997")));
			assertTrue(iws.getExec_data_flag() == 0);
			assertTrue(iws.getPlace_id().equals("70100004"));
			assertTrue(iws.getSerial_number().equals("1110002890"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-08 08:57:30.818")));
			assertTrue(iws.getWork_status_key().equals("INSPECTION"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("シナリオ9-3"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 0);
			assertTrue(iws.getCancel_date_time() == null);
			assertTrue(iws.getCancel_reason().equals(""));
			assertTrue(iws.getCancel_worker().equals(""));
			assertTrue(iws.getInspection_flg() == 1);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-08 08:58:27.922")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-08 08:57:30")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-08 08:58:27")));
			
			iws = (InterlockWorkStatus)list.get(1);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-23 13:35:43.486997")));
			assertTrue(iws.getExec_data_flag() == 1);
			assertTrue(iws.getPlace_id().equals("70100004"));
			assertTrue(iws.getSerial_number().equals("1110002890"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-08 08:57:30.818")));
			assertTrue(iws.getWork_status_key().equals("INSPECTION"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("シナリオ9-3"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 1);
			assertTrue(iws.getCancel_date_time().equals(Timestamp.valueOf("2020-01-23 04:35:43.486")));
			assertTrue(iws.getCancel_reason().equals("取消テスト"));
			assertTrue(iws.getCancel_worker().equals("t6e"));
			assertTrue(iws.getInspection_flg() == 1);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-08 08:58:27.922")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-08 08:57:30")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-23 04:35:43")));
		} catch (Exception e) {
			fail("The program should not raise an exception.");
		} finally {
			try {
				new DisconnectDatabaseFunction().execute();
				dao.dropDb("dat_exists");
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test100_3000() {
	
		JsonNode config = null;
		TestInputDatMigratorMariDao dao = null;
		List<InterlockSeq> seqList = null;
		try {
			String fileName = "execute/config/test_configTarJudge.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(fileName);
			dao = new TestInputDatMigratorMariDao(
					config.get("v1ServerAddress").asText()
					,config.get("v1UserName").asText()
					,config.get("v1Password").asText()
					,config.get("v1DBName").asText()
					);
			dao.runScript("execute/sql/tarJudge.sql");
			String delDate = TestTimeUtil.getOldDelDate();
			((ObjectNode)config).put("oldDataDel", delDate + "d");
			new VfInputDatMigratorExecutor(config);
		} catch (Exception e) {
			fail("Set interlock_seq data fail.");
		}
		
		try {
			new ConnectMariaDbFunction().execute();
			new DeleteExceedFunction().execute();
			seqList = new SelectSequenceFunction().execute();
			assertTrue(seqList.size() == 10);
		} catch (Exception e) {
			fail("Preparation fail.");
		}
		
		try {
			Map<Integer, List<Object>> dataMap = null;
			Map<Integer, List<Object>> targetDataMap = null;
			List<Object> list = null;
			
			dataMap = new SelectDataFunction().execute(seqList.get(0));
			assertTrue(!Objects.isNull(dataMap) && dataMap.size() == 1);
			
			targetDataMap = new JudgmentFunction().execute(dataMap);
			assertTrue(!Objects.isNull(targetDataMap) && targetDataMap.size() == dataMap.size());
			
			list = targetDataMap.get(1);
			assertTrue(list.size() == 2);
			InterlockVisualInspection ivi = (InterlockVisualInspection)list.get(0);
			assertTrue(ivi.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:29:16.424720")));
			assertTrue(ivi.getExec_data_flag() == 1);
			assertTrue(ivi.getPlace_id().equals("70100999"));
			assertTrue(ivi.getSerial_number().equals("IT00000051"));
			assertTrue(ivi.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:29:16.424")));
			assertTrue(ivi.getEntry_worker().equals("testadmin"));
			assertTrue(ivi.getJudgment_key().equals("CHK_NG"));
			assertTrue(ivi.getNg_work_status_key().equals("INSPECTION"));
			assertTrue(ivi.getStatus_reason_key().equals("その他"));
			assertTrue(ivi.getStatus_reason().equals("その他"));
			assertTrue(ivi.getNote().equals("外観検査入検→取消"));
			assertTrue(ivi.getCreated_by().equals("testadmin"));
			assertTrue(ivi.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16")));
			assertTrue(ivi.getUpdated_by().equals("testadmin"));
			assertTrue(ivi.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16")));
			
			InterlockWorkStatus iws = (InterlockWorkStatus)list.get(1);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:29:16.425718")));
			assertTrue(iws.getExec_data_flag() == 1);
			assertTrue(iws.getPlace_id().equals("70100999"));
			assertTrue(iws.getSerial_number().equals("IT00000051"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:29:16.424")));
			assertTrue(iws.getWork_status_key().equals("INSPECTION"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("外観検査入検→取消"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 0);
			assertTrue(Objects.isNull(iws.getCancel_date_time()));
			assertTrue(iws.getCancel_reason().equals(""));
			assertTrue(iws.getCancel_worker().equals(""));
			assertTrue(iws.getInspection_flg() == 0);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16.424")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16")));
			
			
			dataMap = new SelectDataFunction().execute(seqList.get(1));
			assertTrue(!Objects.isNull(dataMap) && dataMap.size() == 1);
			
			targetDataMap = new JudgmentFunction().execute(dataMap);
			assertTrue(!Objects.isNull(targetDataMap) && targetDataMap.size() == dataMap.size());
			
			list = targetDataMap.get(1);
			assertTrue(list.size() == 3);
			ivi = (InterlockVisualInspection)list.get(0);
			assertTrue(ivi.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:29:16.424720")));
			assertTrue(ivi.getExec_data_flag() == 1);
			assertTrue(ivi.getPlace_id().equals("70100999"));
			assertTrue(ivi.getSerial_number().equals("IT00000051"));
			assertTrue(ivi.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:29:16.424")));
			assertTrue(ivi.getEntry_worker().equals("testadmin"));
			assertTrue(ivi.getJudgment_key().equals("CHK_NG"));
			assertTrue(ivi.getNg_work_status_key().equals("INSPECTION"));
			assertTrue(ivi.getStatus_reason_key().equals("その他"));
			assertTrue(ivi.getStatus_reason().equals("その他"));
			assertTrue(ivi.getNote().equals("外観検査入検→取消"));
			assertTrue(ivi.getCreated_by().equals("testadmin"));
			assertTrue(ivi.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16")));
			assertTrue(ivi.getUpdated_by().equals("testadmin"));
			assertTrue(ivi.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16")));
			
			iws = (InterlockWorkStatus)list.get(1);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:44:26.656648")));
			assertTrue(iws.getExec_data_flag() == 0);
			assertTrue(iws.getPlace_id().equals("70100999"));
			assertTrue(iws.getSerial_number().equals("IT00000051"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:29:16.424")));
			assertTrue(iws.getWork_status_key().equals("INSPECTION"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("外観検査入検→取消"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 0);
			assertTrue(Objects.isNull(iws.getCancel_date_time()));
			assertTrue(iws.getCancel_reason().equals(""));
			assertTrue(iws.getCancel_worker().equals(""));
			assertTrue(iws.getInspection_flg() == 0);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16.424")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16")));
			
			iws = (InterlockWorkStatus)list.get(2);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:44:26.656648")));
			assertTrue(iws.getExec_data_flag() == 1);
			assertTrue(iws.getPlace_id().equals("70100999"));
			assertTrue(iws.getSerial_number().equals("IT00000051"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:29:16.424")));
			assertTrue(iws.getWork_status_key().equals("INSPECTION"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("外観検査入検→取消"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 1);
			assertTrue(iws.getCancel_date_time().equals(Timestamp.valueOf("2020-01-16 02:44:26.656")));
			assertTrue(iws.getCancel_reason().equals("外観検査入検→取消"));
			assertTrue(iws.getCancel_worker().equals("testadmin"));
			assertTrue(iws.getInspection_flg() == 0);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16.424")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:29:16")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:44:26")));
			
			
			dataMap = new SelectDataFunction().execute(seqList.get(2));
			assertTrue(!Objects.isNull(dataMap) && dataMap.size() == 1);
			
			targetDataMap = new JudgmentFunction().execute(dataMap);
			assertTrue(!Objects.isNull(targetDataMap) && targetDataMap.size() == dataMap.size());
			
			list = targetDataMap.get(1);
			assertTrue(list.size() == 2);
			ivi = (InterlockVisualInspection)list.get(0);
			assertTrue(ivi.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:46:51.370640")));
			assertTrue(ivi.getExec_data_flag() == 1);
			assertTrue(ivi.getPlace_id().equals("70100999"));
			assertTrue(ivi.getSerial_number().equals("IT00000051"));
			assertTrue(ivi.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:46:51.370")));
			assertTrue(ivi.getEntry_worker().equals("testadmin"));
			assertTrue(ivi.getJudgment_key().equals("CHK_NG"));
			assertTrue(ivi.getNg_work_status_key().equals("DISCARD"));
			assertTrue(ivi.getStatus_reason_key().equals("その他"));
			assertTrue(ivi.getStatus_reason().equals("その他"));
			assertTrue(ivi.getNote().equals("外観検査廃棄→取消テスト"));
			assertTrue(ivi.getCreated_by().equals("testadmin"));
			assertTrue(ivi.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51")));
			assertTrue(ivi.getUpdated_by().equals("testadmin"));
			assertTrue(ivi.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51")));
			
			iws = (InterlockWorkStatus)list.get(1);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:46:51.371603")));
			assertTrue(iws.getExec_data_flag() == 1);
			assertTrue(iws.getPlace_id().equals("70100999"));
			assertTrue(iws.getSerial_number().equals("IT00000051"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:46:51.370")));
			assertTrue(iws.getWork_status_key().equals("DISCARD"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("外観検査廃棄→取消テスト"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 0);
			assertTrue(Objects.isNull(iws.getCancel_date_time()));
			assertTrue(iws.getCancel_reason().equals(""));
			assertTrue(iws.getCancel_worker().equals(""));
			assertTrue(iws.getInspection_flg() == 0);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51.370")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51")));
			
			dataMap = new SelectDataFunction().execute(seqList.get(3));
			assertTrue(!Objects.isNull(dataMap) && dataMap.size() == 1);
			
			targetDataMap = new JudgmentFunction().execute(dataMap);
			assertTrue(!Objects.isNull(targetDataMap) && targetDataMap.size() == dataMap.size());
			
			list = targetDataMap.get(1);
			assertTrue(list.size() == 3);
			ivi = (InterlockVisualInspection)list.get(0);
			assertTrue(ivi.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:46:51.370640")));
			assertTrue(ivi.getExec_data_flag() == 1);
			assertTrue(ivi.getPlace_id().equals("70100999"));
			assertTrue(ivi.getSerial_number().equals("IT00000051"));
			assertTrue(ivi.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:46:51.370")));
			assertTrue(ivi.getEntry_worker().equals("testadmin"));
			assertTrue(ivi.getJudgment_key().equals("CHK_NG"));
			assertTrue(ivi.getNg_work_status_key().equals("DISCARD"));
			assertTrue(ivi.getStatus_reason_key().equals("その他"));
			assertTrue(ivi.getStatus_reason().equals("その他"));
			assertTrue(ivi.getNote().equals("外観検査廃棄→取消テスト"));
			assertTrue(ivi.getCreated_by().equals("testadmin"));
			assertTrue(ivi.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51")));
			assertTrue(ivi.getUpdated_by().equals("testadmin"));
			assertTrue(ivi.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51")));
			
			iws = (InterlockWorkStatus)list.get(1);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:47:28.588029")));
			assertTrue(iws.getExec_data_flag() == 0);
			assertTrue(iws.getPlace_id().equals("70100999"));
			assertTrue(iws.getSerial_number().equals("IT00000051"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:46:51.370")));
			assertTrue(iws.getWork_status_key().equals("DISCARD"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("外観検査廃棄→取消テスト"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 0);
			assertTrue(Objects.isNull(iws.getCancel_date_time()));
			assertTrue(iws.getCancel_reason().equals(""));
			assertTrue(iws.getCancel_worker().equals(""));
			assertTrue(iws.getInspection_flg() == 0);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51.370")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51")));
			
			iws = (InterlockWorkStatus)list.get(2);
			assertTrue(iws.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:47:28.588029")));
			assertTrue(iws.getExec_data_flag() == 1);
			assertTrue(iws.getPlace_id().equals("70100999"));
			assertTrue(iws.getSerial_number().equals("IT00000051"));
			assertTrue(iws.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:46:51.370")));
			assertTrue(iws.getWork_status_key().equals("DISCARD"));
			assertTrue(iws.getStatus_reason_key().equals("その他"));
			assertTrue(iws.getStatus_reason().equals("その他"));
			assertTrue(iws.getNote().equals("外観検査廃棄→取消テスト"));
			assertTrue(iws.getEntry_worker().equals("testadmin"));
			assertTrue(iws.getCancel_flg() == 1);
			assertTrue(iws.getCancel_date_time().equals(Timestamp.valueOf("2020-01-16 02:47:28.586")));
			assertTrue(iws.getCancel_reason().equals("外観検査廃棄→取消テスト"));
			assertTrue(iws.getCancel_worker().equals("testadmin"));
			assertTrue(iws.getInspection_flg() == 0);
			assertTrue(iws.getInspection_updated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51.370")));
			assertTrue(iws.getCreated_by().equals("testadmin"));
			assertTrue(iws.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:46:51")));
			assertTrue(iws.getUpdated_by().equals("testadmin"));
			assertTrue(iws.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:47:28")));
			
			
			dataMap = new SelectDataFunction().execute(seqList.get(4));
			assertTrue(!Objects.isNull(dataMap) && dataMap.size() == 1);
			
			targetDataMap = new JudgmentFunction().execute(dataMap);
			assertTrue(!Objects.isNull(targetDataMap) && targetDataMap.size() == dataMap.size());
			
			list = targetDataMap.get(1);
			assertTrue(list.size() == 1);
			ivi = (InterlockVisualInspection)list.get(0);
			assertTrue(ivi.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:49:09.148328")));
			assertTrue(ivi.getExec_data_flag() == 1);
			assertTrue(ivi.getPlace_id().equals("70100999"));
			assertTrue(ivi.getSerial_number().equals("IT00000051"));
			assertTrue(ivi.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:49:09.148")));
			assertTrue(ivi.getEntry_worker().equals("testadmin"));
			assertTrue(ivi.getJudgment_key().equals("CHK_NG"));
			assertTrue(ivi.getNg_work_status_key().equals("STAY"));
			assertTrue(ivi.getStatus_reason_key().equals("その他"));
			assertTrue(ivi.getStatus_reason().equals("その他"));
			assertTrue(ivi.getNote().equals("外観検査保留→取消テスト"));
			assertTrue(ivi.getCreated_by().equals("testadmin"));
			assertTrue(ivi.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:49:09")));
			assertTrue(ivi.getUpdated_by().equals("testadmin"));
			assertTrue(ivi.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:49:09")));
			
			
			dataMap = new SelectDataFunction().execute(seqList.get(5));
			assertTrue(!Objects.isNull(dataMap) && dataMap.size() == 1);
			
			targetDataMap = new JudgmentFunction().execute(dataMap);
			assertTrue(!Objects.isNull(targetDataMap) && targetDataMap.size() == dataMap.size());
			
			list = targetDataMap.get(1);
			assertTrue(list.size() == 1);
			ivi = (InterlockVisualInspection)list.get(0);
			assertTrue(ivi.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:49:09.148328")));
			assertTrue(ivi.getExec_data_flag() == 1);
			assertTrue(ivi.getPlace_id().equals("70100999"));
			assertTrue(ivi.getSerial_number().equals("IT00000051"));
			assertTrue(ivi.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:49:09.148")));
			assertTrue(ivi.getEntry_worker().equals("testadmin"));
			assertTrue(ivi.getJudgment_key().equals("CHK_NG"));
			assertTrue(ivi.getNg_work_status_key().equals("STAY"));
			assertTrue(ivi.getStatus_reason_key().equals("その他"));
			assertTrue(ivi.getStatus_reason().equals("その他"));
			assertTrue(ivi.getNote().equals("外観検査保留→取消テスト"));
			assertTrue(ivi.getCreated_by().equals("testadmin"));
			assertTrue(ivi.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:49:09")));
			assertTrue(ivi.getUpdated_by().equals("testadmin"));
			assertTrue(ivi.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:49:09")));
			
			
			dataMap = new SelectDataFunction().execute(seqList.get(6));
			assertTrue(!Objects.isNull(dataMap) && dataMap.size() == 1);
			
			targetDataMap = new JudgmentFunction().execute(dataMap);
			assertTrue(!Objects.isNull(targetDataMap) && targetDataMap.size() == dataMap.size());
			
			list = targetDataMap.get(1);
			assertTrue(list.size() == 1);
			ivi = (InterlockVisualInspection)list.get(0);
			assertTrue(ivi.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:50:57.156077")));
			assertTrue(ivi.getExec_data_flag() == 1);
			assertTrue(ivi.getPlace_id().equals("70100999"));
			assertTrue(ivi.getSerial_number().equals("IT00000051"));
			assertTrue(ivi.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:50:57.156")));
			assertTrue(ivi.getEntry_worker().equals("testadmin"));
			assertTrue(ivi.getJudgment_key().equals("CHK_NG"));
			assertTrue(ivi.getNg_work_status_key().equals("OP_CHECK"));
			assertTrue(ivi.getStatus_reason_key().equals("その他"));
			assertTrue(ivi.getStatus_reason().equals("その他"));
			assertTrue(ivi.getNote().equals("外観検査OPチェック→取消テスト"));
			assertTrue(ivi.getCreated_by().equals("testadmin"));
			assertTrue(ivi.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:50:57")));
			assertTrue(ivi.getUpdated_by().equals("testadmin"));
			assertTrue(ivi.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:50:57")));
			
			
			dataMap = new SelectDataFunction().execute(seqList.get(7));
			assertTrue(!Objects.isNull(dataMap) && dataMap.size() == 1);
			
			targetDataMap = new JudgmentFunction().execute(dataMap);
			assertTrue(!Objects.isNull(targetDataMap) && targetDataMap.size() == dataMap.size());
			list = targetDataMap.get(1);
			assertTrue(list.size() == 1);
			ivi = (InterlockVisualInspection)list.get(0);
			assertTrue(ivi.getExec_date_time().equals(Timestamp.valueOf("2020-01-16 11:50:57.156077")));
			assertTrue(ivi.getExec_data_flag() == 1);
			assertTrue(ivi.getPlace_id().equals("70100999"));
			assertTrue(ivi.getSerial_number().equals("IT00000051"));
			assertTrue(ivi.getEntry_date_time().equals(Timestamp.valueOf("2020-01-16 02:50:57.156")));
			assertTrue(ivi.getEntry_worker().equals("testadmin"));
			assertTrue(ivi.getJudgment_key().equals("CHK_NG"));
			assertTrue(ivi.getNg_work_status_key().equals("OP_CHECK"));
			assertTrue(ivi.getStatus_reason_key().equals("その他"));
			assertTrue(ivi.getStatus_reason().equals("その他"));
			assertTrue(ivi.getNote().equals("外観検査OPチェック→取消テスト"));
			assertTrue(ivi.getCreated_by().equals("testadmin"));
			assertTrue(ivi.getCreated_at().equals(Timestamp.valueOf("2020-01-16 02:50:57")));
			assertTrue(ivi.getUpdated_by().equals("testadmin"));
			assertTrue(ivi.getUpdated_at().equals(Timestamp.valueOf("2020-01-16 02:50:57")));
			
			
			dataMap = new SelectDataFunction().execute(seqList.get(8));
			assertTrue(!Objects.isNull(dataMap) && dataMap.size() == 1);
			
			targetDataMap = new JudgmentFunction().execute(dataMap);
			assertTrue(!Objects.isNull(targetDataMap) && targetDataMap.size() == 0);
			
			
			dataMap = new SelectDataFunction().execute(seqList.get(9));
			assertTrue(!Objects.isNull(dataMap) && dataMap.size() == 1);
			
			targetDataMap = new JudgmentFunction().execute(dataMap);
			assertTrue(!Objects.isNull(targetDataMap) && targetDataMap.size() == 0);
		} catch (Exception e) {
			fail("The program should not raise an exception.");
		} finally {
			try {
				new DisconnectDatabaseFunction().execute();
				dao.dropDb("tar_judge");
			} catch (Exception e) {
			}
		}
	}
}
