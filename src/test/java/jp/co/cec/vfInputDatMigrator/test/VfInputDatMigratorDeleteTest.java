package jp.co.cec.vfInputDatMigrator.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import jp.co.cec.vfInputDatMigrator.dao.TestInputDatMigratorCassDao;
import jp.co.cec.vfInputDatMigrator.dao.TestInputDatMigratorMariDao;
import jp.co.cec.vfInputDatMigrator.data.TestData;
import jp.co.cec.vfInputDatMigrator.db.dao.SqlSessionFactoryCustom;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockSeq;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockVisualInspection;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockWorkStatus;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
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
import jp.co.cec.vfInputDatMigrator.function.TestVfInputDatMigratorFunction;
import jp.co.cec.vfInputDatMigrator.main.VfInputDatMigratorExecutor;
import jp.co.cec.vfInputDatMigrator.utility.TestTimeUtil;

public class VfInputDatMigratorDeleteTest {

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
	public void test400_1000() {
	
		JsonNode config = null;
		TestInputDatMigratorMariDao mariDao = null;
		TestInputDatMigratorCassDao cassDao = null;
		List<InterlockSeq> seqList = null;
		Map<Integer, List<Object>> dataMap = null;
		Map<Integer, List<Object>> targetDataMap = null;
		try {
			String fileName = "execute/config/test_configGetBack.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(fileName);
			mariDao = new TestInputDatMigratorMariDao(
					config.get("v1ServerAddress").asText()
					,config.get("v1UserName").asText()
					,config.get("v1Password").asText()
					,config.get("v1DBName").asText()
					);
			mariDao.runScript("execute/sql/getBack.sql");
			cassDao = new TestInputDatMigratorCassDao(
					config.get("v2CassServerAddress").asText()
					);
			cassDao.runScript("execute/cql/fe_teKeyspace.cql");
			cassDao.runScript("execute/cql/vf_teKeyspace.cql");
			String delDate = TestTimeUtil.getOldDelDate();
			((ObjectNode)config).put("oldDataDel", delDate + "d");
			new VfInputDatMigratorExecutor(config);
		} catch (Exception e) {
			fail("Set configuration fail.");
		}
		
		try {
			new ConnectCassandraFunction().execute();
			new ConnectMariaDbFunction().execute();
			new DeleteExceedFunction().execute();
			seqList = new SelectSequenceFunction().execute();
			assertTrue(seqList.size() == 2);
		} catch (Exception e) {
			fail("Preparation fail.");
		}
		
		try {
			List<Integer> eventExpected = (List<Integer>) Arrays.asList(new Integer[] { 13, 7 });
			List<Integer> cassExpected = (List<Integer>) Arrays.asList(new Integer[] { 13, 20 });
			Map<String, List<Timestamp>> expected = new TestData().deleteGetBack();
			
			for (int index = 0; index < seqList.size(); index++) {
				dataMap = new SelectDataFunction().execute(seqList.get(index));
				if (dataMap == null || dataMap.isEmpty()) {
					continue;
				}
				targetDataMap = new JudgmentFunction().execute(dataMap);
				
				List<TrnEvent> trnEventList = new ConversionFunction().execute(targetDataMap);
				assertTrue(trnEventList.size() == eventExpected.get(index));

				new InsertCassandraFunction().execute(trnEventList);
				List<TrnEvent> res = cassDao.select("vf_teKeyspace", "trn_event");
				assertTrue(res.size() == cassExpected.get(index));
				
				new DeleteProccessedFunction().execute(dataMap, seqList.get(index));
				List<InterlockSeq> tmpSeqList = mariDao.getSeq();
				List<Timestamp> tmpStamp = expected.get("is_loop" + index);
				assertTrue(tmpSeqList.size() == tmpStamp.size());
				for (int seIndex = 0; seIndex < tmpSeqList.size(); seIndex++) {
					InterlockSeq is = tmpSeqList.get(seIndex);
					Timestamp ts = tmpStamp.get(seIndex);
					assertTrue(is.getExecDateTime().equals(ts));
				}
				
				List<InterlockVisualInspection> tmpIviList = mariDao.getVi();
				tmpStamp = expected.get("ivi_loop" + index);
				assertTrue(tmpIviList.size() == tmpStamp.size());
				for (int viIndex = 0; viIndex < tmpIviList.size(); viIndex++) {
					InterlockVisualInspection ivi = tmpIviList.get(viIndex);
					Timestamp ts = tmpStamp.get(viIndex);;
					assertTrue(ivi.getExec_date_time().equals(ts));
				}
				
				List<InterlockWorkStatus> tmpIwsList = mariDao.getWs();
				tmpStamp = expected.get("iws_loop" + index);
				assertTrue(tmpIwsList.size() == tmpStamp.size());
				for (int wsIndex = 0; wsIndex < tmpIwsList.size(); wsIndex++) {
					InterlockWorkStatus iws = tmpIwsList.get(wsIndex);
					Timestamp ts = tmpStamp.get(wsIndex);
					assertTrue(iws.getExec_date_time().equals(ts));
				}
			}
		} catch (Exception e) {
			fail("The program should not raise an exception.");
		} finally {
			try {
				new DisconnectDatabaseFunction().execute();
				mariDao.dropDb("get_back");
				mariDao.dropDb("test_v2");
				cassDao.dropKeySpace("fe_teKeyspace");
				cassDao.dropKeySpace("vf_teKeyspace");
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_2000() {
	
		JsonNode config = null;
		TestInputDatMigratorMariDao mariDao = null;
		TestInputDatMigratorCassDao cassDao = null;
		List<InterlockSeq> seqList = null;
		Map<Integer, List<Object>> dataMap = null;
		Map<Integer, List<Object>> targetDataMap = null;
		try {
			String fileName = "execute/config/test_configChkNg.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(fileName);
			mariDao = new TestInputDatMigratorMariDao(
					config.get("v1ServerAddress").asText()
					,config.get("v1UserName").asText()
					,config.get("v1Password").asText()
					,config.get("v1DBName").asText()
					);
			mariDao.runScript("execute/sql/chkNg.sql");
			cassDao = new TestInputDatMigratorCassDao(
					config.get("v2CassServerAddress").asText()
					);
			cassDao.runScript("execute/cql/fe_teKeyspace(NoneGetBack).cql");
			cassDao.runScript("execute/cql/vf_teKeyspace.cql");
			String delDate = TestTimeUtil.getOldDelDate();
			((ObjectNode)config).put("oldDataDel", delDate + "d");
			new VfInputDatMigratorExecutor(config);
		} catch (Exception e) {
			fail("Set configuration fail.");
		}
		
		try {
			new ConnectCassandraFunction().execute();
			new ConnectMariaDbFunction().execute();
			new DeleteExceedFunction().execute();
			seqList = new SelectSequenceFunction().execute();
			assertTrue(seqList.size() == 4);
		} catch (Exception e) {
			fail("Preparation fail.");
		}
		
		try {
			List<Integer> eventExpected = (List<Integer>) Arrays.asList(new Integer[] { 0, 14, 28, 31 });
			List<Integer> cassExpected = (List<Integer>) Arrays.asList(new Integer[] { 0, 14, 28, 45 });
			Map<String, List<Timestamp>> expected = new TestData().deleteNg();
			
			for (int index = 0; index < seqList.size(); index++) {
				dataMap = new SelectDataFunction().execute(seqList.get(index));
				if (dataMap == null || dataMap.isEmpty()) {
					continue;
				}
				targetDataMap = new JudgmentFunction().execute(dataMap);
				
				List<TrnEvent> trnEventList = new ConversionFunction().execute(targetDataMap);
				assertTrue(trnEventList.size() == eventExpected.get(index));

				new InsertCassandraFunction().execute(trnEventList);
				List<TrnEvent> res = cassDao.select("vf_teKeyspace", "trn_event");
				assertTrue(res.size() == cassExpected.get(index));
				
				new DeleteProccessedFunction().execute(dataMap, seqList.get(index));
				List<InterlockSeq> tmpSeqList = mariDao.getSeq();
				List<Timestamp> tmpStamp = expected.get("is_loop" + index);
				assertTrue(tmpSeqList.size() == tmpStamp.size());
				for (int seIndex = 0; seIndex < tmpSeqList.size(); seIndex++) {
					InterlockSeq is = tmpSeqList.get(seIndex);
					Timestamp ts = tmpStamp.get(seIndex);
					assertTrue(is.getExecDateTime().equals(ts));
				}
				
				List<InterlockVisualInspection> tmpIviList = mariDao.getVi();
				tmpStamp = expected.get("ivi_loop" + index);
				assertTrue(tmpIviList.size() == tmpStamp.size());
				for (int viIndex = 0; viIndex < tmpIviList.size(); viIndex++) {
					InterlockVisualInspection ivi = tmpIviList.get(viIndex);
					Timestamp ts = tmpStamp.get(viIndex);;
					assertTrue(ivi.getExec_date_time().equals(ts));
				}
				
				List<InterlockWorkStatus> tmpIwsList = mariDao.getWs();
				tmpStamp = expected.get("iws_loop" + index);
				assertTrue(tmpIwsList.size() == tmpStamp.size());
				for (int wsIndex = 0; wsIndex < tmpIwsList.size(); wsIndex++) {
					InterlockWorkStatus iws = tmpIwsList.get(wsIndex);
					Timestamp ts = tmpStamp.get(wsIndex);
					assertTrue(iws.getExec_date_time().equals(ts));
				}
			}
		} catch (Exception e) {
			fail("The program should not raise an exception.");
		} finally {
			try {
				new DisconnectDatabaseFunction().execute();
				mariDao.dropDb("get_back");
				mariDao.dropDb("test_v2");
				cassDao.dropKeySpace("fe_teKeyspace");
				cassDao.dropKeySpace("vf_teKeyspace");
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_3000() {
	
		JsonNode config = null;
		TestInputDatMigratorMariDao mariDao = null;
		TestInputDatMigratorCassDao cassDao = null;
		List<InterlockSeq> seqList = null;
		Map<Integer, List<Object>> dataMap = null;
		Map<Integer, List<Object>> targetDataMap = null;
		try {
			String fileName = "execute/config/test_configOther.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(fileName);
			mariDao = new TestInputDatMigratorMariDao(
					config.get("v1ServerAddress").asText()
					,config.get("v1UserName").asText()
					,config.get("v1Password").asText()
					,config.get("v1DBName").asText()
					);
			mariDao.runScript("execute/sql/other.sql");
			cassDao = new TestInputDatMigratorCassDao(
					config.get("v2CassServerAddress").asText()
					);
			cassDao.runScript("execute/cql/fe_teKeyspace(NoneGetBack).cql");
			cassDao.runScript("execute/cql/vf_teKeyspace.cql");
			String delDate = TestTimeUtil.getOldDelDate();
			((ObjectNode)config).put("oldDataDel", delDate + "d");
			new VfInputDatMigratorExecutor(config);
		} catch (Exception e) {
			fail("Set configuration fail.");
		}
		
		try {
			new ConnectCassandraFunction().execute();
			new ConnectMariaDbFunction().execute();
			new DeleteExceedFunction().execute();
			seqList = new SelectSequenceFunction().execute();
			assertTrue(seqList.size() == 3);
		} catch (Exception e) {
			fail("Preparation fail.");
		}
		
		try {
			List<Integer> eventExpected = (List<Integer>) Arrays.asList(new Integer[] { 13, 26, 29 });
			List<Integer> cassExpected = (List<Integer>) Arrays.asList(new Integer[] { 13, 26, 42 });
			Map<String, List<Timestamp>> expected = new TestData().deleteOther();
			
			for (int index = 0; index < seqList.size(); index++) {
				dataMap = new SelectDataFunction().execute(seqList.get(index));
				if (dataMap == null || dataMap.isEmpty()) {
					continue;
				}
				targetDataMap = new JudgmentFunction().execute(dataMap);
				
				List<TrnEvent> trnEventList = new ConversionFunction().execute(targetDataMap);
				assertTrue(trnEventList.size() == eventExpected.get(index));

				new InsertCassandraFunction().execute(trnEventList);
				List<TrnEvent> res = cassDao.select("vf_teKeyspace", "trn_event");
				assertTrue(res.size() == cassExpected.get(index));
				
				new DeleteProccessedFunction().execute(dataMap, seqList.get(index));
				List<InterlockSeq> tmpSeqList = mariDao.getSeq();
				List<Timestamp> tmpStamp = expected.get("is_loop" + index);
				assertTrue(tmpSeqList.size() == tmpStamp.size());
				for (int seIndex = 0; seIndex < tmpSeqList.size(); seIndex++) {
					InterlockSeq is = tmpSeqList.get(seIndex);
					Timestamp ts = tmpStamp.get(seIndex);
					assertTrue(is.getExecDateTime().equals(ts));
				}
				
				List<InterlockVisualInspection> tmpIviList = mariDao.getVi();
				tmpStamp = expected.get("ivi_loop" + index);
				assertTrue(tmpIviList.size() == tmpStamp.size());
				for (int viIndex = 0; viIndex < tmpIviList.size(); viIndex++) {
					InterlockVisualInspection ivi = tmpIviList.get(viIndex);
					Timestamp ts = tmpStamp.get(viIndex);;
					assertTrue(ivi.getExec_date_time().equals(ts));
				}
				
				List<InterlockWorkStatus> tmpIwsList = mariDao.getWs();
				tmpStamp = expected.get("iws_loop" + index);
				assertTrue(tmpIwsList.size() == tmpStamp.size());
				for (int wsIndex = 0; wsIndex < tmpIwsList.size(); wsIndex++) {
					InterlockWorkStatus iws = tmpIwsList.get(wsIndex);
					Timestamp ts = tmpStamp.get(wsIndex);
					assertTrue(iws.getExec_date_time().equals(ts));
				}
			}
		} catch (Exception e) {
			fail("The program should not raise an exception.");
		} finally {
			try {
				new DisconnectDatabaseFunction().execute();
				mariDao.dropDb("get_back");
				mariDao.dropDb("test_v2");
				cassDao.dropKeySpace("fe_teKeyspace");
				cassDao.dropKeySpace("vf_teKeyspace");
			} catch (Exception e) {
			}
		}
	}
}
