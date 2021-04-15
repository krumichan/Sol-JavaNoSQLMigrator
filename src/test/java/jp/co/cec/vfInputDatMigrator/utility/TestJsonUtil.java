package jp.co.cec.vfInputDatMigrator.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;


public class TestJsonUtil {
	/**
	 * オブジェクトマッパー
	 */
	public ObjectMapper om = null;
	
	/**
	 * コンストラクタ
	 */
	public TestJsonUtil() {
		this.om = new ObjectMapper();
		this.om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.om.configure(DeserializationFeature.WRAP_EXCEPTIONS, false);
		SimpleModule module = new SimpleModule()
				.addDeserializer(String.class, new VFJsonStringDeserializer(String.class));
		this.om.registerModule(module);
	}
	
	class VFJsonStringDeserializer extends StdDeserializer<String> {
		/**
		 * defaultのシリアルバージョンUID
		 */
		private static final long serialVersionUID = 1L;
		
		protected VFJsonStringDeserializer(Class<?> vc) {
			super(vc);
		}
		
		public String deserialize(JsonParser parser, DeserializationContext ctxt) 
		throws IOException, JsonProcessingException {
			if (parser.getCurrentToken() != JsonToken.VALUE_STRING) {
				throw new JsonParseException(parser + "string exptected : " + parser.getText(),
						parser.getCurrentLocation());
			}
			return parser.getText();
		}
	}
	
	/**
	 * 対象のJsonファイルを指定したクラスに変換して読み込む
	 * @param <T> 変換先のクラス
	 * @param filename 読み込み対象のファイル
	 * @param clazz 変換先のクラス
	 * @return 読み込み結果のクラス
	 * @throws Exception
	 */
	public <T> T jsonRead(String fileName, Class<T> clazz) throws Exception {
		T value = null;
		try (InputStream jsonStream = new FileInputStream(fileName)) {
			value = om.readValue(jsonStream, clazz);
		}
		return value;
	}
	
	public static JsonNode readTestJson(String resourcePath) throws Exception {
		String path = getResourceFileFullPath(resourcePath);
		TestJsonUtil tju = new TestJsonUtil();
		JsonNode rootNode = tju.jsonRead(path, JsonNode.class);
		return rootNode;
	}
	
	/**
	 * testフォルダのリソースファイルのフルパスを返す
	 */
	public static String getResourceFileFullPath(String resourcePath) throws Exception {
		String path = TestJsonUtil.class.getClassLoader().getResource(resourcePath).getPath();
		return path;
	}
}
