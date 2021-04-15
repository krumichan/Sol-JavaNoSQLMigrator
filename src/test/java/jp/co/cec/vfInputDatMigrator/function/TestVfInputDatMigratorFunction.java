package jp.co.cec.vfInputDatMigrator.function;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfInputDatMigrator.utility.TestJsonUtil;

public class TestVfInputDatMigratorFunction {
	
	private static final String RESOURCE_FOLDER = "VfInputDatMigrator";
	
	public static JsonNode getConfigNode(final String fileName) throws Exception {
		String resourcePath = RESOURCE_FOLDER + "/" + fileName;
		JsonNode configNode = TestJsonUtil.readTestJson(resourcePath);
		return configNode;
	}
	
	public static <T> T jsonRead(String fileName, Class<T> clazz) throws Exception {
		String resoucePath = RESOURCE_FOLDER + "/" + fileName;
		String fullPath = TestJsonUtil.getResourceFileFullPath(resoucePath);
		TestJsonUtil tju = new TestJsonUtil();
		return tju.jsonRead(fullPath, clazz);
	}
}
