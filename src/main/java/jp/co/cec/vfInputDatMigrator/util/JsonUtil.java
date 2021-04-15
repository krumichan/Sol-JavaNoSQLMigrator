package jp.co.cec.vfInputDatMigrator.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

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

/**
 * Json Utility
 */
public class JsonUtil {
	
	/**
	 * オブジェクトマッパー
	 */
	public ObjectMapper om = null;
	
	/**
	 * コンストラクタ
	 */
	public JsonUtil() {
		this.om = new ObjectMapper();
		this.om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.om.configure(DeserializationFeature.WRAP_EXCEPTIONS, false);
		SimpleModule module = new SimpleModule()
				.addDeserializer(String.class, new VFJsonStringDeserializer(String.class))
				.addDeserializer(Integer.TYPE, new VFJsonIntegerDeserializer(Integer.TYPE))
				.addDeserializer(Integer.class, new VFJsonIntegerDeserializer(Integer.class))
				.addDeserializer(Long.TYPE, new VFJsonLongDeserializer(Long.TYPE))
				.addDeserializer(Long.class, new VFJsonLongDeserializer(Long.class))
				.addDeserializer(Boolean.TYPE, new VFJsonBooleanDeserializer(Boolean.TYPE))
				.addDeserializer(Boolean.class, new VFJsonBooleanDeserializer(Boolean.class))
				.addDeserializer(Float.TYPE, new VFJsonFloatDeserializer(Float.TYPE))
				.addDeserializer(Float.class, new VFJsonFloatDeserializer(Float.class))
				.addDeserializer(Double.TYPE, new VFJsonDoubleDeserializer(Double.TYPE))
				.addDeserializer(Double.class, new VFJsonDoubleDeserializer(Double.class))
				.addDeserializer(BigDecimal.class, new VFJsonBigDecimalDeserializer(BigDecimal.class));
		om.registerModule(module);
	}
	
	/**
	 * 対象のJson文字列を指定したクラスに変換する
	 * 
	 * @param <T>   変換先のクラス
	 * @param json  対象のJson文字列
	 * @param clazz 変換先のクラス
	 * @return 変換結果のクラス
	 */
	public <T> T convert(JsonNode json, Class<T> clazz)  throws Exception {
		return om.treeToValue(json, clazz);
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
	
	/**
	 * 対象のJsonを指定したタイプに変換して読み込む
	 * @param <T> 変換先のクラス
	 * @param filename 対象のJSON
	 * @param ypeReference<T> 変換タイプ
	 * @return 変換結果
	 * @throws Exception
	 */
	public <T> T strJsonRead(String json, Class<T> clazz) throws Exception {
		T value = null;
		if (json == null) {
			return value;
		}
		
		try {
			value = om.readValue(json, clazz);
		} catch (Exception e) {
			throw e;
		}
		
		return value;
	}
	
	class VFJsonStringDeserializer extends StdDeserializer<String> {
		/**
		 * defaultのシリアルバージョンUID
		 */
		private static final long serialVersionUID = 1L;

		protected VFJsonStringDeserializer(Class<?> vc) {
			super(vc);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public String deserialize(JsonParser parser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			if (parser.getCurrentToken() != JsonToken.VALUE_STRING) {
				throw new JsonParseException(parser + "string expected : " + parser.getText(),
						parser.getCurrentLocation());
			}
			return parser.getText();
		}
	}

	class VFJsonIntegerDeserializer extends StdDeserializer<Integer> {
		/**
		 * defaultのシリアルバージョンUID
		 */
		private static final long serialVersionUID = 1L;

		protected VFJsonIntegerDeserializer(Class<?> vc) {
			super(vc);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public Integer deserialize(JsonParser parser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			if (parser.getCurrentToken() != JsonToken.VALUE_NUMBER_INT) {
				throw new JsonParseException(parser + "int expected : " + parser.getText(),
						parser.getCurrentLocation());
			}
			return parser.getIntValue();
		}
	}

	class VFJsonLongDeserializer extends StdDeserializer<Long> {
		/**
		 * defaultのシリアルバージョンUID
		 */
		private static final long serialVersionUID = 1L;

		protected VFJsonLongDeserializer(Class<?> vc) {
			super(vc);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public Long deserialize(JsonParser parser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			if (parser.getCurrentToken() != JsonToken.VALUE_NUMBER_INT) {
				throw new JsonParseException(parser + "long expected : " + parser.getText(),
						parser.getCurrentLocation());
			}
			return parser.getLongValue();
		}
	}

	class VFJsonBooleanDeserializer extends StdDeserializer<Boolean> {
		/**
		 * defaultのシリアルバージョンUID
		 */
		private static final long serialVersionUID = 1L;

		protected VFJsonBooleanDeserializer(Class<?> vc) {
			super(vc);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public Boolean deserialize(JsonParser parser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			if (parser.getCurrentToken() != JsonToken.VALUE_TRUE && parser.getCurrentToken() != JsonToken.VALUE_FALSE) {
				throw new JsonParseException(parser + "boolean expected : " + parser.getText(),
						parser.getCurrentLocation());
			}
			return parser.getBooleanValue();
		}
	}

	class VFJsonFloatDeserializer extends StdDeserializer<Float> {
		/**
		 * defaultのシリアルバージョンUID
		 */
		private static final long serialVersionUID = 1L;

		protected VFJsonFloatDeserializer(Class<?> vc) {
			super(vc);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public Float deserialize(JsonParser parser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			if (parser.getCurrentToken() != JsonToken.VALUE_NUMBER_INT
					&& parser.getCurrentToken() != JsonToken.VALUE_NUMBER_FLOAT) {
				throw new JsonParseException(parser + "float expected : " + parser.getText(),
						parser.getCurrentLocation());
			}else if(parser.getFloatValue() < - Float.MAX_VALUE || Float.MAX_VALUE < parser.getFloatValue()){
				throw new JsonParseException(parser + "float out of range : " + parser.getText(),
						parser.getCurrentLocation());
			}
			return parser.getFloatValue();
		}
	}

	class VFJsonDoubleDeserializer extends StdDeserializer<Double> {
		/**
		 * defaultのシリアルバージョンUID
		 */
		private static final long serialVersionUID = 1L;

		protected VFJsonDoubleDeserializer(Class<?> vc) {
			super(vc);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public Double deserialize(JsonParser parser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			if (parser.getCurrentToken() != JsonToken.VALUE_NUMBER_INT
					&& parser.getCurrentToken() != JsonToken.VALUE_NUMBER_FLOAT) {
				throw new JsonParseException(parser + "double expected : " + parser.getText(),
						parser.getCurrentLocation());
			}else if(parser.getDoubleValue() < - Double.MAX_VALUE || Double.MAX_VALUE < parser.getDoubleValue()) {
				throw new JsonParseException(parser + "double out of range : " + parser.getText(),
						parser.getCurrentLocation());
			}
			return parser.getDoubleValue();
		}
	}

	class VFJsonBigDecimalDeserializer extends StdDeserializer<BigDecimal> {
		/**
		 * defaultのシリアルバージョンUID
		 */
		private static final long serialVersionUID = 1L;

		protected VFJsonBigDecimalDeserializer(Class<?> vc) {
			super(vc);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public BigDecimal deserialize(JsonParser parser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			if (parser.getCurrentToken() != JsonToken.VALUE_STRING) {
				throw new JsonParseException(parser + "bigDecimal expected : " + parser.getText(),
						parser.getCurrentLocation());
			}
			return new BigDecimal(parser.getText());
		}
	}
}
