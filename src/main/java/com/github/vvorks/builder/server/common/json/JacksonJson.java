package com.github.vvorks.builder.server.common.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Strings;
import com.github.vvorks.builder.shared.common.util.SimpleEntry;

public class JacksonJson extends Json {

	private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT =
			ThreadLocal.withInitial(() -> new SimpleDateFormat(DATE_PATTERN));

	public static class LongSerializer extends JsonSerializer<Long> {
		public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			if (value == null) {
				gen.writeNull();
			}
			else {
				gen.writeString(Long.toString(value));
			}
		}
	}

	public static class LongDeserializer extends JsonDeserializer<Long> {
		public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			String str = p.getText();
			if (Strings.isEmpty(str)) {
				return null;
			} else {
				return Long.decode(str);
			}
		}
	}

	public static class DecimalSerializer extends JsonSerializer<BigDecimal> {
		public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			if (value == null) {
				gen.writeNull();
			}
			else {
				gen.writeString(value.toString());
			}
		}
	}

	public static class DecimalDeserializer extends JsonDeserializer<BigDecimal> {
		public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			String str = p.getText();
			if (Strings.isEmpty(str)) {
				return null;
			} else {
				return new BigDecimal(str);
			}
		}
	}

	public static class DateSerializer extends JsonSerializer<Date> {
		public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			if (value == null) {
				gen.writeNull();
			}
			else {
				gen.writeString(DATE_FORMAT.get().format(value));
			}
		}
	}

	public static class DateDeserializer extends JsonDeserializer<Date> {
		public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			String str = p.getText();
			if (Strings.isEmpty(str)) {
				return null;
			} else {
				try {
					return new Date(DATE_FORMAT.get().parse(str).getTime());
				} catch (ParseException err) {
					throw new RuntimeException(err);
				}
			}
		}
	}

	private static final ObjectMapper MAPPER = new ObjectMapper();
	static {
		SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, new LongSerializer());
        module.addSerializer(Long.TYPE, new LongSerializer());
        module.addSerializer(BigDecimal.class, new DecimalSerializer());
		module.addSerializer(Date.class, new DateSerializer());
		module.addDeserializer(Long.class, new LongDeserializer());
		module.addDeserializer(Long.TYPE, new LongDeserializer());
		module.addDeserializer(BigDecimal.class, new DecimalDeserializer());
		module.addDeserializer(Date.class, new DateDeserializer());
		MAPPER.registerModule(module);
	}

	private final JsonNode nativeValue;

	public JacksonJson(Object obj) {
		if (obj == null) {
			nativeValue = MAPPER.nullNode();
		} else if (obj instanceof JsonNode) {
			nativeValue = (JsonNode) obj;
		} else if (obj instanceof JacksonJson) {
			nativeValue = ((JacksonJson)obj).getNativeValue();
		} else if (obj instanceof Json) {
			nativeValue = parse(((Json)obj).toJsonString());
		} else if (obj instanceof String) {
			nativeValue = parse((String) obj);
		} else if (obj instanceof Number) {
			nativeValue = toNativeValue(((Number)obj).doubleValue());
		} else if (obj instanceof Boolean) {
			nativeValue = toNativeValue(((Boolean)obj).booleanValue());
		} else {
			nativeValue = toNativeValue(obj);
		}
	}

	private static JsonNode parse(String jsonString) {
		JsonNode value;
		if ("{}".equals(jsonString)) {
			value = MAPPER.createObjectNode();
		} else if ("[]".equals(jsonString)) {
			value = MAPPER.createArrayNode();
		} else {
			try {
				value = MAPPER.readTree(jsonString);
			} catch (JsonProcessingException err) {
				throw new IllegalArgumentException(err);
			}
		}
		return value;
	}

	public static Type getTypeOf(JsonNode value) {
		if (value == null) {
			return Type.UNDEFINED;
		} else if (value.isNull()) {
			return Type.NULL;
		} else if (value.isBoolean()) {
			return Type.BOOLEAN;
		} else if (value.isNumber()) {
			return Type.NUMBER;
		} else if (value.isTextual()) {
			return Type.STRING;
		} else if (value.isObject()) {
			return Type.OBJECT;
		} else if (value.isArray()) {
			return Type.ARRAY;
		} else {
			return Type.UNDEFINED;
		}
	}

	private static boolean asBoolean(JsonNode value, boolean defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.asBoolean();
		case NUMBER:
			return (value.asDouble() != 0.0);
		case STRING:
			return Boolean.valueOf(value.asText());
		default:
			return defaultValue;
		}
	}

	private static int asInt(JsonNode value, int defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.asBoolean() ? 1 : 0;
		case NUMBER:
			return (int) value.asDouble();
		case STRING:
			return Integer.decode(value.asText());
		default:
			return defaultValue;
		}
	}

	private static long asLong(JsonNode value, long defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.asBoolean() ? 1L : 0L;
		case NUMBER:
			return (long) value.asDouble();
		case STRING:
			return Long.decode(value.asText());
		default:
			return defaultValue;
		}
	}

	private static float asFloat(JsonNode value, float defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.asBoolean() ? 1 : 0;
		case NUMBER:
			return (float) value.asDouble();
		case STRING:
			try {
				return Float.valueOf(value.asText());
			} catch (NumberFormatException err) {
				return defaultValue;
			}
		default:
			return defaultValue;
		}
	}

	private static double asDouble(JsonNode value, double defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.asBoolean() ? 1.0 : 0.0;
		case NUMBER:
			return value.asDouble();
		case STRING:
			try {
				return Double.valueOf(value.asText());
			} catch (NumberFormatException err) {
				return defaultValue;
			}
		default:
			return defaultValue;
		}
	}

	private static BigDecimal asDecimal(JsonNode value, BigDecimal defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return BigDecimal.valueOf(value.asBoolean() ? 1.0 : 0.0);
		case NUMBER:
			return BigDecimal.valueOf(value.asDouble());
		case STRING:
			try {
				return new BigDecimal(value.asText());
			} catch (NumberFormatException err) {
				return defaultValue;
			}
		default:
			return defaultValue;
		}
	}

	private static Date asDate(JsonNode value, Date defaultValue) {
		switch (getTypeOf(value)) {
		case NUMBER:
			return new Date((long)value.asDouble());
		case STRING:
			try {
				return DATE_FORMAT.get().parse(value.asText());
			} catch (ParseException err) {
				return defaultValue;
			}
		case BOOLEAN:
		default:
			return defaultValue;
		}
	}

	private static String asString(JsonNode value, String defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return String.valueOf(value.asBoolean());
		case NUMBER:
			return String.valueOf(value.asDouble());
		case STRING:
			return value.asText();
		default:
			return defaultValue;
		}
	}

	private static ObjectNode asObject(JsonNode value) {
		if (getTypeOf(value) != Type.OBJECT) {
			throw new IllegalStateException();
		}
		return (ObjectNode) value;
	}

	private static ArrayNode asArray(JsonNode value) {
		if (getTypeOf(value) != Type.ARRAY) {
			throw new IllegalStateException();
		}
		return (ArrayNode) value;
	}

	private static Json wrap(JsonNode value) {
		return new JacksonJson(value);
	}

	private static JsonNode toNativeValue(boolean value) {
		return value ? BooleanNode.TRUE : BooleanNode.FALSE;
	}

	private static JsonNode toNativeValue(int value) {
		return new DoubleNode(value);
	}

	private static JsonNode toNativeValue(long value) {
		return new TextNode(String.valueOf(value));
	}

	private static JsonNode toNativeValue(float value) {
		return new DoubleNode(value);
	}

	private static JsonNode toNativeValue(double value) {
		return new DoubleNode(value);
	}

	private static JsonNode toNativeValue(BigDecimal value) {
		return new TextNode(value.toString());
	}

	private static JsonNode toNativeValue(Date value) {
		return new TextNode(DATE_FORMAT.get().format(value));
	}

	private static JsonNode toNativeValue(String value) {
		return new TextNode(value);
	}

	private static JsonNode toNativeValue(Json value) {
		if (value == null) {
			return null;
		} else if (value instanceof JacksonJson) {
			return ((JacksonJson)value).getNativeValue();
		} else {
			return parse(value.toJsonString());
		}
	}

	private static JsonNode toNativeValue(Object obj) {
		return MAPPER.convertValue(obj, JsonNode.class);
	}

	private JsonNode getNativeValue() {
		return nativeValue;
	}

	@Override
	public Object toObject(Class<?> cls) {
		try {
			return MAPPER.readValue(MAPPER.treeAsTokens(nativeValue), cls);
		} catch (IOException err) {
			throw new IllegalStateException(err);
		}
	}

	@Override
	public Type getType() {
		return getTypeOf(nativeValue);
	}

	@Override
	public boolean getBooleanValue(boolean defaultValue) {
		return asBoolean(nativeValue, defaultValue);
	}

	@Override
	public int getIntValue(int defaultValue) {
		return asInt(nativeValue, defaultValue);
	}

	@Override
	public long getLongValue(long defaultValue) {
		return asLong(nativeValue, defaultValue);
	}

	@Override
	public float getFloatValue(float defaultValue) {
		return asFloat(nativeValue, defaultValue);
	}

	@Override
	public double getDoubleValue(double defaultValue) {
		return asDouble(nativeValue, defaultValue);
	}

	@Override
	public BigDecimal getDecimalValue(BigDecimal defaultValue) {
		return asDecimal(nativeValue, defaultValue);
	}

	@Override
	public Date getDateValue(Date defaultValue) {
		return asDate(nativeValue, defaultValue);
	}

	@Override
	public String getStringValue(String defaultValue) {
		return asString(nativeValue, defaultValue);
	}

	@Override
	public Json get(String key) {
		JsonNode node = asObject(nativeValue).get(key);
		if (node != null) {
			return wrap(node);
		}
		return null;
	}

	@Override
	public Json computeIfAbsent(String key, Function<String, Json> creator) {
		JsonNode node = asObject(nativeValue).get(key);
		if (node != null) {
			return wrap(node);
		}
		Json newObj = creator.apply(key);
		node = toNativeValue(newObj);
		asObject(nativeValue).set(key, node);
		return newObj;
	}

	@Override
	public Json get(Iterable<String> path) {
		JsonNode node = nativeValue;
		for (String s : path) {
			node = trace(node, s);
			if (node == null) {
				break;
			}
		}
		if (node != null) {
			return wrap(node);
		}
		return null;
	}

	private JsonNode trace(JsonNode v, String nameOrNumber) {
		Type t = getTypeOf(v);
		JsonNode result;
		if (t == Type.OBJECT) {
			result = asObject(v).get(nameOrNumber);
		} else if (t == Type.ARRAY) {
			try {
				int index = Integer.decode(nameOrNumber);
				result = asArray(v).get(index);
			} catch (NumberFormatException err) {
				result = null;
			}
		} else {
			result = null;
		}
		return result;
	}

	@Override
	public Type getType(String key) {
		return getTypeOf(asObject(nativeValue).get(key));
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return asBoolean(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return asInt(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return asLong(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public float getFloat(String key, float defaultValue) {
		return asFloat(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		return asDouble(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public BigDecimal getDecimal(String key, BigDecimal defaultValue) {
		return asDecimal(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public Date getDate(String key, Date defaultValue) {
		return asDate(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public String getString(String key, String defaultValue) {
		return asString(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public int size() {
		return asArray(nativeValue).size();
	}

	@Override
	public Json get(int index) {
		return wrap(asArray(nativeValue).get(index));
	}

	@Override
	public Type getType(int index) {
		return getTypeOf(asArray(nativeValue).get(index));
	}

	@Override
	public boolean getBoolean(int index, boolean defaultValue) {
		return asBoolean(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public int getInt(int index, int defaultValue) {
		return asInt(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public long getLong(int index, long defaultValue) {
		return asLong(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public float getFloat(int index, float defaultValue) {
		return asFloat(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public double getDouble(int index, double defaultValue) {
		return asDouble(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public BigDecimal getDecimal(int index, BigDecimal defaultValue) {
		return asDecimal(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public Date getDate(int index, Date defaultValue) {
		return asDate(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public String getString(int index, String defaultValue) {
		return asString(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public String toJsonString(boolean humanReadable) {
		if (humanReadable) {
			return nativeValue.toPrettyString();
		} else {
			return nativeValue.toString();
		}
	}

	private <X> Iterable<X> asIterable(Iterator<X> itr) {
		return () -> itr;
	}

	@Override
	public Collection<Json> values() {
		Type type = getTypeOf(nativeValue);
		List<Json> result;
		if (type == Type.OBJECT) {
			result = new ArrayList<>();
			ObjectNode obj = asObject(nativeValue);
			for (JsonNode child : asIterable(obj.elements())) {
				result.add(wrap(child));
			}
		} else if (type == Type.ARRAY) {
			result = new ArrayList<>();
			ArrayNode array = asArray(nativeValue);
			for (int i = 0; i < array.size(); i++) {
				result.add(wrap(array.get(i)));
			}
		} else {
			result = Collections.emptyList();
		}
		return result;
	}

	@Override
	public Set<String> keySet() {
		Type type = getTypeOf(nativeValue);
		Set<String> result;
		if (type == Type.OBJECT) {
			ObjectNode obj = asObject(nativeValue);
			result = new LinkedHashSet<>();
			for (String key : asIterable(obj.fieldNames())) {
				result.add(key);
			}
		} else {
			result = Collections.emptySet();
		}
		return result;
	}

	@Override
	public Set<Entry<String, Json>> entrySet() {
		Type type = getTypeOf(nativeValue);
		Set<Map.Entry<String,Json>> result;
		if (type == Type.OBJECT) {
			result = new LinkedHashSet<>();
			ObjectNode obj = asObject(nativeValue);
			for (Map.Entry<String, JsonNode> e : asIterable(obj.fields())) {
				result.add(new SimpleEntry<>(e.getKey(), wrap(e.getValue())));
			}
		} else {
			result = Collections.emptySet();
		}
		return result;
	}

	@Override
	public void remove(String key) {
		asObject(nativeValue).remove(key);
	}

	@Override
	public void setNull(String key) {
		asObject(nativeValue).set(key, null);
	}

	@Override
	public void setBoolean(String key, boolean value) {
		asObject(nativeValue).set(key, toNativeValue(value));
	}


	@Override
	public void setInt(String key, int value) {
		asObject(nativeValue).set(key, toNativeValue(value));
	}

	@Override
	public void setLong(String key, long value) {
		asObject(nativeValue).set(key, toNativeValue(value));
	}

	@Override
	public void setFloat(String key, float value) {
		asObject(nativeValue).set(key, toNativeValue(value));
	}

	@Override
	public void setDouble(String key, double value) {
		asObject(nativeValue).set(key, toNativeValue(value));
	}

	@Override
	public void setDecimal(String key, BigDecimal value) {
		if (value == null) {
			setNull(key);
		} else {
			asObject(nativeValue).set(key, toNativeValue(value));
		}
	}

	@Override
	public void setDate(String key, Date value) {
		if (value == null) {
			setNull(key);
		} else {
			asObject(nativeValue).set(key, toNativeValue(value));
		}
	}

	@Override
	public void setString(String key, String value) {
		if (value == null) {
			setNull(key);
		} else {
			asObject(nativeValue).set(key, toNativeValue(value));
		}
	}

	@Override
	public void set(String key, Json value) {
		if (value == null) {
			setNull(key);
		} else {
			asObject(nativeValue).set(key, toNativeValue(value));
		}
	}

	@Override
	public Json setNewObject(String key) {
		JsonNode value = MAPPER.createObjectNode();
		asObject(nativeValue).set(key, value);
		return wrap(value);
	}

	@Override
	public Json setNewArray(String key) {
		JsonNode value = MAPPER.createArrayNode();
		asObject(nativeValue).set(key, value);
		return wrap(value);
	}

	@Override
	public void setNull(int index) {
		set0(index, null);
	}

	private void set0(int index, JsonNode node) {
		ArrayNode array = asArray(nativeValue);
		int n = array.size();
		if (0 <= index && index < n) {
			array.set(index, node);
		} else if (index == n) {
			array.add(node);
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void setBoolean(int index, boolean value) {
		set0(index, toNativeValue(value));
	}

	@Override
	public void setInt(int index, int value) {
		set0(index, toNativeValue(value));
	}

	@Override
	public void setLong(int index, long value) {
		set0(index, toNativeValue(value));
	}

	@Override
	public void setFloat(int index, float value) {
		set0(index, toNativeValue(value));
	}

	@Override
	public void setDouble(int index, double value) {
		set0(index, toNativeValue(value));
	}

	@Override
	public void setDecimal(int index, BigDecimal value) {
		if (value == null) {
			setNull(index);
		} else {
			set0(index, toNativeValue(value));
		}
	}

	@Override
	public void setDate(int index, Date value) {
		if (value == null) {
			setNull(index);
		} else {
			set0(index, toNativeValue(value));
		}
	}

	@Override
	public void setString(int index, String value) {
		if (value == null) {
			setNull(index);
		} else {
			set0(index, toNativeValue(value));
		}
	}

	@Override
	public void set(int index, Json value) {
		if (value == null) {
			setNull(index);
		} else {
			set0(index, toNativeValue(value));
		}
	}

	@Override
	public Json setNewObject(int index) {
		JsonNode value = MAPPER.createObjectNode();
		set0(index, value);
		return wrap(value);
	}

	@Override
	public Json setNewArray(int index) {
		JsonNode value = MAPPER.createArrayNode();
		set0(index, value);
		return wrap(value);
	}

	@Override
	public Json merge(Json otherJson) {
		if (getTypeOf(nativeValue) != Type.OBJECT) {
			return this;
		}
		JsonNode other;
		if (otherJson instanceof JacksonJson) {
			other = ((JacksonJson) otherJson).getNativeValue();
		} else {
			other = parse(otherJson.toJsonString());
		}
		if (getTypeOf(other) != Type.OBJECT) {
			return this;
		}
		mergeImpl(asObject(nativeValue), asObject(other));
		return this;
	}

	private void mergeImpl(ObjectNode a, ObjectNode b) {
		for (String key : asIterable(b.fieldNames())) {
			JsonNode c1 = a.get(key);
			JsonNode c2 = b.get(key);
			switch (getTypeOf(c1)) {
			case UNDEFINED:
				a.set(key, c2);
				break;
			case OBJECT:
				if (c2 != null && c2.isObject()) {
					mergeImpl(asObject(c1), asObject(c2));
				}
				break;
			default:
				break;
			}
		}
	}

}
