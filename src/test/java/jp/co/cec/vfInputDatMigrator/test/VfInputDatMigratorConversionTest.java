package jp.co.cec.vfInputDatMigrator.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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

import jp.co.cec.vfInputDatMigrator.dao.TestInputDatMigratorCassDao;
import jp.co.cec.vfInputDatMigrator.dao.TestInputDatMigratorMariDao;
import jp.co.cec.vfInputDatMigrator.data.TestData;
import jp.co.cec.vfInputDatMigrator.db.dao.SqlSessionFactoryCustom;
import jp.co.cec.vfInputDatMigrator.db.model.InterlockSeq;
import jp.co.cec.vfInputDatMigrator.dto.TrnEvent;
import jp.co.cec.vfInputDatMigrator.function.ConnectCassandraFunction;
import jp.co.cec.vfInputDatMigrator.function.ConnectMariaDbFunction;
import jp.co.cec.vfInputDatMigrator.function.ConversionFunction;
import jp.co.cec.vfInputDatMigrator.function.DeleteExceedFunction;
import jp.co.cec.vfInputDatMigrator.function.DisconnectDatabaseFunction;
import jp.co.cec.vfInputDatMigrator.function.JudgmentFunction;
import jp.co.cec.vfInputDatMigrator.function.SelectDataFunction;
import jp.co.cec.vfInputDatMigrator.function.SelectSequenceFunction;
import jp.co.cec.vfInputDatMigrator.function.TestVfInputDatMigratorFunction;
import jp.co.cec.vfInputDatMigrator.main.VfInputDatMigratorExecutor;
import jp.co.cec.vfInputDatMigrator.utility.TestTimeUtil;

public class VfInputDatMigratorConversionTest {

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
	public void test200_1000() {
	
		JsonNode config = null;
		TestInputDatMigratorMariDao mariDao = null;
		TestInputDatMigratorCassDao cassDao = null;
		List<InterlockSeq> seqList = null;
		
		List<TrnEvent> expectedList = new TestData().expectedChkOk();
		
		try {
			String fileName = "execute/config/test_configChkOk.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(fileName);
			mariDao = new TestInputDatMigratorMariDao(
					config.get("v1ServerAddress").asText()
					,config.get("v1UserName").asText()
					,config.get("v1Password").asText()
					,config.get("v1DBName").asText()
					);
			mariDao.runScript("execute/sql/chkOk.sql");
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
			assertTrue(seqList.size() == 2);
		} catch (Exception e) {
			fail("Preparation fail.");
		}
		
		try {
			Map<Integer, List<Object>> dataMap = null;
			Map<Integer, List<Object>> targetDataMap = null;
			List<TrnEvent> resultList = new ArrayList<TrnEvent>();
			
			for (int index = 0; index < seqList.size(); index++) {
				dataMap = new SelectDataFunction().execute(seqList.get(index));
				targetDataMap = new JudgmentFunction().execute(dataMap);
				List<TrnEvent> trnEventList = null;
				trnEventList = new ConversionFunction().execute(targetDataMap);
				resultList.addAll(trnEventList);
			}

			assertTrue(resultList.size() == 14);
			for (int index = 0; index < resultList.size(); index++) {
				TrnEvent result = resultList.get(index);
				TrnEvent expected = expectedList.get(index);
				
				assertTrue(result.getLogId().equals(expected.getLogId()));
				assertTrue(result.getSubject().equals(expected.getSubject()));
				assertTrue(result.getLogDateTime().equals(expected.getLogDateTime()));
				assertTrue(result.getLogDate().equals(expected.getLogDate()));
				assertTrue(Objects.isNull(result.getLogEndDateTime()));
				assertTrue(result.getValueKey().equals(expected.getValueKey()));
				assertTrue(result.getValue().equals(expected.getValue()));
				assertTrue(result.getMapValue().equals(expected.getMapValue()));
				assertTrue(result.getUpdatedBy().equals(expected.getUpdatedBy()));
				assertTrue(result.getSignalId().equals(expected.getSignalId()));
			}
		} catch (Exception e) {
			fail("The program should not raise an exception.");
		} finally {
			try {
				new DisconnectDatabaseFunction().execute();
				mariDao.dropDb("chk_ok");
				mariDao.dropDb("test_v2");
				cassDao.dropKeySpace("fe_teKeyspace");
				cassDao.dropKeySpace("vf_teKeyspace");
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test200_2000() {
	
		JsonNode config = null;
		TestInputDatMigratorMariDao mariDao = null;
		TestInputDatMigratorCassDao cassDao = null;
		List<InterlockSeq> seqList = null;
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
			Map<Integer, List<Object>> dataMap = null;
			Map<Integer, List<Object>> targetDataMap = null;
			List<TrnEvent> resultList = new ArrayList<TrnEvent>();
			List<TrnEvent> expectedList = new ArrayList<TrnEvent>();
			expectedList.addAll(new TestData().expectedChkNg());
			
			for (int index = 0; index < seqList.size(); index++) {
				dataMap = new SelectDataFunction().execute(seqList.get(index));
				targetDataMap = new JudgmentFunction().execute(dataMap);
				List<TrnEvent> trnEventList = null;
				trnEventList = new ConversionFunction().execute(targetDataMap);
				resultList.addAll(trnEventList);
			}

			assertTrue(resultList.size() == 73);
			for (int index = 0; index < resultList.size(); index++) {
				TrnEvent result = resultList.get(index);
				TrnEvent expected = expectedList.get(index);
				
				assertTrue(result.getLogId().equals(expected.getLogId()));
				assertTrue(result.getSubject().equals(expected.getSubject()));
				assertTrue(result.getLogDateTime().equals(expected.getLogDateTime()));
				assertTrue(result.getLogDate().equals(expected.getLogDate()));
				assertTrue(Objects.isNull(result.getLogEndDateTime()));
				assertTrue(result.getValueKey().equals(expected.getValueKey()));
				assertTrue(result.getValue().equals(expected.getValue()));
				assertTrue(result.getMapValue().equals(expected.getMapValue()));
				assertTrue(result.getUpdatedBy().equals(expected.getUpdatedBy()));
				assertTrue(result.getSignalId().equals(expected.getSignalId()));
			}
		} catch (Exception e) {
			fail("The program should not raise an exception.");
		} finally {
			try {
				new DisconnectDatabaseFunction().execute();
				mariDao.dropDb("chk_ng");
				mariDao.dropDb("test_v2");
				cassDao.dropKeySpace("fe_teKeyspace");
				cassDao.dropKeySpace("vf_teKeyspace");
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test200_3000() {
	
		JsonNode config = null;
		TestInputDatMigratorMariDao mariDao = null;
		TestInputDatMigratorCassDao cassDao = null;
		List<InterlockSeq> seqList = null;
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
			assertTrue(seqList.size() == 3);
		} catch (Exception e) {
			fail("Preparation fail.");
		}
		
		try {
			Map<Integer, List<Object>> dataMap = null;
			Map<Integer, List<Object>> targetDataMap = null;
			List<TrnEvent> resultList = new ArrayList<TrnEvent>();
			List<TrnEvent> expectedList = new TestData().expectedOther();
			
			for (int index = 0; index < seqList.size(); index++) {
				dataMap = new SelectDataFunction().execute(seqList.get(index));
				targetDataMap = new JudgmentFunction().execute(dataMap);
				List<TrnEvent> trnEventList = null;
				trnEventList = new ConversionFunction().execute(targetDataMap);
				resultList.addAll(trnEventList);
			}

			assertTrue(resultList.size() == 68);
			for (int index = 0; index < resultList.size(); index++) {
				TrnEvent result = resultList.get(index);
				TrnEvent expected = expectedList.get(index);
				
				assertTrue(result.getLogId().equals(expected.getLogId()));
				assertTrue(result.getSubject().equals(expected.getSubject()));
				assertTrue(result.getLogDateTime().equals(expected.getLogDateTime()));
				assertTrue(result.getLogDate().equals(expected.getLogDate()));
				assertTrue(Objects.isNull(result.getLogEndDateTime()));
				assertTrue(result.getValueKey().equals(expected.getValueKey()));
				assertTrue(result.getValue().equals(expected.getValue()));
				assertTrue(result.getMapValue().equals(expected.getMapValue()));
				assertTrue(result.getUpdatedBy().equals(expected.getUpdatedBy()));
				assertTrue(result.getSignalId().equals(expected.getSignalId()));
			}
		} catch (Exception e) {
			fail("The program should not raise an exception.");
		} finally {
			try {
				new DisconnectDatabaseFunction().execute();
				mariDao.dropDb("other");
				mariDao.dropDb("test_v2");
				cassDao.dropKeySpace("fe_teKeyspace");
				cassDao.dropKeySpace("vf_teKeyspace");
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test250_5000() {
	
		JsonNode config = null;
		TestInputDatMigratorMariDao mariDao = null;
		TestInputDatMigratorCassDao cassDao = null;
		List<InterlockSeq> seqList = null;
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
			Map<Integer, List<Object>> dataMap = null;
			Map<Integer, List<Object>> targetDataMap = null;
			List<TrnEvent> resultList = new ArrayList<TrnEvent>();
			List<TrnEvent> expectedList = new TestData().expectedGetBack();
			
			for (int index = 0; index < seqList.size(); index++) {
				dataMap = new SelectDataFunction().execute(seqList.get(index));
				targetDataMap = new JudgmentFunction().execute(dataMap);
				List<TrnEvent> trnEventList = null;
				trnEventList = new ConversionFunction().execute(targetDataMap);
				resultList.addAll(trnEventList);
			}

			assertTrue(resultList.size() == 20);
			for (int index = 0; index < resultList.size(); index++) {
				TrnEvent result = resultList.get(index);
				TrnEvent expected = expectedList.get(index);
				
				assertTrue(result.getLogId().equals(expected.getLogId()));
				assertTrue(result.getSubject().equals(expected.getSubject()));
				assertTrue(result.getLogDateTime().equals(expected.getLogDateTime()));
				assertTrue(result.getLogDate().equals(expected.getLogDate()));
				assertTrue(Objects.isNull(result.getLogEndDateTime()));
				assertTrue(result.getValueKey().equals(expected.getValueKey()));
				assertTrue(result.getValue().equals(expected.getValue()));
				assertTrue(result.getMapValue().equals(expected.getMapValue()));
				assertTrue(result.getUpdatedBy().equals(expected.getUpdatedBy()));
				assertTrue(result.getSignalId().equals(expected.getSignalId()));
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
