package jp.co.cec.vfInputDatMigrator.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfInputDatMigrator.function.TestVfInputDatMigratorFunction;
import jp.co.cec.vfInputDatMigrator.main.VfInputDatMigratorExecutor;
import jp.co.cec.vfInputDatMigrator.main.VfInputDatMigratorInitialization;

public class VfInputDatMigratorInitTest {

	private static ByteArrayOutputStream logOutputStream;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logOutputStream = new ByteArrayOutputStream();
		WriterAppender appender = new WriterAppender(new PatternLayout("[%p] %d %c %l%n - %m%n"), logOutputStream);
		LogManager.getRootLogger().addAppender(appender);
		LogManager.getRootLogger().setAdditivity(false);
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
	public void test10_0100() {
		//　全ての項目が正常
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0100.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
		} catch (Exception e) {
			fail("Can not run at setup.");
		}
	}
	
	@Test
	public void test10_0200() {
		//　v1ServerAddressの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0200.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_SERVER_ADDRESS_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0210() {
		//　v1ServerAddressの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0210.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_SERVER_ADDRESS_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0300() {
		//　v1DBNameの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0300.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_DATABASE_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0310() {
		//　v1DBNameの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0310.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_DATABASE_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0400() {
		//　v1UserNameの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0400.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_USER_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0410() {
		//　v1UserNameの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0410.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_USER_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0500() {
		//　v1Passwordの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0500.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_PASSWORD_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0510() {
		//　v1Passwordの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0510.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_PASSWORD_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_9100() {
		//　v1CassServerAddressの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_9100.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_CASSANDRA_SERVER_ADDRESS_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_9110() {
		//　v1CassServerAddressの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_9110.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_CASSANDRA_SERVER_ADDRESS_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_9200() {
		//　v1DBKeyspaceの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_9200.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_CASSANDRA_KEYSPACE_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_9210() {
		//　v1DBKeyspaceの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_9210.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V1_CASSANDRA_KEYSPACE_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0600() {
		//　v2DBNameの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0600.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V2_DATABASE_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0610() {
		//　v2DBNameの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0610.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V2_DATABASE_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0700() {
		//　v2UserNameの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0700.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V2_USER_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0710() {
		//　v2UserNameの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0710.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V2_USER_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0800() {
		//　v2Passwordの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0800.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V2_PASSWORD_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0810() {
		//　v2Passwordの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0810.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V2_PASSWORD_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0900() {
		//　v2DBKeyspaceの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0900.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V2_CASSANDRA_KEYSPACE_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0910() {
		//　v2DBKeyspaceの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_0910.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_V2_CASSANDRA_KEYSPACE_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1100() {
		//　LogPathの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1100.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_LOG_PATH_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1110() {
		//　LogPathの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1110.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_LOG_PATH_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1200() {
		//　viInStrの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1200.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_VISUAL_INSPECTION_STRING_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1210() {
		//　viInStrの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1210.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_VISUAL_INSPECTION_STRING_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1300() {
		//　wsInStrの項目無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1300.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_WORK_STATUS_STRING_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1310() {
		//　wsInStrの項目空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1310.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_WORK_STATUS_STRING_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1410() {
		//　idmMasterの項目が無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1410.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_IDM_MASTER_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1420() {
		//　idmMasterの項目が空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1420.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_IDM_MASTER_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1510() {
		//　idmMasterのidの項目が無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1510.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_ID_NULL));
		}
	}
	
	@Test
	public void test10_1520() {
		//　idmMasterのsignalInformの項目が無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1520.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_SIGNAL_INFROM_NULL));
		}
	}
	
	@Test
	public void test10_1610() {
		//　idmMasterのsignalInformのmajor項目が無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1610.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_MAJOR_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1620() {
		//　idmMasterのsignalInformのmedium項目が無し
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_1620.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_MEDIUM_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_2000() {
		//　oldDataDelの項目が不正
		//　文字列のみ
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_2000.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_OLD_DATA_DEL_ILLEGAL));
		}
	}
	
	@Test
	public void test10_2010() {
		//　oldDataDelの項目が不正
		//　数字のみ
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_2010.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_OLD_DATA_DEL_ILLEGAL));
		}
	}
	
	@Test
	public void test10_2020() {
		//　oldDataDelの項目が不正
		//　順番が逆
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_2020.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_OLD_DATA_DEL_ILLEGAL));
		}
	}
	
	@Test
	public void test10_2030() {
		//　oldDataDelの項目が不正
		//　不正な日時区分
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_2030.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_OLD_DATA_DEL_ILLEGAL));
		}
	}
	
	@Test
	public void test10_3000() {
		//　retryCountの項目が不正
		//　実数
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_3000.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_RETRY_COUNT_ILLEGAL));
		}
	}
	
	@Test
	public void test10_3010() {
		//　retryCountの項目が不正
		//　文字列
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_3010.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_RETRY_COUNT_ILLEGAL));
		}
	}
	
	@Test
	public void test10_3020() {
		//　retryCountの項目が不正
		//　マイナス
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_3020.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_RETRY_COUNT_ILLEGAL));
		}
	}
	
	@Test
	public void test10_3100() {
		//　retryIntervalの項目が不正
		//　実数
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_3100.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_RETRY_INTERVAL_ILLEGAL));
		}
	}
	
	@Test
	public void test10_3110() {
		//　retryIntervalの項目が不正
		//　文字列
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_3110.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_RETRY_INTERVAL_ILLEGAL));
		}
	}
	
	@Test
	public void test10_3120() {
		//　retryIntervalの項目が不正
		//　マイナス
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_3120.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_RETRY_INTERVAL_ILLEGAL));
		}
	}
	
	@Test
	public void test10_3200() {
		//　selLimitの項目が不正
		//　文字列のみ
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_3200.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_SEL_LIMIT_ILLEGAL));
		}
	}
	
	@Test
	public void test10_3210() {
		//　selLimitの項目が不正
		//　実数
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_3210.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_SEL_LIMIT_ILLEGAL));
		}
	}
	
	@Test
	public void test10_3220() {
		//　selLimitの項目が不正
		//　ゼロ
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_3220.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_SEL_LIMIT_ILLEGAL));
		}
	}
	
	@Test
	public void test10_3230() {
		//　selLimitの項目が不正
		//　マイナス
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_3230.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_SEL_LIMIT_ILLEGAL));
		}
	}
	
	@Test
	public void test10_4000() {
		//　viIntStrの項目の要素がnull
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_4000.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_VISUAL_INSPECTION_STRING_ELEMENT_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_4010() {
		//　viIntStrの項目の要素が空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_4010.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_VISUAL_INSPECTION_STRING_ELEMENT_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_4100() {
		//　wsInStrの項目の要素がnull
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_4100.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_WORK_STATUS_STRING_ELEMENT_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_4110() {
		//　wsInStrの項目の要素が空
		
		JsonNode config = null;
		try {
			String filename = "init/test_config10_4110.conf";
			config = TestVfInputDatMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfInputDatMigratorExecutor(config);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfInputDatMigratorInitialization.ERROR_STATE_WORK_STATUS_STRING_ELEMENT_NULLOREMPTY));
		}
	}
}
